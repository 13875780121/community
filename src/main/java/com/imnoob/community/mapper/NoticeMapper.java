package com.imnoob.community.mapper;

import com.imnoob.community.dto.NoticeDTO;
import com.imnoob.community.model.Notice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeMapper {

    public void createNotice(Notice notice);

    public Integer unreadCount(@Param("id") Long id);

    List<NoticeDTO> findAll(@Param("id") Long id);

    Notice selectById(@Param("id") Long id);

    int readMsg(@Param("id")Long id);
}
