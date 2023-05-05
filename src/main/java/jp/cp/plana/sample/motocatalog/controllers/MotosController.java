package jp.cp.plana.sample.motocatalog.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.cp.plana.sample.motocatalog.beans.Brand;
import jp.cp.plana.sample.motocatalog.beans.Motorcycle;
import jp.cp.plana.sample.motocatalog.beans.SearchCondition;
import jp.cp.plana.sample.motocatalog.services.MotosService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j //ログ部品を使えるようになった。
public class MotosController {

    @Autowired
    MotosService service;

    
    @RequestMapping("/hello")
    public String hello(@RequestParam String name, Model model){
        model.addAttribute("name",name);
        return "test";
    }

    @GetMapping("/motos")
    public String motos(Model model){
        // ブランド
        List<Brand> brands = new ArrayList<Brand>();
        brands = service.getBrands();

        // バイク
        List<Motorcycle> motos = new ArrayList<>();
        SearchCondition condition = new SearchCondition();
        motos = service.getMotos(condition);
        

        model.addAttribute("brands",brands);
        model.addAttribute("motos",motos);

        log.debug("motos : {}", motos); // ログ出力


        return "moto_list";


    }
}
