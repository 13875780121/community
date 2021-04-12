package com.imnoob.community.service;

import com.imnoob.community.dto.CommentDTO;
import com.imnoob.community.enums.CommentTypeEnum;
import com.imnoob.community.enums.NoticeActionEnum;
import com.imnoob.community.exception.CustomizeException;
import com.imnoob.community.enums.ExceptionEnum;
import com.imnoob.community.mapper.CommentMapper;
import com.imnoob.community.mapper.NoticeMapper;
import com.imnoob.community.mapper.QuestionMapper;
import com.imnoob.community.model.Comment;
import com.imnoob.community.model.Notice;
import com.imnoob.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Transactional
    public void insertComment(Comment comment) {


        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(ExceptionEnum.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExitType(comment.getType())) {
            throw new CustomizeException(ExceptionEnum.TYPE_PARAM_WRONG);
        }

        Notice notice = new Notice();
        notice.setNoticer(comment.getUserId());

        notice.setGmtCreate(System.currentTimeMillis());




        if (comment.getType() == CommentTypeEnum.COMMENT_TYPE.getType()) {
            // 回复评论
            Comment dbComment = commentMapper.selectById(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(ExceptionEnum.COMMENT_NOT_FOUND);
            }
            // 回复问题
            Question question = questionMapper.findQuestionById(dbComment.getParentId());
            commentMapper.insertComment(comment);

            // 增加评论数
            commentMapper.incCommentCount(dbComment.getId());
            notice.setOuterName(question.getTitle());
            notice.setReceiver(dbComment.getUserId());
            notice.setOuterName(question.getTitle()+"的评论");
            notice.setType(NoticeActionEnum.NOTICE_REPLY.getType());
            notice.setOuterId(dbComment.getParentId());
        } else {

            // 回复问题
            Question question = questionMapper.findQuestionById(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(ExceptionEnum.QUESTION_NOT_FOUND);
            }
            questionMapper.incCommentCount(question.getId());
            notice.setType(NoticeActionEnum.NOTICE_COLLECT.getType());
            notice.setOuterName(question.getTitle());
            notice.setReceiver(question.getCreator());
            notice.setOuterId(question.getId());
            commentMapper.insertComment(comment);
        }

        noticeMapper.createNotice(notice);
    }

    public List<CommentDTO> findCommentByParId(Long id, Integer type) {
        List<CommentDTO> list = commentMapper.selectByParId(id, type);
        return list;
//
    }

    public Integer incLikeCount(Long id) {
        System.out.println("点赞加一");
        commentMapper.incLikeCount(id);
        return commentMapper.queryLikeCount(id);
    }
}
