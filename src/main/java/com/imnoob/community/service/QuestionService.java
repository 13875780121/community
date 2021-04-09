package com.imnoob.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imnoob.community.dto.QuestionDTO;
import com.imnoob.community.exception.CustomizeException;
import com.imnoob.community.enums.ExceptionEnum;
import com.imnoob.community.mapper.QuestionMapper;
import com.imnoob.community.mapper.UserMapper;
import com.imnoob.community.model.Question;
import com.imnoob.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    public PageInfo<QuestionDTO> findAllQuestions(Integer pageNum, Integer size){
        //实现分页

        PageHelper.startPage(pageNum, size);
        List<Question> list = questionMapper.findAllQuestions();
        PageInfo<Question> pageInfo1 = new PageInfo<>(list);

        List<QuestionDTO> res = new ArrayList<>();
        for (Question question : list) {
            QuestionDTO tmp = new QuestionDTO();
            BeanUtils.copyProperties(question,tmp);
            Long id = question.getCreator();
            User user = userMapper.findById(id);
            tmp.setUser(user);
            res.add(tmp);
        }
        PageInfo<QuestionDTO> pageInfo = new PageInfo<>(res);
        BeanUtils.copyProperties(pageInfo1,pageInfo);
        pageInfo.setList(res);

        return pageInfo;
    }

    public PageInfo<Question> findQuestionsById(Integer pageNum,Integer size,Long id){
        PageHelper.startPage(pageNum, size);
        List<Question> list = questionMapper.findQuestionsById(id);
        PageInfo<Question> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    public QuestionDTO findQuestionById(Long id) {
        Question question = questionMapper.findQuestionById(id);

        if (question == null){
            throw new CustomizeException(ExceptionEnum.QUESTION_NOT_FOUND);
        }
        User user = userMapper.findById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createQuestion(Question question) {
        questionMapper.createQuestion(question);
    }

    public int modifiedQuestion(Question question) {
        return  questionMapper.modifiedQuestion(question);
    }

    public int incView(Long id) {
        return questionMapper.incView(id);
    }

    public List<Question> selectByTag(String tag) {
        if (tag == null || tag.equals("")) return new ArrayList<>();
        String replace = tag.replace(",", "|");
        List<Question> questions = questionMapper.selectByTag(replace);

        return questions;
    }
}
