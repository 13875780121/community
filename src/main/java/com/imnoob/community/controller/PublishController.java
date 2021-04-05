package com.imnoob.community.controller;

import com.imnoob.community.mapper.QuestionMapper;
import com.imnoob.community.mapper.UserMapper;
import com.imnoob.community.model.Question;
import com.imnoob.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
            //
        if (question.getTag() == null || question.getTag().equals("")){
            model.addAttribute("error", "tag不为空");
            return "/publish";
        }
        if (question.getTitle() == null || question.getTitle().equals("")){
            model.addAttribute("error", "title不为空");
            return "/publish";
        }
        if (question.getDescription() == null || question.getDescription().equals("")){
            model.addAttribute("error", "内容不为空");
            return "/publish";
        }
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if (user != null && user instanceof User){
            User admin = (User) user;
            question.setCreator(admin.getId());
            question.setGmtCreate(System.currentTimeMillis());
            questionMapper.createQuestion(question);
        }
        return "index";

    }

}
