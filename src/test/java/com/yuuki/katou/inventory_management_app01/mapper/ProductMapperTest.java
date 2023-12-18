package com.yuuki.katou.inventory_management_app01.mapper;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.yuuki.katou.inventory_management_app01.entity.Product;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductMapperTest {


    @Autowired
    ProductMapper productMapper;

    @Test
    @DataSet({"datasets/01_brands.yml", "datasets/02_categories.yml", "datasets/03_conditions.yml", "datasets/04_stores.yml", "datasets/05_products.yml"})
    @Transactional
    void すべての商品が取得できること() {
        List<Product> expectedProducts = Arrays.asList(
                new Product(1, "商品1", "MD-1001", 1, 1, BigDecimal.valueOf(1000.00).setScale(2), 20, 1, "商品1の説明", "image1.jpg", 1, LocalDateTime.of(2023, 1, 1, 0, 0), LocalDateTime.of(2023, 1, 2, 0, 0), "ブランド1", "エレキギター", "新品", "店舗名1", "所在地1", "電話番号1"),
                new Product(2, "商品2", "MD-1002", 2, 2, BigDecimal.valueOf(2000.00).setScale(2), 15, 2, "商品2の説明", "image2.jpg", 2, LocalDateTime.of(2023, 2, 1, 0, 0), LocalDateTime.of(2023, 2, 2, 0, 0), "ブランド2", "アコースティックギター", "中古良好", "店舗名2", "所在地2", "電話番号2"),
                new Product(3, "商品3", "MD-1003", 3, 3, BigDecimal.valueOf(3000.00).setScale(2), 10, 3, "商品3の説明", "image3.jpg", 3, LocalDateTime.of(2023, 3, 1, 0, 0), LocalDateTime.of(2023, 3, 2, 0, 0), "ブランド3", "ベースギター", "中古可", "店舗名3", "所在地3", "電話番号3")
        );
        List<Product> actualProducts = productMapper.findAll();
        assertThat(actualProducts)
                .hasSize(3)
                .usingRecursiveFieldByFieldElementComparator().usingRecursiveComparison().isEqualTo(expectedProducts);

    }

    @Test
    @DataSet(value = "datasets/09_empty_products.yml")
    @Transactional
    void 商品情報が何もない場合に空のリストを返す() {
        List<Product> actualProducts = productMapper.findAll();
        assertThat(actualProducts).isEmpty();
    }

    @Test
    @DataSet({"datasets/01_brands.yml", "datasets/02_categories.yml", "datasets/03_conditions.yml", "datasets/04_stores.yml", "datasets/05_products.yml"})
    @Transactional
    void 特定の商品情報を取得できること() {
        Optional<Product> actualProducts = productMapper.findById(1);
        assertThat(actualProducts)
                .isPresent()
                .contains(
                        new Product(1, "商品1", "MD-1001", 1, 1, BigDecimal.valueOf(1000.00).setScale(2), 20, 1, "商品1の説明", "image1.jpg", 1, LocalDateTime.of(2023, 1, 1, 0, 0), LocalDateTime.of(2023, 1, 2, 0, 0), "ブランド1", "エレキギター", "新品", "店舗名1", "所在地1", "電話番号1")
                );
    }

    @Test
    @DataSet({"datasets/01_brands.yml", "datasets/02_categories.yml", "datasets/03_conditions.yml", "datasets/04_stores.yml", "datasets/05_products.yml"})
    @Transactional
    void 商品IDが存在しない場合返ってくるデータが空であること() {
        Optional<Product> actualProducts = productMapper.findById(999);
        assertThat(actualProducts)
                .isEmpty();
    }

    @Test
    @DataSet({"datasets/01_brands.yml", "datasets/02_categories.yml", "datasets/03_conditions.yml", "datasets/04_stores.yml", "datasets/05_products.yml"})
    @Transactional
    void 複数条件に該当する商品情報を取得できること() {

        String keyword = "商品1";
        String brandName = "ブランド1";
        String categoryName = "エレキギター";
        String storeName = "店舗名1";
        BigDecimal priceLower = new BigDecimal("900");
        BigDecimal priceUpper = new BigDecimal("1100");
        String conditionName = "新品";
        String sortOrder = "価格の低い順";

        List<Product> actualProducts = productMapper.findByCriteria(keyword, brandName, categoryName, storeName, priceLower, priceUpper, conditionName, sortOrder);
        assertThat(actualProducts)
                .hasSize(1)
                .contains(
                        new Product(1, "商品1", "MD-1001", 1, 1, BigDecimal.valueOf(1000.00).setScale(2), 20, 1, "商品1の説明", "image1.jpg", 1, LocalDateTime.of(2023, 1, 1, 0, 0), LocalDateTime.of(2023, 1, 2, 0, 0), "ブランド1", "エレキギター", "新品", "店舗名1", "所在地1", "電話番号1")
                );
    }

    @Test
    @DataSet({"datasets/01_brands.yml", "datasets/02_categories.yml", "datasets/03_conditions.yml", "datasets/04_stores.yml", "datasets/05_products.yml"})
    @Transactional
    void 条件に該当する商品がない場合データが空であること() {
        String keyword = "存在しない商品";
        String brandName = "存在しないブランド";
        String categoryName = "存在しないカテゴリー";
        String storeName = "存在しない店舗名";
        BigDecimal priceLower = new BigDecimal("9999");
        BigDecimal priceUpper = new BigDecimal("10000");
        String conditionName = "存在しない状態";
        String sortOrder = "価格の低い順";

        List<Product> actualProducts = productMapper.findByCriteria(keyword, brandName, categoryName, storeName, priceLower, priceUpper, conditionName, sortOrder);
        assertThat(actualProducts).isEmpty();
    }

    @Test
    @DataSet({"datasets/01_brands.yml", "datasets/02_categories.yml", "datasets/03_conditions.yml", "datasets/04_stores.yml", "datasets/05_products.yml"})
    @Transactional
    void 要素から主キーを取得することができること() {
        String brandName = "ブランド1";
        Integer actualNumber = productMapper.getIdByName("brands", "brand_id", "brand_name", brandName);
        assertThat(actualNumber)
                .isEqualTo(1);
    }

    @Test
    @DataSet({"datasets/01_brands.yml", "datasets/02_categories.yml", "datasets/03_conditions.yml", "datasets/04_stores.yml", "datasets/05_products.yml"})
    @Transactional
    void 要素から主キーを取得できない場合データが空で返されること() {
        String brandName = "ブランド999";
        Integer actualId = productMapper.getIdByName("brands", "brand_id", "brand_name", brandName);
        assertThat(actualId).isNull();
    }

    @Test
    @DataSet({"datasets/05_products.yml"})
    @ExpectedDataSet(value = "datasets/06_after_create_products.yml", ignoreCols = {"product_id", "date_added", "last_updated"})
    @Transactional
    void 新しい商品情報が登録されること() {
        Product product = new Product(null, "商品4", "MD-1004", 2, 2, BigDecimal.valueOf(4000.00).setScale(2), 8, 1, "商品4の説明", "image4.jpg", 1, null, null, "ブランド4", "アコースティックギター", "新品", "店舗名1", null, null);
        productMapper.create(product);
    }

    @Test
    @DataSet({"datasets/05_products.yml"})
    @Transactional
    void 必須フィールドが欠けている商品情報を登録しようとした場合エラーを返す() {
        Product incompleteProduct = new Product(null, null, null, null, null, null, null, null, "不完全な商品", "image.jpg", null, null, null, null, null, null, null, null, null);
        assertThrows(DataIntegrityViolationException.class, () -> productMapper.create(incompleteProduct));
    }

    @Test
    @DataSet({"datasets/05_products.yml"})
    @ExpectedDataSet(value = "datasets/07_after_update_products.yml", ignoreCols = {"product_id", "date_added", "last_updated"})
    void 商品情報を更新できること() {
        Product product = new Product(2, "（更新済）商品2", "MD-1002", 2, 2, BigDecimal.valueOf(2000.00).setScale(2), 15, 2, "更新しました", "image2.jpg", 2, LocalDateTime.of(2023, 2, 1, 0, 0), LocalDateTime.of(2023, 2, 2, 0, 0), "ブランド2", "アコースティックギター", "中古良好", "店舗名2", "所在地2", "電話番号2");
        productMapper.updateProduct(product);
    }

    @Test
    @DataSet({"datasets/05_products.yml"})
    @Transactional
    void 存在しない商品を更新しようとした場合失敗すること() {
        Product nonExistentProduct = new Product(999, "存在しない商品", "MD-9999", 1, 1, BigDecimal.valueOf(1000.00), 20, 1, "説明", "image.jpg", 1, LocalDateTime.now(), LocalDateTime.now(), "ブランド1", "カテゴリー1", "新品", "店舗1", "所在地1", "電話番号1");
        int updatedRows = productMapper.updateProduct(nonExistentProduct);
        assertThat(updatedRows).isEqualTo(0);
    }

    @Test
    @DataSet({"datasets/05_products.yml"})
    @ExpectedDataSet(value = "datasets/08_after_delete_products.yml")
    void 商品情報を削除できること() {
        productMapper.deleteById(2);
    }

    @Test
    @DataSet({"datasets/05_products.yml"})
    @Transactional
    void 存在しない商品を削除しようとした場合失敗する() {
        int deletedRows = productMapper.deleteById(999);
        assertThat(deletedRows).isEqualTo(0);
    }
}