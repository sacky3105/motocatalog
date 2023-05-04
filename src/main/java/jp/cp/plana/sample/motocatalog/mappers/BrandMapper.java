package jp.cp.plana.sample.motocatalog.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.cp.plana.sample.motocatalog.beans.Brand;

@Mapper
public interface BrandMapper {
    public List<Brand> selectAll();
}
