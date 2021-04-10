package com.imnoob.community.controller;

import com.imnoob.community.dto.NoticeDTO;
import com.imnoob.community.model.Notice;
import com.imnoob.community.model.User;
import com.imnoob.community.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long id) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        Notice notificationDTO = noticeService.read(id, user);
        int num = (int) request.getSession().getAttribute("unreadCount");
        num--;
        if (num >0){
            request.getSession().setAttribute("unreadCount",num);
        }else{
            request.getSession().setAttribute("unreadCount",0);
        }
        return "redirect:/question/" + notificationDTO.getOuterId();

    }
}
