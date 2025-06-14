package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer>{
    // 인터페이스는 껍데기..
    // 얘를 레포지토리라는것으로 만들기 위해 JpaRepository를 상속
    // JpaRepository는 JPA가 제공하는 인터페이스로, CRUD 작업 처리 메서드를 내장함
    // <Question, Integer> -> Question 엔티티로 레포지토리를 생성한다는 의미, 기본 키가 Integer임을 지정.

    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);
}
