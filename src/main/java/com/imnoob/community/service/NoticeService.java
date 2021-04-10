package com.imnoob.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imnoob.community.dto.NoticeDTO;
import com.imnoob.community.dto.QuestionDTO;
import com.imnoob.community.enums.ExceptionEnum;
import com.imnoob.community.enums.NoticeActionEnum;
import com.imnoob.community.exception.CustomizeException;
import com.imnoob.community.mapper.NoticeMapper;
import com.imnoob.community.model.Notice;
import com.imnoob.community.model.Question;
import com.imnoob.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeService {
    @Autowired
    NoticeMapper noticeMapper;

    public void createNotice(Notice notice){
        noticeMapper.createNotice(notice);
    }

    public int unreadCount(Long id){
        return  noticeMapper.unreadCount(id);
    }

    public PageInfo<NoticeDTO> findAllQuestions(Long id,Integer pageNum, Integer size){
        //实现分页

        PageHelper.startPage(pageNum, size);
        List<NoticeDTO> list = noticeMapper.findAll(id);
        for (NoticeDTO noticeDTO : list) {
            noticeDTO.setTypeName(NoticeActionEnum.getNameByType(noticeDTO.getType()));
        }
        PageInfo<NoticeDTO> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public Notice read(Long id, User user) {
        Notice notice = noticeMapper.selectById(id);
        if (notice == null){
            throw new CustomizeException(ExceptionEnum.NOTIFICATION_NOT_FOUND);
        }
        if (!notice.getReceiver().equals(user.getId())){
            throw new CustomizeException(ExceptionEnum.READ_NOTIFICATION_FAIL);
        }
        noticeMapper.readMsg(id);
        return notice;

    }
}
