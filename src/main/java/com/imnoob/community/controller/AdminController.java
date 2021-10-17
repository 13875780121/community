package com.imnoob.community.controller;

import com.imnoob.community.dto.AjaxResult;
import com.imnoob.community.dto.CommentDTO;
import com.imnoob.community.enums.CommentTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    //TODO 个人主页
    //TODO 关注功能

    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public String comments(@PathVariable(name = "id") Long id) {
        //发布的文章

        //关注的人

        //粉丝

        //是否关注

        return "admin";
    }
}
