package com.yuuki.katou.inventory_management_app01.controller;

import com.yuuki.katou.inventory_management_app01.dto.ProductDto;
import com.yuuki.katou.inventory_management_app01.form.ProductForm;
import com.yuuki.katou.inventory_management_app01.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ProductController {

    // 依存関係: ProductService
    private ProductService productService;

    // コンストラクタによる注入

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 全商品データの取得
    @GetMapping("/products")
    public List<ProductDto> getAll() {
        return productService.findAll();
    }

    //特定の商品IDに基づいた商品情報の取得
    // パス変数{id}を使用して、リクエストされた商品のIDを取得する
    @GetMapping("/products/{id}")
    public ProductDto getProductById(@PathVariable("id") int productId) {
        return productService.findById(productId);
    }

    //条件に基づいた商品検索
    // @RequestParamを使用して、クエリパラメータを受け取る　required =false :必須項目ではない。
    @GetMapping("/products/search")
    public List<ProductDto> searchProducts(@RequestParam(required = false) String keyword,
                                           @RequestParam(required = false) String brandName,
                                           @RequestParam(required = false) String categoryName,
                                           @RequestParam(required = false) String storeName,
                                           @RequestParam(required = false) BigDecimal priceLower,
                                           @RequestParam(required = false) BigDecimal priceUpper,
                                           @RequestParam(required = false) String conditionName,
                                           @RequestParam(required = false) String sortOrder) {

        return productService.findByCriteria(keyword, brandName, categoryName, storeName, priceLower, priceUpper, conditionName, sortOrder);
    }

    //商品情報を新規作成
    //@RequestBody　ProductCreateForm型で登録情報を受け取る　
    //@Valid バリデーションの有効化　
    @PostMapping("/products")
    public ResponseEntity<Map<String, String>> createProducts(@RequestBody @Valid ProductForm productForm) {

        //productCreateFormからproductDtoに変換する
        ProductDto productDto = productForm.convertToDto();

        //productService.createProductで登録処理を行い、新規登録された商品のId戻り値として受け取る
        int newProductId = productService.createProduct(productDto);

        //URIの作成
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newProductId)
                .toUri();

        //レスポンスの生成　URIとメッセージを返す
        return ResponseEntity.created(location).body(Map.of("message", "A new Product is created!"));
    }

    //商品情報を更新
    //パスからid(productId)RequestBodyから商品情報を受け取る
    //@Valid　バリデーションの有効化
    @PatchMapping("/products/{id}")
    public ResponseEntity<Map<String, String>> updateProduct(@PathVariable("id") int productId,
                                                             @RequestBody @Valid ProductForm productForm) {
        //productFormをproductDtoに変換
        ProductDto productDto = productForm.convertToDto(productId);

        //更新処理の実行　更新された商品Idを戻り値として受け取る
        int updatedProductId = productService.updateProduct(productDto);
        //URIの作成
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand("")
                .toUri();
        //レスポンスの生成　URIとメッセージを返す
        return ResponseEntity.ok().body(Map.of("message", "Product with ID has been updated"));
    }

    //商品を削除
    //パスからid(productId)を受け取る
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Map<String, String>> deleteEmployeeById(@PathVariable("id") int productId) {

        //削除処理の実行　削除した商品のIdを戻り値として受け取る
        int deletedProductId = productService.deletedProduct(productId);

        //URIの生成
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand("")
                .toUri();

        //レスポンスの生成　URIとメッセージを返す
        return ResponseEntity.ok().body(Map.of("message", "Product with ID has been deleted"));
    }
}
