package jp.cp.plana.sample.motocatalog.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import jp.cp.plana.sample.motocatalog.beans.Motorcycle;
import jp.cp.plana.sample.motocatalog.beans.SearchCondition;

@SpringBootTest
public class MotoServiceTest {

    DateTimeFormatter dtFormater = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    
    @Autowired
    MotosService service;

    // @Test
    // void バイク情報を全件検索できる(){
    //     SearchCondition condition = new SearchCondition();
    //     List<Motorcycle> motos = service.getMotos(condition);
    //     //  検索結果の件数確認
    //     assertThat(motos.size()).isEqualTo(2);
    //     // 検索結果の各項目の確認
    //     Motorcycle moto = motos.get(0); // 1件目
    //     assertThat(moto).isNotNull();
    //     assertThat(moto.getMotoNo()).isEqualTo(1);
    //     assertThat(moto.getMotoName()).isEqualTo("GB850");
    //     assertThat(moto.getBrand().getBrandName()).isEqualTo("Honda");
    // }

    @DisplayName("バイク一覧取得　条件：　ブランドID")
    @ParameterizedTest
    @CsvSource({"01, Honda", "02, Kawasaki", "03, Yamaha"})
    void test001(String brandId, String brandName){
        SearchCondition condition = new SearchCondition();
        condition.setBrandId(brandId); //Honda

        List<Motorcycle> motos = service.getMotos(condition); //テスト対策
        assertThat(motos.size()).isGreaterThanOrEqualTo(1);
        for (Motorcycle moto : motos) {
            assertThat(moto.getBrand().getBrandName()).isEqualTo(brandName);
        }
    }

    @DisplayName("バイク一覧取得　条件：　ブランドID 該当なし")
    @Test
    void test002(){
        SearchCondition condition = new SearchCondition();
        condition.setBrandId("99"); //Honda

        List<Motorcycle> motos = service.getMotos(condition); //テスト対策
        assertThat(motos.size()).isEqualTo(0);
    }

    @DisplayName("バイク一覧取得　条件：　バイク名・完全一致")
    @ParameterizedTest
    @CsvSource({"Z900RS CAFE", "YZF-R1", "Rebe1250"})
    void test003(String motoName){
        SearchCondition condition = new SearchCondition();
        condition.setKeyword(motoName);

        List<Motorcycle> motos = service.getMotos(condition); //テスト対策
        assertThat(motos.size()).isGreaterThanOrEqualTo(1);
        for (Motorcycle moto : motos) {
            assertThat(moto.getMotoName()).isEqualTo(motoName);
        }
    
    }

    
    @DisplayName("バイク一覧取得　条件：　バイク名・部分一致")
    @ParameterizedTest
    @CsvSource({"Z900RS CA, Z900RS CAFE", "F-R1, YZF-R1", "be125, Rebe1250"})
    void test004(String keyword, String motoName){
        SearchCondition condition = new SearchCondition();
        condition.setKeyword(keyword);

        List<Motorcycle> motos = service.getMotos(condition); //テスト対策
        assertThat(motos.size()).isGreaterThanOrEqualTo(1);
        for (Motorcycle moto : motos) {
            assertThat(moto.getMotoName()).isEqualTo(motoName);
        }
    
    }
    

    @DisplayName("バイク一覧取得　条件：　バイク名・該当なし")
    @Test
    void test005(){
        SearchCondition condition = new SearchCondition();
        condition.setKeyword("存在しないバイク名");

        List<Motorcycle> motos = service.getMotos(condition); //テスト対策
        assertThat(motos.size()).isEqualTo(0);
    }

    @DisplayName("バイク一覧取得　条件：　ブランドID, バイク名")
    @ParameterizedTest
    @CsvSource({"02, Z900, Z900RS", "03, R1, YZF-R1", "01, be1, Rebe1"})
    void test006(String brandId, String keyword, String motoName){
        SearchCondition condition = new SearchCondition();
        condition.setBrandId(brandId);
        condition.setKeyword(keyword);

        List<Motorcycle> motos = service.getMotos(condition); //テスト対策
        assertThat(motos.size()).isGreaterThanOrEqualTo(1);
        for (Motorcycle moto : motos) {
            assertThat(moto.getMotoName()).startsWith(motoName);
        }
    
    }
    // バイク一覧取得　条件：　ブランドID, バイク名 該当なし
    @DisplayName("バイク一覧取得　条件：　ブランドID, バイク名 該当なし")
    @ParameterizedTest
    @CsvSource({"01, Z900RS", "03, R99"})
    void test007(String brandId, String keyword){
        SearchCondition condition = new SearchCondition();
        condition.setBrandId(brandId);
        condition.setKeyword(keyword);

        List<Motorcycle> motos = service.getMotos(condition); //テスト対策
        assertThat(motos.size()).isEqualTo(0);
    }
    
    @DisplayName("バイク一覧取得　条件：　なし　全件該当")
    @Test
    void test008(){
        SearchCondition condition = new SearchCondition();
        

        List<Motorcycle> motos = service.getMotos(condition); //テスト対策
        assertThat(motos.size()).isEqualTo(9);
    }

    
    @DisplayName("バイク一覧取得　条件：　バイク番号")
    @ParameterizedTest
    @CsvSource({"1, GB850", "3, W800 CAFE"})
    void test009(int motoNo, String motoName){
        

        Motorcycle moto = service.getMotos(motoNo); //テスト対策
        assertThat(moto.getMotoName()).isEqualTo(motoName);
    }

    @DisplayName("バイク一覧取得　条件：　バイク番号　全項目番号")
    @Test
    void test010(){
        Motorcycle moto = service.getMotos(1); //テスト対策
        assertThat(moto.getMotoNo()).isEqualTo(1);
        assertThat(moto.getMotoName()).isEqualTo("GB850");
        assertThat(moto.getSeatHeight()).isEqualTo(800);
        assertThat(moto.getCylinder()).isEqualTo(1);
        assertThat(moto.getCooling()).isEqualTo("空冷");
        assertThat(moto.getPrice()).isEqualTo(550000);
        assertThat(moto.getComment()).isEqualTo("エンジンのトコトコ音がいい。");
        assertThat(moto.brand.getBrandId()).isEqualTo("01");
        assertThat(moto.getVersion()).isEqualTo(1);
        assertThat(moto.getInsDt().format(dtFormater)).isEqualTo(LocalDateTime.now().format(dtFormater));
        assertThat(moto.getUpdDt()).isNull();
    }

    @DisplayName("バイク情報更新")
    @Test
    @Transactional
    @Rollback
    void test011(){
        Motorcycle before = service.getMotos(1);
        before.setMotoName("motomoto");

        service.save(before); // 更新 (保存)

        Motorcycle after = service.getMotos(1); // 変更後のバイク情報取得
        assertThat(after.getMotoName()).isEqualTo("motomoto");
        assertThat(after.getVersion()).isEqualTo(before.getVersion() + 1);

    }

}


