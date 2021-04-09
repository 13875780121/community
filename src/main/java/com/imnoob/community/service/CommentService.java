package com.imnoob.community.service;

import com.imnoob.community.dto.CommentDTO;
import com.imnoob.community.enums.CommentTypeEnum;
import com.imnoob.community.exception.CustomizeException;
import com.imnoob.community.enums.ExceptionEnum;
import com.imnoob.community.mapper.CommentMapper;
import com.imnoob.community.mapper.QuestionMapper;
import com.imnoob.community.model.Comment;
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

    @Transactional
    public void insertComment(Comment comment) {


        if (comment.getParentId() == null || comment.getParentId() == 0) {

            throw new CustomizeException(ExceptionEnum.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExitType(comment.getType())) {
            throw new CustomizeException(ExceptionEnum.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT_TYPE.getType()) {
            // 回复评论
            Comment dbComment = commentMapper.selectById(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(ExceptionEnum.COMMENT_NOT_FOUND);
            }
            commentMapper.insertComment(comment);
            // 增加评论数
            commentMapper.incCommentCount(dbComment.getId());


        } else {
            // 回复问题
            Question question = questionMapper.findQuestionById(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(ExceptionEnum.QUESTION_NOT_FOUND);
            }
            questionMapper.incCommentCount(question.getId());

        }
        commentMapper.insertComment(comment);
    }

    public List<CommentDTO> findCommentByParId(Long id, Integer type) {
        List<CommentDTO> list = commentMapper.selectByParId(id, type);
        return list;
//
    }
}
