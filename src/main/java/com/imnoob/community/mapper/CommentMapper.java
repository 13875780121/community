package com.imnoob.community.mapper;

import com.imnoob.community.dto.CommentDTO;
import com.imnoob.community.model.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {

    public int insertComment(Comment comment);

    public Comment selectById(@Param("id") Long id);

    int incCommentCount(@Param("id") Long id);

    List<CommentDTO> selectByParId(@Param(value = "id") Long id, @Param(value = "type") Integer type);


}
