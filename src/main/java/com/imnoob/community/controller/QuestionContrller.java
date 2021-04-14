package com.imnoob.community.controller;

import com.imnoob.community.dto.CommentDTO;
import com.imnoob.community.dto.QuestionDTO;
import com.imnoob.community.enums.CommentTypeEnum;
import com.imnoob.community.exception.CustomizeException;
import com.imnoob.community.enums.ExceptionEnum;

import com.imnoob.community.model.Question;
import com.imnoob.community.model.User;
import com.imnoob.community.service.CommentService;
import com.imnoob.community.service.QuestionService;
import com.imnoob.community.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Controller
public class QuestionContrller {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    RedisService redisService;

    @GetMapping("/question/{id}")
    public String goQuestionPage(@PathVariable(value = "id")Long id,
                                 Model model, HttpServletRequest request){

        QuestionDTO quesDto = questionService.findQuestionById(id);
        //
        redisService.adddayboard(quesDto.getId(),quesDto.getTitle());
        redisService.addscrollboard(quesDto.getId(),quesDto.getTitle());

        if (quesDto.getId() == null){
            throw new CustomizeException(ExceptionEnum.QUESTION_NOT_FOUND);
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getId() != quesDto.getUser().getId()){

            //防止重复刷新导致浏览量增加 ，恶意刷浏览量
            boolean isreScan = redisService.isReScan(id, user.getId());
            if (!isreScan) questionService.incView(quesDto.getId());
        }



        List<CommentDTO> list = commentService.findCommentByParId(id, CommentTypeEnum.QUESTION_TYPE.getType());
        List<Question> questions = questionService.selectByTag(quesDto.getTag());

        model.addAttribute("comments", list);
        model.addAttribute("question", quesDto);
        model.addAttribute("relatedQuestions",questions);

        return "question";
    }
}
