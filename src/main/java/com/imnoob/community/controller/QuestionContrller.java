package com.imnoob.community.controller;

import com.imnoob.community.dto.QuestionDTO;
import com.imnoob.community.exception.CustomizeException;
import com.imnoob.community.exception.ExceptionEnum;
import com.imnoob.community.model.User;
import com.imnoob.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionContrller {

    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public String goQuestionPage(@PathVariable(value = "id")Integer id,
                                 Model model, HttpServletRequest request){

        QuestionDTO quesDto = questionService.findQuestionById(id);
        if (quesDto.getId() == null){
            throw new CustomizeException(ExceptionEnum.QUESTION_NOT_FOUND);
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getId() != quesDto.getUser().getId()){
            questionService.incView(quesDto.getId());
        }

        model.addAttribute("question", quesDto);
        return "question";
    }
}
