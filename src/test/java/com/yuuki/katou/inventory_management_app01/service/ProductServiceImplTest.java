package com.yuuki.katou.inventory_management_app01.service;

import com.yuuki.katou.inventory_management_app01.dto.ProductDto;
import com.yuuki.katou.inventory_management_app01.entity.Product;
import com.yuuki.katou.inventory_management_app01.exception.DatabaseOperationException;
import com.yuuki.katou.inventory_management_app01.exception.InternalConsistencyException;
import com.yuuki.katou.inventory_management_app01.exception.ResourceNotFoundException;
import com.yuuki.katou.inventory_management_app01.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductMapper productMapper;


    @Test
    void 全ての商品情報が載ったリストが正常に返されること() {
        // モックデータの準備
        List<Product> mockProducts = Arrays.asList(
                new Product(1, "商品1", "MD-1001", 1, 1, BigDecimal.valueOf(1000.00).setScale(2), 20, 1, "商品1の説明", "image1.jpg", 1, LocalDateTime.of(2023, 1, 1, 0, 0), LocalDateTime.of(2023, 1, 2, 0, 0), "ブランド1", "エレキギター", "新品", "店舗名1", "所在地1", "電話番号1"),
                new Product(2, "商品2", "MD-1002", 2, 2, BigDecimal.valueOf(2000.00).setScale(2), 15, 2, "商品2の説明", "image2.jpg", 2, LocalDateTime.of(2023, 2, 1, 0, 0), LocalDateTime.of(2023, 2, 2, 0, 0), "ブランド2", "アコースティックギター", "中古良好", "店舗名2", "所在地2", "電話番号2"),
                new Product(3, "商品3", "MD-1003", 3, 3, BigDecimal.valueOf(3000.00).setScale(2), 10, 3, "商品3の説明", "image3.jpg", 3, LocalDateTime.of(2023, 3, 1, 0, 0), LocalDateTime.of(2023, 3, 2, 0, 0), "ブランド3", "ベースギター", "中古可", "店舗名3", "所在地3", "電話番号3")
        );

        // モックの動作を設定
        when(productMapper.findAll()).thenReturn(mockProducts);

        // 実行と検証
        List<ProductDto> result = productService.findAll();
        assertThat(result)
                .hasSize(3)
                .contains(
                        new ProductDto(1, "商品1", "MD-1001", "ブランド1", "エレキギター", BigDecimal.valueOf(1100.00).setScale(2), 20, "新品", "商品1の説明", "image1.jpg", "店舗名1", "所在地1", "電話番号1", "2023-01-01 00:00:00", "2023-01-02 00:00:00"),
                        new ProductDto(2, "商品2", "MD-1002", "ブランド2", "アコースティックギター", BigDecimal.valueOf(2200.00).setScale(2), 15, "中古良好", "商品2の説明", "image2.jpg", "店舗名2", "所在地2", "電話番号2", "2023-02-01 00:00:00", "2023-02-02 00:00:00"),
                        new ProductDto(3, "商品3", "MD-1003", "ブランド3", "ベースギター", BigDecimal.valueOf(3300.00).setScale(2), 10, "中古可", "商品3の説明", "image3.jpg", "店舗名3", "所在地3", "電話番号3", "2023-03-01 00:00:00", "2023-03-02 00:00:00")
                );
    }

    @Test
    void 商品情報がまったく存在しない場合にResourceNotFoundExceptionが返されること() {
        // 商品情報が存在しない場合のテスト
        when(productMapper.findAll()).thenReturn(Arrays.asList());

        // 実行と例外の検証
        assertThatThrownBy(() -> productService.findAll())
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("商品情報が見つかりませんでした。");
    }

    @Test
    void 指定したIDに該当する商品が正常に返されること() {
        // モックデータと期待値の準備
        Optional<Product> mockProducts = Optional.of(new Product(1, "商品1", "MD-1001", 1, 1, BigDecimal.valueOf(1000.00).setScale(2), 20, 1, "商品1の説明", "image1.jpg", 1, LocalDateTime.of(2023, 1, 1, 0, 0), LocalDateTime.of(2023, 1, 2, 0, 0), "ブランド1", "エレキギター", "新品", "店舗名1", "所在地1", "電話番号1"));
        ProductDto expectedProduct = new ProductDto(1, "商品1", "MD-1001", "ブランド1", "エレキギター", BigDecimal.valueOf(1100.00).setScale(2), 20, "新品", "商品1の説明", "image1.jpg", "店舗名1", "所在地1", "電話番号1", "2023-01-01 00:00:00", "2023-01-02 00:00:00");

        // モックの動作を設定
        when(productMapper.findById(1)).thenReturn(mockProducts);

        // 実行と検証
        ProductDto result = productService.findById(1);
        assertThat(result)
                .isEqualTo(expectedProduct);
    }

    // findById メソッドで指定されたIDの商品が見つからない場合のテスト
    @Test
    void 指定したIDに該当する商品が見つからなかった場合ResourceNotFoundが正常に返されること() {
        // モックの動作を設定（空のOptionalを返す）
        when(productMapper.findById(1)).thenReturn(Optional.empty());

        // 実行と例外の検証
        assertThatThrownBy(() -> productService.findById(1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("ProductID：1の商品は見つかりませんでした。");
    }


    @Test
    void 複数の検索条件に合う商品情報が返されること() {
        // 検索条件とモックデータの準備
        String keyword = "商品1";
        String brandName = "ブランド1";
        String categoryName = "エレキギター";
        String storeName = "店舗名1";
        BigDecimal priceLower = new BigDecimal("900");
        BigDecimal priceUpper = new BigDecimal("1100");
        String conditionName = "新品";
        String sortOrder = "価格の低い順";
        List<Product> mockProducts = List.of(
                new Product(1, "商品1", "MD-1001", 1, 1, BigDecimal.valueOf(1000.00).setScale(2), 20, 1, "商品1の説明", "image1.jpg", 1, LocalDateTime.of(2023, 1, 1, 0, 0), LocalDateTime.of(2023, 1, 2, 0, 0), "ブランド1", "エレキギター", "新品", "店舗名1", "所在地1", "電話番号1")
        );

        // モックの動作を設定
        when(productMapper.findByCriteria(keyword, brandName, categoryName, storeName, priceLower, priceUpper, conditionName, sortOrder)).thenReturn(mockProducts);

        // 実行と検証
        List<ProductDto> result = productService.findByCriteria(keyword, brandName, categoryName, storeName, priceLower, priceUpper, conditionName, sortOrder);
        assertThat(result).hasSize(1).contains(
                new ProductDto(1, "商品1", "MD-1001", "ブランド1", "エレキギター", BigDecimal.valueOf(1100.00).setScale(2), 20, "新品", "商品1の説明", "image1.jpg", "店舗名1", "所在地1", "電話番号1", "2023-01-01 00:00:00", "2023-01-02 00:00:00")
        );
    }


    @Test
    void 指定した検索条件に該当する商品が見つからなかった場合ResourceNotFoundExceptionが正常に返されること() {
        // モックの動作を設定（空のリストを返す）
        when(productMapper.findByCriteria(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(Arrays.asList());

        // 実行と例外の検証
        assertThatThrownBy(() -> productService.findByCriteria("keyword", "brand", "category", "store", BigDecimal.ONE, BigDecimal.TEN, "condition", "sortOrder"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("指定された検索条件に一致する商品は見つかりませんでした。");
    }

    @Test
    void 商品情報の登録処理が正常にできること() {
        // テストデータの準備
        ProductDto productDto = new ProductDto(0, "商品4", "MD-1004", "ブランド2", "アコースティックギター", BigDecimal.valueOf(4000.00).setScale(2), 8, "新品", "商品4の説明", "image4.jpg", "店舗名1", null, null, null, null);

        // 各IDを取得するためのモックの設定
        when(productMapper.getIdByName("brands", "brand_id", "brand_name", "ブランド2")).thenReturn(2);
        when(productMapper.getIdByName("categories", "category_id", "Category_name", "アコースティックギター")).thenReturn(2);
        when(productMapper.getIdByName("stores", "store_id", "Store_name", "店舗名1")).thenReturn(1);
        when(productMapper.getIdByName("conditions", "condition_id", "condition_name", "新品")).thenReturn(1);

        // ProductMapper.createのモックの設定
        doAnswer(invocation -> {
            Product product = invocation.getArgument(0, Product.class);
            ReflectionTestUtils.setField(product, "productId", 4);
            return 1;
        }).when(productMapper).create(any(Product.class));

        // 実行と検証
        int productId = productService.createProduct(productDto);
        assertThat(productId).isEqualTo(4);
    }

    @Test
    void 商品情報の登録処理に失敗した場合DatabaseOperationExceptionを正常に返すこと() {
        // テストデータの準備
        ProductDto productDto = new ProductDto(0, "商品4", "MD-1004", "ブランド2", "アコースティックギター", BigDecimal.valueOf(4000.00), 8, "新品", "商品4の説明", "image4.jpg", "店舗名1", null, null, null, null);
        // モックの振る舞いを設定
        when(productMapper.create(any(Product.class))).thenReturn(0);
        // 実行と例外の検証
        assertThatThrownBy(() -> productService.createProduct(productDto))
                .isInstanceOf(DatabaseOperationException.class)
                .hasMessageContaining("Failed to register product");
    }

    @Test
    void 商品情報の更新処理が正常に行われること() {
        // テストデータの準備
        ProductDto productDto = new ProductDto(4, "商品4", "MD-1004", "ブランド2", "アコースティックギター", BigDecimal.valueOf(4000.00).setScale(2), 8, "新品", "商品4の説明", "image4.jpg", "店舗名1", null, null, null, null);

        // 各IDを取得するためのモックの振る舞いを設定
        when(productMapper.findById(productDto.getProductId())).thenReturn(java.util.Optional.of(new Product()));
        when(productMapper.getIdByName("brands", "brand_id", "brand_name", "ブランド2")).thenReturn(2);
        when(productMapper.getIdByName("categories", "category_id", "Category_name", "アコースティックギター")).thenReturn(2);
        when(productMapper.getIdByName("stores", "store_id", "Store_name", "店舗名1")).thenReturn(1);
        when(productMapper.getIdByName("conditions", "condition_id", "condition_name", "新品")).thenReturn(1);
        // updateProductのモックの振る舞いを設定（正常に更新）
        when(productMapper.updateProduct(any(Product.class))).thenReturn(1);


        // 実行と検証
        int productId = productService.updateProduct(productDto);
        assertThat(productId).isEqualTo(4);

    }

    @Test
    void 更新処理に失敗した場合DatabaseOperationExceptionを正常に返すこと() {
        // テストデータの準備
        ProductDto productDto = new ProductDto(1, "商品4", "MD-1004", "ブランド2", "アコースティックギター", BigDecimal.valueOf(4000.00), 8, "新品", "商品4の説明", "image4.jpg", "店舗名1", null, null, null, null);
        // 各IDを取得するためのモックの振る舞いを設定
        when(productMapper.findById(productDto.getProductId())).thenReturn(java.util.Optional.of(new Product()));
        when(productMapper.getIdByName("brands", "brand_id", "brand_name", "ブランド2")).thenReturn(2);
        when(productMapper.getIdByName("categories", "category_id", "Category_name", "アコースティックギター")).thenReturn(2);
        when(productMapper.getIdByName("stores", "store_id", "Store_name", "店舗名1")).thenReturn(1);
        when(productMapper.getIdByName("conditions", "condition_id", "condition_name", "新品")).thenReturn(1);
        // updateProductのモックの振る舞いを設定（更新失敗）
        when(productMapper.updateProduct(any(Product.class))).thenReturn(0);

        // 実行と例外の検証
        assertThatThrownBy(() -> productService.updateProduct(productDto))
                .isInstanceOf(DatabaseOperationException.class)
                .hasMessageContaining("Failed to update product");
    }

    @Test
    void 商品情報を削除できること() {
        // テストデータの準備
        ProductDto productDto = new ProductDto(4, "商品4", "MD-1004", "ブランド2", "アコースティックギター", BigDecimal.valueOf(4000.00).setScale(2), 8, "新品", "商品4の説明", "image4.jpg", "店舗名1", null, null, null, null);
        // findByIdとdeleteByIdのモックの振る舞いを設定（正常に削除）
        when(productMapper.findById(productDto.getProductId())).thenReturn(java.util.Optional.of(new Product()));
        when(productMapper.deleteById(4)).thenReturn(1);
        // 実行と検証
        int productId = productService.deletedProduct(4);
        assertThat(productId).isEqualTo(4);

    }

    // deletedProduct メソッドで商品削除に失敗した場合のテスト
    @Test
    void 商品情報の削除に失敗した場合DatabaseOperationExceptionを正常に返すこと() {
        // findByIdとdeleteByIdのモックの振る舞いを設定（削除失敗）
        when(productMapper.findById(1)).thenReturn(Optional.of(new Product()));
        when(productMapper.deleteById(1)).thenReturn(0);

        // 実行と例外の検証
        assertThatThrownBy(() -> productService.deletedProduct(1))
                .isInstanceOf(DatabaseOperationException.class)
                .hasMessageContaining("Failed to delete product");
    }

    // setProductDetails メソッドでIDが見つからない場合のテスト
    @Test
    void 要素からIdを見つけることができない場合InternalConsistencyExceptionを正常に返すこと() {
        // テストデータの準備
        Product product = new Product(1, "商品4", "MD-1004", 1, 1, BigDecimal.valueOf(4000.00), 8, 1, "商品4の説明", "image4.jpg", 1, LocalDateTime.now(), LocalDateTime.now(), "ブランド2", "アコースティックギター", "新品", "店舗名1", "所在地1", "電話番号1");
        // getIdByNameのモックの振る舞いを設定（IDが見つからない）
        when(productMapper.getIdByName("brands", "brand_id", "brand_name", "ブランド2")).thenReturn(null);
        // 実行と例外の検証
        assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(productService, "setProductDetails", product))
                .isInstanceOf(InternalConsistencyException.class)
                .hasMessageContaining("ブランド情報の処理中に問題が発生しました。");
    }
}