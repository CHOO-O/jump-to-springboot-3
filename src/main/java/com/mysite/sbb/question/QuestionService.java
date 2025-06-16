package com.mysite.sbb.question;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
// 컨트롤러 -> 서비스 -> 리포지토리 순으로 접근하도록 함
public class QuestionService {
    private final QuestionRepository questionRepository;

//    public List<Question> getList(){
//        return this.questionRepository.findAll();
//    }

    public Page<Question> getList(int page){ // 페이징을 위해 수정
        List<Sort.Order> sorts = new ArrayList<>(); // 게시물 최신순(역순) 조회를 이해 Sort 객체 생성, 전달
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }

    public Question getQuestion(Integer id){
        Optional<Question> question = this.questionRepository.findById(id);
        if(question.isPresent()){
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content){
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q);
    }
}
