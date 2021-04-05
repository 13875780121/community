package com.imnoob.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imnoob.community.dto.QuestionDTO;
import com.imnoob.community.mapper.QuestionMapper;
import com.imnoob.community.mapper.UserMapper;
import com.imnoob.community.model.Question;
import com.imnoob.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
}
