package com.yuuki.katou.inventory_management_app01.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.math.BigDecimal;

public class advancedProvider {

    //定数　税率
    private static final BigDecimal TAX_RATE = new BigDecimal("1.10");

    // 複数の検索条件に基づいて商品情報を取得するSQLクエリを生成するメソッド
    public String findByCriteria(@Param("keyword") String keyword,
                                 @Param("brandName") String brandName,
                                 @Param("categoryName") String categoryName,
                                 @Param("storeName") String storeName,
                                 @Param("priceLower") BigDecimal priceLower,
                                 @Param("priceUpper") BigDecimal priceUpper,
                                 @Param("conditionName") String conditionName,
                                 @Param("sortOrder") String sortOrder) {
        //クエリの作成
        return new SQL() {{
            // SELECT句: productsと関連する情報を選択
            SELECT("products.*, brands.brand_name, categories.category_name, conditions.condition_name, stores.store_name, stores.location, stores.telephone_number");
            // FROM句: productsテーブルを基準とする
            FROM("products");
            // JOIN句: productsテーブルを関連するテーブルと結合
            JOIN("brands ON products.brand_id = brands.brand_id");
            JOIN("categories ON products.category_id = categories.category_id");
            JOIN("conditions ON products.condition_id = conditions.condition_id");
            JOIN("stores ON products.store_id = stores.store_id");

            // WHERE句: キーワードに基づいて動的に条件を追加
            if (keyword != null && !keyword.trim().isEmpty()) {
                String[] keywords = keyword.split("[ +\\u3000]+");
                for (String key : keywords) {
                    WHERE("(products.product_name LIKE CONCAT('%', '" + key + "', '%') " +
                            "OR products.description LIKE CONCAT('%', '" + key + "', '%') " +
                            "OR products.model_number LIKE CONCAT('%', '" + key + "', '%') " +
                            "OR brands.brand_name LIKE CONCAT('%', '" + key + "', '%')" +
                            "OR stores.store_name LIKE CONCAT('%', '" + key + "', '%')" +
                            "OR conditions.condition_name LIKE CONCAT('%', '" + key + "', '%')" +
                            "OR categories.category_name LIKE CONCAT('%', '" + key + "', '%'))");
                }
            }
            //ブランド名検索
            if (brandName != null) {
                WHERE("brands.brand_name = #{brandName}");
            }
            //カテゴリ検索
            if (categoryName != null) {
                WHERE("categories.category_name = #{categoryName}");
            }
            //取扱店舗検索
            if (storeName != null) {
                WHERE("stores.store_name = #{storeName}");
            }
            //価格検索
            if (priceLower != null && priceUpper != null) {
                WHERE("price *" + TAX_RATE + " BETWEEN #{priceLower} AND #{priceUpper}");
            } else if (priceLower != null) {
                WHERE("price *" + TAX_RATE + " >= #{priceLower}");
            } else if (priceUpper != null) {
                WHERE("price * " + TAX_RATE + " <= #{priceUpper}");
            }
            //状態検索
            if (conditionName != null) {
                WHERE("conditions.condition_name = #{conditionName}");
            }

            // ソート条件の設定
            if ("新着順".equals(sortOrder)) {
                ORDER_BY("date_added DESC");
            } else if ("更新日順".equals(sortOrder)) {
                ORDER_BY("last_updated DESC");
            } else if ("価格の安い順".equals(sortOrder)) {
                ORDER_BY("price ASC");
            } else if ("価格の高い順".equals(sortOrder)) {
                ORDER_BY("price DESC");
            }
        }}.toString();
    }
}
