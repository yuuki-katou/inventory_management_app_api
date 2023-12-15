package com.yuuki.katou.inventory_management_app01.mapper;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.yuuki.katou.inventory_management_app01.entity.Brand;
import com.yuuki.katou.inventory_management_app01.entity.Category;
import com.yuuki.katou.inventory_management_app01.entity.Condition;
import com.yuuki.katou.inventory_management_app01.entity.Store;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReferenceDataMapperTest {

    @Autowired
    ReferenceDataMapper referenceDataMapper;

    @Test
    @DataSet({"datasets/01_brands.yml", "datasets/05_products.yml"})
    @Transactional
    void すべてのブランド情報を取得できること() {
        List<Brand> expectedBrands = Arrays.asList(
                new Brand("ブランド1", "住所1", "電話番号1"),
                new Brand("ブランド2", "住所2", "電話番号2"),
                new Brand("ブランド3", "住所3", "電話番号3")
        );
        List<Brand> actualBrands = referenceDataMapper.brandFindAll();
        assertThat(actualBrands)
                .hasSize(3)
                .usingRecursiveFieldByFieldElementComparator().usingRecursiveComparison().isEqualTo(expectedBrands);
    }


    @Test
    @DataSet({"datasets/02_categories.yml", "datasets/05_products.yml"})
    @Transactional
    void すべてのカテゴリ情報が取得できること() {

        List<Category> expectedCategories = Arrays.asList(
                new Category(1, "エレキギター"),
                new Category(2, "アコースティックギター"),
                new Category(3, "ベースギター")
        );
        List<Category> actualBrands = referenceDataMapper.categoryFindAll();
        assertThat(actualBrands)
                .hasSize(3)
                .usingRecursiveFieldByFieldElementComparator().usingRecursiveComparison().isEqualTo(expectedCategories);
    }

    @Test
    @DataSet({"datasets/03_conditions.yml", "datasets/05_products.yml"})
    @Transactional
    void すべての商品の状態が取得できること() {
        List<Condition> expectedConditions = Arrays.asList(
                new Condition(1, "新品"),
                new Condition(2, "中古良好"),
                new Condition(3, "中古可")
        );
        List<Condition> actualConditions = referenceDataMapper.conditionFindAll();
        assertThat(actualConditions)
                .hasSize(3)
                .usingRecursiveFieldByFieldElementComparator().usingRecursiveComparison().isEqualTo(expectedConditions);
    }

    @Test
    @DataSet({"datasets/04_stores.yml", "datasets/05_products.yml"})
    @Transactional
    void すべての取扱店舗情報が取得できること() {
        List<Store> expectedStores = Arrays.asList(
                new Store(1, "店舗名1", "所在地1", "電話番号1"),
                new Store(2, "店舗名2", "所在地2", "電話番号2"),
                new Store(3, "店舗名3", "所在地3", "電話番号3")
        );
        List<Store> actualStores = referenceDataMapper.storeFindAll();
        assertThat(actualStores)
                .hasSize(3)
                .usingRecursiveFieldByFieldElementComparator().usingRecursiveComparison().isEqualTo(expectedStores);
    }
}