package com.imnoob.community;

import com.imnoob.community.mapper.QuestionMapper;
import com.imnoob.community.mapper.UserMapper;
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
    DataSource dataSource;

   @Test
   void test1() throws SQLException {
       Connection connection = dataSource.getConnection();
       System.out.println(connection);
       connection.close();
   }

   @Autowired
    UserMapper userMapper;

   @Autowired
    QuestionMapper questionMapper;
   @Test
    void UserCRUD(){
       List<Question> allQuestions = questionMapper.findAllQuestions();
       for (Question item : allQuestions) {
           System.out.println(item);
       }
   }

}
