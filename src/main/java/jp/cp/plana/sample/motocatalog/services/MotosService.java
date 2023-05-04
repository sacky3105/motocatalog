package jp.cp.plana.sample.motocatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.cp.plana.sample.motocatalog.beans.Brand;
import jp.cp.plana.sample.motocatalog.beans.Motorcycle;
import jp.cp.plana.sample.motocatalog.mappers.BrandMapper;
import jp.cp.plana.sample.motocatalog.mappers.MotorcycleMapper;

@Service
public class MotosService {

    @Autowired
    MotorcycleMapper motorcycleMapper;

    @Autowired
    BrandMapper brandMapper;

    public List<Motorcycle> getMotos() {
        return motorcycleMapper.selectAll();
    }

    public List<Brand> getBrands(){
        return brandMapper.selectAll();
    }
    
}
