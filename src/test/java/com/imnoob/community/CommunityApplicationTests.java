package com.imnoob.community;

import com.imnoob.community.dto.CommentDTO;
import com.imnoob.community.mapper.CommentMapper;
import com.imnoob.community.mapper.QuestionMapper;
import com.imnoob.community.mapper.UserMapper;
import com.imnoob.community.model.Comment;
import com.imnoob.community.model.Question;
import com.imnoob.community.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class CommunityApplicationTests {


   @Autowired
    CommentMapper commentMapper;

   @Autowired
   QuestionMapper questionMapper;
   @Test
    void UserCRUD(){
       List<Question> questions = questionMapper.selectByTag("Spring|SpringBott|Java");
       for (Question question : questions) {
           System.out.println(question);
       }


   }

}
