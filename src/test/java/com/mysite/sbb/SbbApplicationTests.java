package com.mysite.sbb;

import com.mysite.sbb.answer.AnswerService;
import com.mysite.sbb.question.QuestionService;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@Autowired // 의존성 주입 -> QuestionRepository의 객체를 주입함.
//	private AnswerRepository answerRepository;
//    private AnswerService questionService;
    private QuestionService questionService;


//    @Transactional
	@Test
	void testJpa(){
//		 1.
//		Question q1 = new Question();
//		q1.setSubject("sbb가 무엇인가요?");
//		q1.setContent("sbb에 대해 알고 싶습니다.");
//		q1.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q1);
//
//		Question q2 = new Question();
//		q2.setSubject("스프링 부트 모델 질문입니다.");
//		q2.setContent("id는 자동으로 생성되나요?");
//		q2.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q2);

		// 2.
//		List<Question> all = this.questionRepository.findAll();
//		// findall 은 SELECT * FROM QUESTION이랑 같은 결과 도출함.
//		assertEquals(2, all.size());
//		// assertEquals -> 예상 결과가 실제 결과랑 같은가? 다르면 실패처리됨.
//
//		Question q = all.get(0);
//		assertEquals("sbb가 무엇인가요?", q.getSubject());

		// 3.
//		Optional<Question> oq = this.questionRepository.findById(1);
//		// id값으로 조회, Optional -> 존재할수도, 아닐수도 있어서 리턴타입을 Optional로 함
//		if(oq.isPresent()){
//			// isPresent -> 값이 존재하는가?
//			Question q = oq.get();
//			assertEquals("sbb가 무엇인가요?", q.getSubject());
//		}

		// 4.
//		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
//		// 기본적으로 제공하지 않는 메서드이기 때문에 questionRepository 인터페이스에서 선언해주어야 한다.
//		// 헐 근데 어떻게 그냥 선언만 하고 구현은 안했는데 됐지?
//		// -> JPA에 레포지토리의 메서드명을 분석해 쿼리를 만들고 실행하는 기능이 있기 때문
//		// -> 그래서 findBy + 엔티티 속성명 과 같이 메서드를 작성했기에 해당 속성값으로 findBy를 수행한 것.
//		assertEquals(1, q.getId());

		// 5.
//		Question q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해 알고 싶습니다.");
//		assertEquals(1, q.getId());

		// 6.
//		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
//		Question q = qList.get(0);
//		assertEquals("sbb가 무엇인가요?", q.getSubject());

		// 7.
//		Optional<Question> oq = this.questionRepository.findById(1);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		q.setSubject("수정된 제목");
//		this.questionRepository.save(q);

		// 8.
//		assertEquals(2, this.questionRepository.count());
//		Optional<Question> oq = this.questionRepository.findById(1);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		this.questionRepository.delete(q);
//		assertEquals(1, this.questionRepository.count());

		// 9.
//		Optional<Question> oq = this.questionRepository.findById(2);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//
//		Answer a = new Answer();
//		a.setContent("네 자동으로 생성됩니다.");
//		a.setQuestion(q);
//		a.setCreateDate(LocalDateTime.now());
//		this.answerRepository.save(a);

		// 10.
//		Optional<Answer> oa = this.answerRepository.findById(1);
//		assertTrue(oa.isPresent());
//		Answer a = oa.get();
//		assertEquals(2, a.getQuestion().getId());

//		// 11.
//		Optional<Question> oq = this.questionRepository.findById(2);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		// 이대로 실행하면 DB 세션이 findById를 수행한 뒤 끊겨 아래 코드는 오류가 발생한다.
//		// 따라서 @Transactional 애너테이션을 넣어줘야 한다.
//
//		List<Answer> answerList = q.getAnswerList();
//
//		assertEquals(1, answerList.size());
//		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());

        // insert paging test data
        for(int i = 0; i < 300; i++){
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용 없음 ";
            this.questionService.create(subject, content, null);
        }
	}
}
