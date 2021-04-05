package com.imnoob.community.controller;

import com.imnoob.community.dto.QuestionDTO;
import com.imnoob.community.mapper.UserMapper;
import com.imnoob.community.model.User;
import com.imnoob.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    String index(HttpServletRequest request, HttpServletResponse response, Model model){
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.selectByToken(token);
                if (user != null){
                    HttpSession session = request.getSession();
                    session.setAttribute("user",user);
                }
                break;
            }
        }

        List<QuestionDTO> list = questionService.findAllQuestions();
        model.addAttribute("questions", list);
        return "index";
    }

}
