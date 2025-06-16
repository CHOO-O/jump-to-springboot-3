package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page; // 페이징을 위한 클래스
import org.springframework.data.domain.Pageable; // 페이징을 처리하는 인터페이스

public interface QuestionRepository extends JpaRepository<Question, Integer>{
    // 인터페이스는 껍데기..
    // 얘를 레포지토리라는것으로 만들기 위해 JpaRepository를 상속
    // JpaRepository는 JPA가 제공하는 인터페이스로, CRUD 작업 처리 메서드를 내장함
    // <Question, Integer> -> Question 엔티티로 레포지토리를 생성한다는 의미, 기본 키가 Integer임을 지정.

    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);
    Page<Question> findAll(Pageable pageable); // pageable 객체를 입력받아 page<question>타입 객체 리턴
}
