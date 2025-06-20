package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page; // 페이징을 위한 클래스
import org.springframework.data.domain.Pageable; // 페이징을 처리하는 인터페이스
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.domain.Specification;

/* 보충 : Specification 대신 Query를 직접 작성하여 사용하는 방법
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
*/

public interface QuestionRepository extends JpaRepository<Question, Integer>{
    // 인터페이스는 껍데기..
    // 얘를 레포지토리라는것으로 만들기 위해 JpaRepository를 상속
    // JpaRepository는 JPA가 제공하는 인터페이스로, CRUD 작업 처리 메서드를 내장함
    // <Question, Integer> -> Question 엔티티로 레포지토리를 생성한다는 의미, 기본 키가 Integer임을 지정.

    /* 보충 : Specification 대신 Query를 직접 작성하여 사용하는 방법
    @Query("select "
            + "distinct q "
            + "from Question q "
            + "left outer join SiteUser u1 on q.author=u1 "
            + "left outer join Answer a on a.question=q "
            + "left outer join SiteUser u2 on a.auther=u2 "
            + "where "
            + "q.subject like %:kw% "
            + "or q.content like %:kw% "
            + "or u1.username like %:kw% "
            + "or a.content like %:kw% "
            + "or u2.username like %:kw% ")
        Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
    */

    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);
    Page<Question> findAll(Pageable pageable); // pageable 객체를 입력받아 page<question>타입 객체 리턴
    Page<Question> findAll(Specification<Question> spec, Pageable pageable); // 검색 결과 페이징해 반환
}
