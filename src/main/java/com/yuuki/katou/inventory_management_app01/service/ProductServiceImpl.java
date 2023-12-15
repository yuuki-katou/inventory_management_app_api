package com.yuuki.katou.inventory_management_app01.service;

import com.yuuki.katou.inventory_management_app01.dto.ProductDto;
import com.yuuki.katou.inventory_management_app01.entity.Product;
import com.yuuki.katou.inventory_management_app01.exception.DatabaseOperationException;
import com.yuuki.katou.inventory_management_app01.exception.InternalConsistencyException;
import com.yuuki.katou.inventory_management_app01.exception.ResourceNotFoundException;
import com.yuuki.katou.inventory_management_app01.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    //定数：税率
    private static final BigDecimal TAX_RATE = new BigDecimal("0.10");
    //定数：日付フォーマット
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //ログ　設定ファイル resources > logback.xml, 出力先 ルート > logs > application.log
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    // 依存関係: ProductMapper
    // Productエンティティとデータベースとの間の操作を担当する

    private ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    // 全ての商品情報を取得するメソッド
    @Override
    public List<ProductDto> findAll() {
        // productMapperを使用して、データベースから全商品情報を取得
        List<Product> products = productMapper.findAll();

        // 取得したProductオブジェクトをProductDtoに変換
        // 商品が見つからない場合はResourceNotFoundExceptionを投げる
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("商品情報が見つかりませんでした。");
        } else {
            return products.stream()
                    .map(this::transformProductToDto).toList();
        }
    }

    // 特定のIDを持つ商品情報を取得するメソッド
    @Override
    public ProductDto findById(int productId) {
        // productMapperを使用して、指定されたIDの商品情報を取得
        Optional<Product> productOpt = productMapper.findById(productId);

        // Optionalが空でない場合、ProductDtoに変換して返す
        // 空の場合は例外を投げる
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            return transformProductToDto(product);
        } else {
            throw new ResourceNotFoundException("ProductID：" + productId + "の商品は見つかりませんでした。");
        }
    }

    // 特定の検索条件に基づいて商品情報を取得するメソッド
    @Override
    public List<ProductDto> findByCriteria(String keyword, String brandName, String categoryName, String storeName, BigDecimal priceLower, BigDecimal priceUpper, String conditionName, String sortOrder) {
        // productMapperを使用して、指定された条件に基づく商品情報を取得
        List<Product> products = this.productMapper.findByCriteria(keyword, brandName, categoryName, storeName, priceLower, priceUpper, conditionName, sortOrder);

        // 取得した商品が空でない場合は、ProductDtoに変換して返す
        // 空の場合は例外を投げる
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("指定された検索条件に一致する商品は見つかりませんでした。");
        } else {
            return products.stream()
                    .map(this::transformProductToDto).toList();
        }
    }

    //商品情報の新規登録を行うメソッド
    @Override
    public int createProduct(ProductDto productDto) {
        //ProductDtoからProductへ変換
        Product product = productDto.convertToEntity();

        //setProductDetailsを実行して必要なデータを設定
        setProductDetails(product);

        //productMapperを使用して商品情報を登録する。
        //戻り値として登録処理によって影響を受けた行数を返す。データが登録できなかった場合は、0が返る。
        int numberRegistered = productMapper.create(product);
        if (numberRegistered > 0) {
            return product.getProductId();
        } else {
            logger.error("Product registration failed: {}", productDto.toString());
            throw new DatabaseOperationException("Create", "Failed to register product");
        }
    }

    //商品情報を更新するメソッド
    @Override
    public int updateProduct(ProductDto productDto) {
        //productMapperを使用して更新する対象が実在するか確認する。
        productMapper.findById(productDto.getProductId()).orElseThrow(() ->
                new ResourceNotFoundException("ProductID：" + productDto.getProductId() + "の商品は見つかりませんでした。"));

//      //引数のproductDtoをProductに変換する
        Product product = productDto.convertToEntity();

        //setProductDetailsを実行して必要なデータを設定
        setProductDetails(product);

        //productMapperを使用して商品情報を更新する。
        //戻り値として更新処理によって影響を受けた行数を返す。データが更新できなかった場合は、0が返る。
        int numberUpdated = productMapper.updateProduct(product);
        if (numberUpdated > 0) {
            return product.getProductId();
        } else {
            logger.error("Product update failed: {}", productDto.toString());
            throw new DatabaseOperationException("Update", "Failed to update product");
        }
    }

    //商品情報を削除するメソッド
    @Override
    public int deletedProduct(int productId) {
        //productMapperを使用して更新する対象が実在するか確認する。
        productMapper.findById(productId).orElseThrow(() -> new ResourceNotFoundException("ProductID：" + productId + "の商品は見つかりませんでした。"));

        //productMapperを使用して商品情報を削除する。
        //戻り値として削除処理によって影響を受けた行数を返す。データが削除できなかった場合は、0が返る。
        int numberDeleted = productMapper.deleteById(productId);
        if (numberDeleted > 0) {
            return productId;
        } else {
            logger.error("Product deletion failed for Product ID: {}", productId);
            throw new DatabaseOperationException("Delete", "Failed to delete product");
        }
    }

    //商品情報に対応するIDを設定
    //ブランド名、カテゴリ名、店舗名、商品状態名に基づいて、対応するIDを取得して設定する。
    //対応するIDが見つからない場合、InternalConsistencyExceptionをスローする。
    private void setProductDetails(Product product) throws InternalConsistencyException {
        try {
//            ブランドIDを設定
            product.setBrandId(productMapper.getIdByName("brands", "brand_id", "brand_name", product.getBrand()));
            if (product.getBrandId() == null) {
                throw new NullPointerException("指定したブランド名「" + product.getBrand() + "」に対応するBrandIDが見つかりませんでした。");
            }

//            カテゴリIDを設定
            product.setCategoryId(productMapper.getIdByName("categories", "category_id", "Category_name", product.getCategory()));
            if (product.getCategoryId() == null) {
                throw new NullPointerException("指定されたカテゴリ名「" + product.getCategory() + "」に対応するCategoryIDが見つかりませんでした。");
            }
            //店舗IDを設定
            product.setStoreId(productMapper.getIdByName("stores", "store_id", "Store_name", product.getStore()));
            if (product.getStoreId() == null) {
                throw new NullPointerException("指定された取り扱い店舗名「" + product.getBrand() + "」に対応するStoreIDが見つかりませんでした。");
            }
            //商品状態IDを設定
            product.setConditionId(productMapper.getIdByName("conditions", "condition_id", "condition_name", product.getCondition()));
            if (product.getConditionId() == null) {
                throw new NullPointerException("指定された商品の状態「" + product.getCondition() + "」に対応するConditionIDが見つかりませんでした。");
            }

        } catch (NullPointerException e) {
            throw new InternalConsistencyException("選択されたブランド情報の処理中に問題が発生しました。再試行しても問題が解決しない場合は、サポートまでご連絡ください。エラー内容：" + e.getMessage());
        }
    }

    //ProductエンティティをProductDtoに変換し、価格と日付の変換を行う
    private ProductDto transformProductToDto(Product product) {
        ProductDto dto = product.convertToProductDto();
        dto.setPrice(applyTax(product.getPrice()));
        dto.setDateAdded(formatDate(product.getDateAdded()));
        dto.setLastUpdated(formatDate(product.getLastUpdated()));
        return dto;
    }

    // 価格に税率を適用し、結果を四捨五入する
    private BigDecimal applyTax(BigDecimal price) {
        return price.multiply(TAX_RATE.add(BigDecimal.ONE)).setScale(2, RoundingMode.HALF_UP);
    }

    // LocalDateTimeを指定されたフォーマットの文字列に変換する
    private String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMATTER);
    }
}
