package com.imnoob.community.service;

import com.imnoob.community.dto.QuestionDTO;
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

    public List<QuestionDTO> findAllQuestions(){
        List<Question> list = questionMapper.findAllQuestions();
        List<QuestionDTO> res = new ArrayList<>();
        for (Question question : list) {
            QuestionDTO tmp = new QuestionDTO();
            BeanUtils.copyProperties(question,tmp);
            Long id = question.getCreator();
            User user = userMapper.findById(id);
            tmp.setUser(user);
            res.add(tmp);

        }
        return res;
    }
}
