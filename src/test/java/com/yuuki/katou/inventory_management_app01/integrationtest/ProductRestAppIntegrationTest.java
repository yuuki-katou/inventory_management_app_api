package com.yuuki.katou.inventory_management_app01.integrationtest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@SpringBootTest
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRestAppIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DataSet({"datasets/01_brands.yml", "datasets/02_categories.yml", "datasets/03_conditions.yml", "datasets/04_stores.yml", "datasets/05_products.yml"})
    @Transactional
    void 全ての商品情報を取得した際にステータスコード200を返すこと() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        // 期待されるJSONレスポンス
        String expectedJson = """
                [
                    {
                        "productId": 1,
                        "productName": "商品1",
                        "modelNumber": "MD-1001",
                        "brand": "ブランド1",
                        "category": "エレキギター",
                        "price": 1100.00,
                        "stockQuantity": 20,
                        "condition": "新品",
                        "description": "商品1の説明",
                        "image": "image1.jpg",
                        "store": "店舗名1",
                        "storeLocation": "所在地1",
                        "storeTelephoneNumber": "電話番号1",
                        "dateAdded": "2023-01-01 00:00:00",
                        "lastUpdated": "2023-01-02 00:00:00"
                    },
                    {
                        "productId": 2,
                        "productName": "商品2",
                        "modelNumber": "MD-1002",
                        "brand": "ブランド2",
                        "category": "アコースティックギター",
                        "price": 2200.00,
                        "stockQuantity": 15,
                        "condition": "中古良好",
                        "description": "商品2の説明",
                        "image": "image2.jpg",
                        "store": "店舗名2",
                        "storeLocation": "所在地2",
                        "storeTelephoneNumber": "電話番号2",
                        "dateAdded": "2023-02-01 00:00:00",
                        "lastUpdated": "2023-02-02 00:00:00"
                    },
                    {
                        "productId": 3,
                        "productName": "商品3",
                        "modelNumber": "MD-1003",
                        "brand": "ブランド3",
                        "category": "ベースギター",
                        "price": 3300.00,
                        "stockQuantity": 10,
                        "condition": "中古可",
                        "description": "商品3の説明",
                        "image": "image3.jpg",
                        "store": "店舗名3",
                        "storeLocation": "所在地3",
                        "storeTelephoneNumber": "電話番号3",
                        "dateAdded": "2023-03-01 00:00:00",
                        "lastUpdated": "2023-03-02 00:00:00"
                    }
                ]

                                                """;

        // 実際のレスポンスを期待されるJSONと比較
        JSONAssert.assertEquals(expectedJson, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet({"datasets/01_brands.yml", "datasets/02_categories.yml", "datasets/03_conditions.yml", "datasets/04_stores.yml", "datasets/05_products.yml"})
    @Transactional
    void 存在するIDを指定して該当する商品情報を取得した際にステータスコードが200を返すこと() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/products/1")).
                andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                    {
                        "productId": 1,
                        "productName": "商品1",
                        "modelNumber": "MD-1001",
                        "brand": "ブランド1",
                        "category": "エレキギター",
                        "price": 1100.00,
                        "stockQuantity": 20,
                        "condition": "新品",
                        "description": "商品1の説明",
                        "image": "image1.jpg",
                        "store": "店舗名1",
                        "storeLocation": "所在地1",
                        "storeTelephoneNumber": "電話番号1",
                        "dateAdded": "2023-01-01 00:00:00",
                        "lastUpdated": "2023-01-02 00:00:00"
                    }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet({"datasets/01_brands.yml", "datasets/02_categories.yml", "datasets/03_conditions.yml", "datasets/04_stores.yml", "datasets/05_products.yml"})
    @Transactional
    void 検索パラメーターを含むリクエストで商品情報を取得した際にステータスコード200を返すこと() throws Exception {
        // 検索パラメーターを設定
        String keyword = "商品";
        String brandName = "ブランド1";
        String categoryName = "エレキギター";
        String storeName = "店舗名1";
        String priceLower = "1000";
        String priceUpper = "2000";
        String conditionName = "新品";
        String sortOrder = "価格の低い順";

        // HTTP GETリクエストを送信し、レスポンスを取得
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/products/search")
                        .param("keyword", keyword)
                        .param("brandName", brandName)
                        .param("categoryName", categoryName)
                        .param("storeName", storeName)
                        .param("priceLower", priceLower)
                        .param("priceUpper", priceUpper)
                        .param("conditionName", conditionName)
                        .param("sortOrder", sortOrder))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        // 期待されるJSONレスポンス
        String expectedJson = """ 
                [
                    {
                        "productId": 1,
                        "productName": "商品1",
                        "modelNumber": "MD-1001",
                        "brand": "ブランド1",
                        "category": "エレキギター",
                        "price": 1100.00,
                        "stockQuantity": 20,
                        "condition": "新品",
                        "description": "商品1の説明",
                        "image": "image1.jpg",
                        "store": "店舗名1",
                        "storeLocation": "所在地1",
                        "storeTelephoneNumber": "電話番号1",
                        "dateAdded": "2023-01-01 00:00:00",
                        "lastUpdated": "2023-01-02 00:00:00"
                    }
                ]
                """;

        // 実際のレスポンスを期待されるJSONと比較
        JSONAssert.assertEquals(expectedJson, response, JSONCompareMode.STRICT);
    }


    @Test
    @DataSet({"datasets/01_brands.yml", "datasets/02_categories.yml", "datasets/03_conditions.yml", "datasets/04_stores.yml", "datasets/05_products.yml"})
    @ExpectedDataSet(value = "datasets/06_after_create_products.yml", ignoreCols = {"product_id", "date_added", "last_updated"})
    @Transactional
    void 商品情報を新規登録した際にステータスコード201を返すこと() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "productName": "商品4",
                                        "brand": "ブランド2",
                                        "modelNumber": "MD-1004",
                                        "category": "アコースティックギター",
                                        "price": 4000.0,
                                        "stockQuantity": 8,
                                        "condition": "新品",
                                        "description": "商品4の説明",
                                        "image": "image4.jpg",
                                        "store": "店舗名1"
                                    }
                                """))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                   "message": "A new Product is created!"
                }
                """, response, JSONCompareMode.STRICT);
    }


    @Test
    @DataSet({"datasets/01_brands.yml", "datasets/02_categories.yml", "datasets/03_conditions.yml", "datasets/04_stores.yml", "datasets/05_products.yml"})
    @ExpectedDataSet(value = "datasets/07_after_update_products.yml", ignoreCols = {"date_added", "last_updated"})
    @Transactional
    void 商品情報を更新した際にステータスコード200を返すこと() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.patch("/products/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "productName": "（更新済）商品2",
                                    "brand": "ブランド2",
                                    "category": "アコースティックギター",
                                    "modelNumber": "MD-1002",
                                    "price": 2000.00,
                                    "stockQuantity": 15,
                                    "condition": "中古良好",
                                    "description": "更新しました",
                                    "image": "image2.jpg",
                                    "store": "店舗名2"
                                }

                                                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                   "message": "Product with ID has been updated"
                }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet({"datasets/01_brands.yml", "datasets/02_categories.yml", "datasets/03_conditions.yml", "datasets/04_stores.yml", "datasets/05_products.yml"})
    public void 存在しない商品IDを送信した場合ステータスコード404とエラーメッセージが返されること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.patch("/products/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "productName": "商品4",
                                        "brand": "ブランド2",
                                        "modelNumber": "MD-1004",
                                        "category": "アコースティックギター",
                                        "price": 4000.0,
                                        "stockQuantity": 8,
                                        "condition": "新品",
                                        "description": "商品4の説明",
                                        "image": "image4.jpg",
                                        "store": "店舗名1"
                                    }
                                """))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                    "error": "Not Found",
                    "path": "/products/999",
                    "status": "404",
                    "message": "ProductID：999の商品は見つかりませんでした。"
                }
                """, response, JSONCompareMode.LENIENT);
    }

    @Test
    @Transactional
    public void 必須フィールドが欠けているときにステータスコード400とエラーメッセージを返す() throws Exception {
        // 無効なJSONデータ
        String response = mockMvc.perform(MockMvcRequestBuilders.patch("/products/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "productName": "",
                                    "brand": "",
                                    "category": "",
                                    "modelNumber": "",
                                    "price": "",
                                    "stockQuantity": "",
                                    "condition": "",
                                    "description": "",
                                    "image": "",
                                    "store": ""
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                    "timestamp": "2023-12-15T11:50:14.111467200+09:00[Asia/Tokyo]",
                    "status": "400",
                    "error": "Bad Request",
                    "message": "Validation failed",
                    "details": {
                        "condition": "商品の状態は必須項目です。",
                        "price": "価格は必須項目です。",
                        "modelNumber": "型番は必須項目です。",
                        "store": "店舗名は必須項目です。",
                        "category": "カテゴリは必須項目です。",
                        "brand": "ブランド名は必須項目です。",
                        "productName": "商品名は必須項目です。"
                    },
                    "path": "/products/2"
                }
                """, response, new CustomComparator(JSONCompareMode.STRICT,
                new Customization("timestamp", (o1, o2) -> true)));
    }

    @Test
    @Transactional
    public void マイナスの数値を入力した場合エラーコード400とエラーメッセージを返すこと() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.patch("/products/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "productName": "",
                                    "brand": "",
                                    "category": "",
                                    "modelNumber": "",
                                    "price": "-1.0",
                                    "stockQuantity": -1,
                                    "condition": "",
                                    "description": "",
                                    "image": "",
                                    "store": ""
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                    "timestamp": "2023-12-15T12:04:13.739028300+09:00[Asia/Tokyo]",
                    "status": "400",
                    "error": "Bad Request",
                    "message": "Validation failed",
                    "details": {
                        "condition": "商品の状態は必須項目です。",
                        "price": "価格は0以上で入力してください。",
                        "stockQuantity": "在庫数は0以上で入力してください。",
                        "modelNumber": "型番は必須項目です。",
                        "store": "店舗名は必須項目です。",
                        "category": "カテゴリは必須項目です。",
                        "brand": "ブランド名は必須項目です。",
                        "productName": "商品名は必須項目です。"
                    },
                    "path": "/products/2"
                }
                                """, response, new CustomComparator(JSONCompareMode.STRICT,
                new Customization("timestamp", (o1, o2) -> true)));
    }

    @Test
    @Transactional
    public void 入力制限を超えた文字を送信した場合ステータスコード400とエラーメッセージを返す() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.patch("/products/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "productName": "この商品名は51文字を超えています。この商品名は51文字を超えています。この商品名は51文字を超えています。",
                                    "brand": "このブランド名は26文字を超えています。このブランド名は26文字を超えています。",
                                    "modelNumber": "この型番は11文字を超えています。",
                                    "category": "このカテゴリは26文字を超えています。このカテゴリは26文字を超えています。",
                                    "price": "1000001.0",
                                    "stockQuantity": 1000000000,
                                    "condition": "この商品の状態は26文字を超えています。この商品の状態は26文字を超えています。",
                                    "description": "この説明文は1001文字を超えています...（1001文字以上のテキスト）...",
                                    "image": "この画像ファイル名は非常に長いため、バリデーションルールに違反します。",
                                    "store": "この店舗名は26文字を超えています。この店舗名は26文字を超えています。"
                                }
                                                                """))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                    "timestamp": "2023-12-15T12:06:53.063354900+09:00[Asia/Tokyo]",
                    "status": "400",
                    "error": "Bad Request",
                    "message": "Validation failed",
                    "details": {
                        "condition": "商品の状態は25文字で入力してください。",
                        "price": "価格は1,000,000以下で入力してください。",
                        "modelNumber": "型番は10文字以内で入力してください。",
                        "store": "店舗名は25文字以内で入力してください。",
                        "category": "カテゴリは25文字以内で入力してください。",
                        "brand": "ブランド名は25文字以内で入力してください。",
                        "productName": "商品名は50文字以内で入力してください。"
                    },
                    "path": "/products/2"
                }
                """, response, new CustomComparator(JSONCompareMode.STRICT,
                new Customization("timestamp", (o1, o2) -> true)));
    }


    @Test
    @DataSet({"datasets/01_brands.yml", "datasets/02_categories.yml", "datasets/03_conditions.yml", "datasets/04_stores.yml", "datasets/05_products.yml"})
    @ExpectedDataSet(value = "datasets/08_after_delete_products.yml")
    @Transactional
    void 存在するIDを指定して商品情報を削除した際にステータスコード200を返すこと() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.delete("/products/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                "message": "Product with ID has been deleted"
                }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet({"datasets/01_brands.yml", "datasets/02_categories.yml", "datasets/03_conditions.yml", "datasets/04_stores.yml", "datasets/05_products.yml"})
    @Transactional
    void 存在しないIDを削除した際のレスポンスが404を返すこと() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

