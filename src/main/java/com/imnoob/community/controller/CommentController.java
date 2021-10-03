package com.imnoob.community.controller;

import com.imnoob.community.dto.AjaxResult;
import com.imnoob.community.dto.CommentDTO;
import com.imnoob.community.enums.CommentTypeEnum;
import com.imnoob.community.exception.CustomizeException;
import com.imnoob.community.enums.ExceptionEnum;
import com.imnoob.community.model.Comment;
import com.imnoob.community.model.User;
import com.imnoob.community.service.CommentService;
import com.imnoob.community.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    RedisService redisService;

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    AjaxResult commitComment(@RequestBody CommentDTO commentDTO, HttpServletRequest request){

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null){
            throw new CustomizeException(ExceptionEnum.NO_LOGIN);
        }

        if (commentDTO.getType() == null) throw new CustomizeException(ExceptionEnum.TYPE_PARAM_WRONG);
        //TODO 用户禁言校验
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setUserId(user.getId());
        comment.setLikeCount(0L);
        commentService.insertComment(comment);

        return AjaxResult.okOf(200, "请求成功");
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public AjaxResult comments(@PathVariable(name = "id") Long id) {
        List<CommentDTO> list = commentService.findCommentByParId(id, CommentTypeEnum.COMMENT_TYPE.getType());
        return AjaxResult.okOf(200, "请求成功", list);
    }


    @ResponseBody
    @RequestMapping(value = "/comment/like/{id}", method = RequestMethod.GET)
    public AjaxResult likeComment(@PathVariable(name = "id") Long id,HttpServletRequest request) {
        User u = (User) request.getSession().getAttribute("user");
        if (u == null) throw new CustomizeException(ExceptionEnum.NO_LOGIN);
        if (!redisService.isthumbUp(id,u.getId())){
            Integer num = commentService.incLikeCount(id);
            return AjaxResult.okOf(200, "请求成功",num);
        }else{
            return AjaxResult.okOf(201,"请求成功");
        }


    }
}
