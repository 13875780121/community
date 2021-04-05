package com.imnoob.community.mapper;

import com.imnoob.community.model.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionMapper {
    int createQuestion(Question question);
    List<Question> findAllQuestions();
}
