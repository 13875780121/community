package com.imnoob.community.mapper;

import com.imnoob.community.model.Medie;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedieMapper {

    @Select("select * from t_medie where id = #{id}")
    Medie selectById(@Param("id") Long id);

    @Select("select * from t_medie ")
    List<Medie> selectAll();
}
