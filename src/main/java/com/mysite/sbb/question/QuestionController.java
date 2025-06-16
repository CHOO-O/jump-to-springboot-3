package com.mysite.sbb.question;
import com.mysite.sbb.answer.AnswerForm;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question") // URL 프리픽스.
@RequiredArgsConstructor // final이 붙은 속성을 포함하는 생성자를 자동으로 만들어주는 역할
@Controller
public class QuestionController {

//    private final QuestionRepository questionRepository;
    // 서비스를 사용함으로써 레포지토리를 직접 접근하지 않도록 함
    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page){
        // GET 방식으로 /list?page=0 과 같이 URL요청 받을거임. 참고로 첫페이지는 1이 아닌 0이다.
//        List<Question> questionList = this.questionRepository.findAll();
//        List<Question> questionList = this.questionService.getList();
        Page<Question> paging = this.questionService.getList(page);
//        model.addAttribute("questionList", questionList);
        model.addAttribute("paging", paging);
        // Model 객체는 템플릿과 자바 클래스 간 연결고리 역할, 여기에 값을 담으면 템플릿이 그 값을 사용할 수 있음.
        // 따로 객체 생성할 필요 없이 메서드에 매개변수로 지정하면 스프링부트가 자동으로 객체 생성함.
        return "question_list"; // 템플릿파일명
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm){
        // 변하는 값을 얻을 때 이 애너테이션을 이용한다.
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    // 메서드 오버로딩 : 매개변수의 형태가 다른 경우 한 클래스에서 동일한 메서드명을 사용할 수 있음.
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm){ // th:object 속성을 위해 QuestionForm 객체 전달
        return "question_form";
    }

    @PostMapping("/create")
//    public String questionCreate(@RequestParam(value="subject") String subject, @RequestParam(value="content") String content){
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult){
        // QuestionForm 사용을 위해 매개변수를 QuestionForm 객체로 변경하였음.
        // 이름이 같으면 연결되는 폼 바인딩 기능을 활용함
        // @Valid 애너테이션이 있어야 QuestionForm의 @NotEmpty, @Size가 동작함
        // BindingResult 매개변수는 항상 @Valid 매개변수 바로 뒤에 위치해야 함.
        if(bindingResult.hasErrors()){
            return "question_form";
        }
//        this.questionService.create(subject, content);
        this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/list";
    }
}
