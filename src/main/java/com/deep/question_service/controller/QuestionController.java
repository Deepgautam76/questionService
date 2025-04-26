package com.deep.question_service.controller;


import com.deep.question_service.model.Question;
import com.deep.question_service.model.QuestionWrapper;
import com.deep.question_service.model.Response;
import com.deep.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    Environment environment;
    @GetMapping("/questions")
    public ResponseEntity<List<Question>> allQuestion(){
        return questionService.getAllQuestion();
    }
    @PostMapping("/add")
    public ResponseEntity<List<Question>> addQuestion(@RequestBody List<Question> question){
        return questionService.addQuestion(question);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Question> updateQuestionById(@PathVariable Long id,
                                                       @RequestBody Question question){
        return questionService.updateQuestionById(id,question);
    }
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable Long id) {
        return questionService.deleteQuestionById(id);
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionByCategory(category);
    }


    /**
     * These all methods used by the quiz service
     */

    //Here is the generating question by giving noOfQuestion and category
    @GetMapping("/generate")
    public ResponseEntity<List<Long>> getQuestionForQuiz(
            @RequestParam String categoryName,
            @RequestParam Long numQuestions
    ){
        return questionService.getQuestionForQuiz(categoryName,numQuestions);
    }
    //Here is get the question by List of questionIds
    @PostMapping("/getQuestions")
    public  ResponseEntity<List<QuestionWrapper>>  getQuestionFromId(@RequestBody List<Long> questionIds){
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionFromId(questionIds);
    }
    //Here is getting the score by submitting the response
    @PostMapping("/getScore")
    public ResponseEntity<Long> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }


}
