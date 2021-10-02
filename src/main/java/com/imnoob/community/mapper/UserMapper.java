package com.imnoob.community.mapper;

import com.imnoob.community.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    int insertUser(User user);

    User selectByToken(@Param("token") String token);

    User selectByAccountId(@Param("accountId") String id);

    int modifiedToken(@Param("token") String token);

    User findById(@Param("id") Long id);

    int incIntegral(@Param("id") Long id ,@Param("integral") Integer integral);
}
