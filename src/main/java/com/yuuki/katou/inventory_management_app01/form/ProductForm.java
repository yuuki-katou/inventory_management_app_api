package com.yuuki.katou.inventory_management_app01.form;

import com.yuuki.katou.inventory_management_app01.dto.ProductDto;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

public class ProductForm {

    @Size(max = 50, message = "商品名は50文字以内で入力してください。")
    @NotBlank(message = "商品名は必須項目です。")
    private String productName;

    @Size(max = 25, message = "ブランド名は25文字以内で入力してください。")
    @NotBlank(message = "ブランド名は必須項目です。")
    private String brand;

    @Size(max = 10, message = "型番は10文字以内で入力してください。")
    @NotBlank(message = "型番は必須項目です。")
    private String modelNumber;

    @Size(max = 25, message = "カテゴリは25文字以内で入力してください。")
    @NotBlank(message = "カテゴリは必須項目です。")
    private String category;

    @DecimalMin(value = "0.0", inclusive = false, message = "価格は0以上で入力してください。")
    @DecimalMax(value = "1000000.0", message = "価格は1,000,000以下で入力してください。")
    @NotNull(message = "価格は必須項目です。")
    private BigDecimal price;

    @Min(value = 0, message = "在庫数は0以上で入力してください。")
    @NotNull(message = "在庫数は必須項目です。")
    private int stockQuantity;

    @Size(max = 25, message = "商品の状態は25文字で入力してください。")
    @NotBlank(message = "商品の状態は必須項目です。")
    private String condition;

    @Size(max = 1000, message = "説明文は1000文字以内で入力してください。")
    private String description;
    private String image;

    @Size(max = 25, message = "店舗名は25文字以内で入力してください。")
    @NotBlank(message = "店舗名は必須項目です。")
    private String store;


    public ProductForm(String productName, String brand, String modelNumber, String category, BigDecimal price, int stockQuantity, String condition, String description, String image, String store) {
        this.productName = productName;
        this.brand = brand;
        this.modelNumber = modelNumber;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.condition = condition;
        this.description = description;
        this.image = image;
        this.store = store;
    }
    

    public String getProductName() {
        return productName;
    }

    public String getBrand() {
        return brand;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public String getCondition() {
        return condition;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getStore() {
        return store;
    }


    public ProductDto convertToDto() {
        ModelMapper modelMapper = new ModelMapper();
        //曖昧な型変換は無視される　参考URL :https://stackoverflow.com/questions/49831753/modelmapper-matches-multiple-source-property-hierarchies）
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(this, ProductDto.class);
    }

    public ProductDto convertToDto(int id) {
        ProductDto productDto = this.convertToDto();
        productDto.setProductId(id);
        return productDto;
    }
}
