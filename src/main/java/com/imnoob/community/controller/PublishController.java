package com.imnoob.community.controller;

import com.imnoob.community.exception.CustomizeException;
import com.imnoob.community.enums.ExceptionEnum;
import com.imnoob.community.mapper.QuestionMapper;
import com.imnoob.community.mapper.UserMapper;
import com.imnoob.community.model.Question;
import com.imnoob.community.model.User;
import com.imnoob.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class PublishController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionService questionService;

    @GetMapping("/publish/{id}")
    String edit(@PathVariable(name = "id")Long id,
                Model model){
        Question quedto = questionMapper.findQuestionById(id);
        model.addAttribute("title", quedto.getTitle());
        model.addAttribute("description", quedto.getDescription());
        model.addAttribute("tag", quedto.getTag());
        model.addAttribute("id", quedto.getId());
        model.addAttribute("tags", quedto.getTag());
        return "publish";

    }
    @GetMapping("/publish")
    String publish(HttpServletResponse response,
                   HttpServletRequest request,
                   Model model){
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if (user == null) model.addAttribute("error", "请登陆");
        return "publish";
    }

    @PostMapping("/publish")
    String doPublish(
            HttpServletResponse response,
            HttpServletRequest request,
            Question question,
            Model model){

        boolean hasError = false;
        if (question.getTag() == null || question.getTag().equals("")){
            model.addAttribute("error", "tag不为空");
            hasError = true;
        }
        if (question.getTitle() == null || question.getTitle().equals("")){
            model.addAttribute("error", "title不为空");
            hasError = true;
        }
        if (question.getDescription() == null || question.getDescription().equals("")){
            model.addAttribute("error", "内容不为空");
            hasError = true;
        }
        if (hasError){
            model.addAttribute("title", question.getTitle());
            model.addAttribute("description", question.getDescription());
            model.addAttribute("tag", question.getTag());

            return "/publish";
        }

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if (user == null){
            throw new CustomizeException(ExceptionEnum.NO_LOGIN);
        }


        if (question.getId() != null){
            //编辑的逻辑
            question.setCreator(user.getId());
            question.setGmtCreate(System.currentTimeMillis());
            questionService.modifiedQuestion(question);

        }else{
            //添加问题的逻辑
            question.setCreator(user.getId());
            question.setGmtModified(System.currentTimeMillis());
            questionService.createQuestion(question);

        }

        return "redirect:/";

    }

}
