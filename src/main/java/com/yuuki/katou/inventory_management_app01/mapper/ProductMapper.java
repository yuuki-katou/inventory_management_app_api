package com.yuuki.katou.inventory_management_app01.mapper;

import com.yuuki.katou.inventory_management_app01.entity.Product;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductMapper {

    //すべての商品情報を取得するSQLクエリを定義
    //外部キーとそれに対応する値も取得
    //@ResultMap: resources > ProductMapper.xmlを読み込む
    @Select("SELECT products.product_id, " +
            "products.product_name, " +
            "products.brand_id, " +
            "products.category_id, " +
            "products.model_number, " +
            "products.price, " +
            "products.stock_quantity, " +
            "products.condition_id, " +
            "SUBSTRING(products.description, 1, 50) as description, " +
            "products.image, " +
            "products.store_id, " +
            "products.date_added, " +
            "products.last_updated, " +
            "brands.brand_name, " +
            "categories.category_name, " +
            "conditions.condition_name, " +
            "stores.store_name, " +
            "stores.location, " +
            "stores.telephone_number " +
            "FROM products " +
            "JOIN brands ON products.brand_id = brands.brand_id " +
            "JOIN categories ON products.category_id = categories.category_id " +
            "JOIN conditions ON products.condition_id = conditions.condition_id " +
            "JOIN stores ON products.store_id = stores.store_id")
    @ResultMap("productResultMap")
    List<Product> findAll();


    //特定の商品情報を取得するSQLクエリを定義
    //外部キーとそれに対応する値も取得
    //@ResultMap: resources > ProductMapper.xmlを読み込む
    @Select("SELECT products.*, " +
            "brands.brand_name, " +
            "categories.category_name, " +
            "conditions.condition_name, " +
            "stores.store_name, " +
            "stores.location, " +
            "stores.telephone_number " +
            "FROM products " +
            "JOIN brands ON products.brand_id = brands.brand_id " +
            "JOIN categories ON products.category_id = categories.category_id " +
            "JOIN conditions ON products.condition_id = conditions.condition_id " +
            "JOIN stores ON products.store_id = stores.store_id " +
            "WHERE products.product_id = #{productId}")
    @ResultMap("productResultMap")
    Optional<Product> findById(int productId);


    //複数条件に該当する商品情報を取得するSQLクエリを定義
    //@SelectProvider: mapper > advancedProvider.java
    //@ResultMap: resources > ProductMapper.xmlを読み込む
    @SelectProvider(type = advancedProvider.class, method = "findByCriteria")
    @ResultMap("productResultMap")
    List<Product> findByCriteria(@Param("keyword") String keyword,
                                 @Param("brandName") String brandName,
                                 @Param("categoryName") String categoryName,
                                 @Param("storeName") String storeName,
                                 @Param("priceLower") BigDecimal priceLower,
                                 @Param("priceUpper") BigDecimal priceUpper,
                                 @Param("conditionName") String conditionName,
                                 @Param("sortOrder") String sortOrder);

    //要素から主キーを取得するSQLクエリを定義
    @Select("SELECT ${idColumn} FROM ${tableName} WHERE ${nameColumn} = #{name}")
    Integer getIdByName(@Param("tableName") String tableName,
                        @Param("idColumn") String idColumn,
                        @Param("nameColumn") String nameColumn,
                        @Param("name") String name);


    //商品情報を新規登録するSQLクエリを定義
    //主キーはproductIdに設定
    @Insert("INSERT INTO products " +
            "(product_name, model_number, brand_id, category_id, price, stock_quantity, condition_id, " +
            "description, image, store_id) " +
            "VALUES (#{productName}, #{modelNumber}, #{brandId}, #{categoryId}, #{price}, #{stockQuantity}, #{conditionId}, " +
            "#{description}, #{image}, #{storeId})")
    @Options(useGeneratedKeys = true, keyProperty = "productId")
    int create(Product product);


    //商品情報を更新するSQLクエリを定義
    @Update("UPDATE products SET product_name = #{productName}, " +
            "model_number = #{modelNumber}, " +
            "brand_id = #{brandId}, category_id = #{categoryId}, " +
            "price = #{price}, stock_quantity = #{stockQuantity}, condition_id = #{conditionId}, " +
            "description = #{description}, image = #{image}, store_id = #{storeId} " +
            "WHERE product_id = #{productId}")
    int updateProduct(Product product);


    //商品情報を削除するSQLクエリを定義
    @Delete("DELETE FROM products WHERE product_id = #{id}")
    int deleteById(int productId);
}

