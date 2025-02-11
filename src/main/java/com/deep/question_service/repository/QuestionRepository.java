package com.deep.question_service.repository;


import com.deep.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    List<Question> findByCategory(String category);

    @Query(value = "SELECT q.id FROM question q Where q.category=:category ORDER By RAND() LIMIT :numQ",nativeQuery = true)
    List<Long> findRandomQuestionsByCategory(String category, Long numQ);
}
