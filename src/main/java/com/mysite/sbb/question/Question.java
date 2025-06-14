package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;

import com.mysite.sbb.answer.Answer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // 일반적으로 엔티티 만들땐 Setter 안쓰는걸 권함. 하지만 일단 여기선 복잡도 낮추고 설명 쉽게하기 위해 썼음. (엔티티는 데이터베이스와 바로 연결되기 때문에 데이터를 자유롭게 변경할 수 있는 Setter메서드를 금지하는것)
@Entity // Question 클래스를 엔티티로 인식시킴
public class Question {
    @Id // id라는 속성을 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 이 id라는 속성에 값을 일일히 입력하지 않아도 자동으로 1씩 증가 시킴
    // 괄호 안의 strategy는 번호 생성 방법을 지정하는 부분. 이걸 생략하면 이 애너테이션 달린 모든 속성에 번호를 차례로 생성해서 고유번호가 아니게 됨. 지금 쓴 옵션 제일 마니 씀.
    private Integer id;

    @Column(length = 200) // 열 설정. 길이설정 해줬음.
    private String subject;

    @Column(columnDefinition = "TEXT") // columnDefinition은 텍스트 데이터로 넣겠다는 뜻
    private String content;

    private LocalDateTime createDate;
    // 참고로 여기서 카멜케이스로 이름을 작성하면 데이터베이스 테이블에서는 자연스럽게 create_date와 같이 언더바+소문자로 변경된다

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;
}
