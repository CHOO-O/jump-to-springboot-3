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
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.answer.Answer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
// 컨트롤러 -> 서비스 -> 리포지토리 순으로 접근하도록 함
public class QuestionService {
    private final QuestionRepository questionRepository;

    private Specification<Question> search(String kw){ // 검색어 kw 입력받아 쿼리의 join, where문을 Specification 객체로 생성하여 리턴
        return new Specification<>() { // JPA의 Specification 인터페이스 -> 쿼리를 보다 정교하게 작성할 수 있게 하는 JPA 도구
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb){ // q = 기준이 되는 Question 객체
                query.distinct(true);
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT); // Question - SiteUser 아우터 조인해 만든 SiteUser 객체 (공통속성 author) => 질문 작성자 검색
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT); // Question - Answer 아우터 조인 Answer 객체 (공통속성 answerList) => 답변 내용 검색
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT); // Answer - SiteUser 아우터 조인 SiteUser 객체 (공통속성 author) => 답변 작성자 검색
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // kw를 포함하는지 => cb.like, cn.or로 여러 조건 OR검색
                        cb.like(q.get("content"), "%" + kw + "%"),
                        cb.like(u1.get("username"), "%" + kw + "%"),
                        cb.like(a.get("content"), "%" + kw + "%"),
                        cb.like(u2.get("username"), "%" + kw + "%"));
            }
        };
    }

    public Page<Question> getList(int page, String kw){ // 페이징을 위해 수정
        List<Sort.Order> sorts = new ArrayList<>(); // 게시물 최신순(역순) 조회를 이해 Sort 객체 생성, 전달
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Question> spec = search(kw);
        return this.questionRepository.findAll(spec, pageable);

        /* 보충 : Specification 대신 Query를 직접 작성하여 사용하는 방법
        return this.questionRepository.findallByKeyword(kw, pageable);
        */
    }

    public Question getQuestion(Integer id){
        Optional<Question> question = this.questionRepository.findById(id);
        if(question.isPresent()){
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, SiteUser user){
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        this.questionRepository.save(q);
    }

    public void modify(Question question, String subject, String content){
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void delete(Question question){
        this.questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser){
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }
}
