package com.imnoob.community.controller;

import com.github.pagehelper.PageInfo;
import com.imnoob.community.dto.NoticeDTO;
import com.imnoob.community.model.Question;
import com.imnoob.community.model.User;
import com.imnoob.community.service.NoticeService;
import com.imnoob.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @Autowired
    QuestionService questionService;

    @Autowired
    NoticeService noticeService;

    @GetMapping("/profile/{action}")
    public String goProfile(@PathVariable(name = "action") String action,
                            HttpServletRequest request,
                            Model model,
                            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "size",defaultValue = "8") Integer size){

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");

//            PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);

            PageInfo<Question> pageInfo = questionService.findQuestionsById(pageNum, size, user.getId());
            model.addAttribute("pageInfo", pageInfo);
        } else if ("replies".equals(action)) {
            PageInfo<NoticeDTO> pageInfo = noticeService.findAllQuestions(user.getId(), pageNum, size);
            model.addAttribute("section", "replies");
            model.addAttribute("pagination", pageInfo);
            model.addAttribute("sectionName", "最新回复");
        }
        return "profile";

    }
}
