package com.study.cae.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.cae.entity.Type;
import com.study.cae.vo.QueryTypeCountVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TypeMapper extends BaseMapper<Type> {

    int countByLevel(QueryTypeCountVo type);
}
