package com.imnoob.community.mapper;

import com.imnoob.community.model.Advertise;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertiseMapper {
    @Select("select * from t_advertise where id = #{id]")
    Advertise selectById(@Param("id") Long id);

    @Select("select * from t_advertise where status = 1")
    List<Advertise> selectAll();
}
