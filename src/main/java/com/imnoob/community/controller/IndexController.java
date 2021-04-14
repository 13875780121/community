package com.imnoob.community.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imnoob.community.dto.QuestionDTO;
import com.imnoob.community.mapper.NoticeMapper;
import com.imnoob.community.mapper.UserMapper;
import com.imnoob.community.model.User;
import com.imnoob.community.provider.AutoLoginProvider;
import com.imnoob.community.service.NoticeService;
import com.imnoob.community.service.QuestionService;
import com.imnoob.community.service.RedisService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    AutoLoginProvider autoLoginProvider;

    @Autowired
    RedisService redisService;

    @GetMapping("/")
    String index(HttpServletRequest request,
                 HttpServletResponse response,
                 Model model,
                 @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                 @RequestParam(value = "size",defaultValue = "8") Integer size){



        String token = autoLoginProvider.checkCookie(request);
        if (token != null){
            User user = userMapper.selectByToken(token);
            request.getSession().setAttribute("user",user);
        }

        PageInfo<QuestionDTO> pageInfo = questionService.findAllQuestions(pageNum, size);
        model.addAttribute("pageInfo", pageInfo);


        Set<String> board = redisService.getdayboard();
        ArrayList<QuestionDTO> list = new ArrayList<>();
        for (String val : board) {
            QuestionDTO item = new QuestionDTO();
            String[] split = val.split(":");
            item.setId(Long.valueOf(split[0]));
            item.setTitle(split[1]);
            list.add(item);
        }

        model.addAttribute("board", list);
        return "index";
    }

}
