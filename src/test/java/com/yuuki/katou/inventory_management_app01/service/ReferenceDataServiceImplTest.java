package com.yuuki.katou.inventory_management_app01.service;

import com.yuuki.katou.inventory_management_app01.dto.BrandsDto;
import com.yuuki.katou.inventory_management_app01.dto.CategoriesDto;
import com.yuuki.katou.inventory_management_app01.dto.ConditionsDto;
import com.yuuki.katou.inventory_management_app01.dto.StoresDto;
import com.yuuki.katou.inventory_management_app01.entity.Brand;
import com.yuuki.katou.inventory_management_app01.entity.Category;
import com.yuuki.katou.inventory_management_app01.entity.Condition;
import com.yuuki.katou.inventory_management_app01.entity.Store;
import com.yuuki.katou.inventory_management_app01.exception.ResourceNotFoundException;
import com.yuuki.katou.inventory_management_app01.mapper.ReferenceDataMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReferenceDataServiceImplTest {
    @Mock
    private ReferenceDataMapper referenceMapper;

    @InjectMocks
    private ReferenceDataServiceImpl referenceDataService;


    @Test
    void 全てのカテゴリ情報を取得すること() {
        // モックデータの準備
        List<Category> mockCategories = Arrays.asList(
                new Category(1, "カテゴリ1"),
                new Category(2, "カテゴリ2")
        );
        when(referenceMapper.categoryFindAll()).thenReturn(mockCategories);

        // 実行と検証
        List<CategoriesDto> result = referenceDataService.categoryFindAll();
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(mockCategories.size());
    }

    @Test
    void カテゴリ情報が存在しない場合に例外を返すこと() {
        // モックの動作を設定（空のリストを返す）
        when(referenceMapper.categoryFindAll()).thenReturn(Collections.emptyList());

        // 実行と例外の検証
        assertThatThrownBy(() -> referenceDataService.categoryFindAll())
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("カテゴリ情報がみつかりませんでした。");
    }


    @Test
    void 全てのブランド情報を取得すること() {
        // モックデータの準備
        List<Brand> mockBrands = Arrays.asList(
                new Brand("ブランド1", "住所1", "電話番号1"),
                new Brand("ブランド2", "住所2", "電話番号2")
        );
        when(referenceMapper.brandFindAll()).thenReturn(mockBrands);

        // 実行と検証
        List<BrandsDto> result = referenceDataService.brandFindAll();
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(mockBrands.size());
    }


    @Test
    void ブランド情報が存在しない場合に例外を返すこと() {
        // モックの動作を設定（空のリストを返す）
        when(referenceMapper.brandFindAll()).thenReturn(Collections.emptyList());

        // 実行と例外の検証
        assertThatThrownBy(() -> referenceDataService.brandFindAll())
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("ブランド情報が見つかりませんでした。");
    }

    @Test
    void 全ての状態情報を取得すること() {
        // モックデータの準備
        List<Condition> mockConditions = Arrays.asList(
                new Condition(1, "状態1"),
                new Condition(2, "状態2")
        );
        when(referenceMapper.conditionFindAll()).thenReturn(mockConditions);

        // 実行と検証
        List<ConditionsDto> result = referenceDataService.conditionFindAll();
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(mockConditions.size());
    }


    @Test
    void 状態情報が存在しない場合に例外を返すこと() {
        // モックの動作を設定（空のリストを返す）
        when(referenceMapper.conditionFindAll()).thenReturn(Collections.emptyList());

        // 実行と例外の検証
        assertThatThrownBy(() -> referenceDataService.conditionFindAll())
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("商品の状態情報が見つかりませんでした。");
    }

    @Test
    void 全ての店舗情報を取得すること() {
        // モックデータの準備
        List<Store> mockStores = Arrays.asList(
                new Store(1, "店舗名1", "所在地1", "電話番号1"),
                new Store(2, "店舗名2", "所在地2", "電話番号2")
        );
        when(referenceMapper.storeFindAll()).thenReturn(mockStores);

        // 実行と検証
        List<StoresDto> result = referenceDataService.storeFindAll();
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(mockStores.size());
    }

    @Test
    void 店舗情報が存在しない場合に例外を返すこと() {
        // モックの動作を設定（空のリストを返す）
        when(referenceMapper.storeFindAll()).thenReturn(Collections.emptyList());

        // 実行と例外の検証
        assertThatThrownBy(() -> referenceDataService.storeFindAll())
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("店舗情報が見つかりませんでした。");
    }
}

