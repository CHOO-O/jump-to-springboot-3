package com.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;

@RequestMapping("/question") // URL 프리픽스.
@RequiredArgsConstructor // final이 붙은 속성을 포함하는 생성자를 자동으로 만들어주는 역할
@Controller
public class QuestionController {

//    private final QuestionRepository questionRepository;
    // 서비스를 사용함으로써 레포지토리를 직접 접근하지 않도록 함
    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model){
//        List<Question> questionList = this.questionRepository.findAll();
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        // Model 객체는 템플릿과 자바 클래스 간 연결고리 역할, 여기에 값을 담으면 템플릿이 그 값을 사용할 수 있음.
        // 따로 객체 생성할 필요 없이 메서드에 매개변수로 지정하면 스프링부트가 자동으로 객체 생성함.
        return "question_list"; // 템플릿파일명
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id){
        // 변하는 값을 얻을 때 이 애너테이션을 이용한다.
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

}
