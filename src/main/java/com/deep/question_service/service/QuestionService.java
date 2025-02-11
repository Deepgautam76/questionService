package com.deep.question_service.service;


import com.deep.question_service.model.Question;
import com.deep.question_service.model.QuestionWrapper;
import com.deep.question_service.model.Response;
import com.deep.question_service.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    public ResponseEntity<List<Question>> getAllQuestion() {
        List<Question> question=questionRepository.findAll();
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        try{
            List<Question> questions=questionRepository.findByCategory(category);
            return new ResponseEntity<>(questions,HttpStatus.OK);

        }catch (Exception ignored){

        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Question> addQuestion(Question question) {
        Question question1=questionRepository.save(question);
        return new ResponseEntity<>(question1,HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteQuestionById(Long id) {
        Optional<Question> question=questionRepository.findById(id);
        try {
            if(question.isPresent()){
                questionRepository.deleteById(id);
                return new ResponseEntity<>("Deleted success",HttpStatus.OK);
            }
        }catch (Exception ignored){
            System.out.println("Such User not exist with id:"+id);
        }
        return new ResponseEntity<>("Such User not exist with id:"+id,HttpStatus.BAD_REQUEST);
    }

    /**
     * Update the question by id and its updated value
     * */
    public ResponseEntity<Question> updateQuestionById(Long id, Question question) {
        Optional<Question> question1=questionRepository.findById(id);
        Question updateResponse=null;
       try{
           if(question1.isPresent()){
               Question question2=question1.get();
               if(question.getQuestionTitle()!=null){
                   question2.setQuestionTitle(question.getQuestionTitle());
               }
               if(question.getOption1()!=null){
                   question2.setOption1(question.getOption1());
               }
               if(question.getOption2()!=null){
                   question2.setOption2(question.getOption2());
               }
               if(question.getOption3()!=null){
                   question2.setOption3(question.getOption3());
               }
               if(question.getOption4()!=null){
                   question2.setOption4(question.getOption4());
               }
               if(question.getDifficultyLevel()!=null){
                   question2.setDifficultyLevel(question.getDifficultyLevel());
               }
               if (question.getRightAnswer()!=null) {
                   question2.setRightAnswer(question.getRightAnswer());
               }
               questionRepository.save(question2);
               updateResponse=question2;
               return new ResponseEntity<>(updateResponse,HttpStatus.ACCEPTED);
           }
       }catch (Exception ignored){
           System.out.println("Such user not found with id:"+id);
       }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    //Generate the question by category and numOfQuestion
    public ResponseEntity<List<Long>> getQuestionForQuiz(String categoryName, Long numQuestions) {
        List<Long> questionIds=questionRepository.findRandomQuestionsByCategory(categoryName,numQuestions);

        return new ResponseEntity<>(questionIds,HttpStatus.OK);
    }

    //Here is the get question by Ids
    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(List<Long> questionIds) {
        List<Question> questions=questionRepository.findAllById(questionIds);
        List<QuestionWrapper> questionWrapper=new ArrayList<>();
        for(Question question:questions){
            QuestionWrapper questionWrapper1=new QuestionWrapper();
            questionWrapper1.setQuestionTitle(question.getQuestionTitle());
            questionWrapper1.setId(question.getId());
            questionWrapper1.setOption1(question.getOption1());
            questionWrapper1.setOption2(question.getOption2());
            questionWrapper1.setOption3(question.getOption3());
            questionWrapper1.setOption4(question.getOption4());
            questionWrapper.add(questionWrapper1);
        }

        return new ResponseEntity<>(questionWrapper,HttpStatus.OK);
    }

    public ResponseEntity<Long> getScore(List<Response> responses) {
        Long count= 0L;
        for(Response res:responses) {
            Question question = questionRepository.findById(res.getId()).get();
            if (question.getRightAnswer().equals(res.getResponse())) {
                count++;
            }
        }
        return new ResponseEntity<>(count,HttpStatus.OK);
    }
}
