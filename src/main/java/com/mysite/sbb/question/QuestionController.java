package com.mysite.sbb.question;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import java.security.Principal;
import java.util.List;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question") // URL 프리픽스.
@RequiredArgsConstructor // final이 붙은 속성을 포함하는 생성자를 자동으로 만들어주는 역할
@Controller
public class QuestionController {
    // 서비스를 사용함으로써 레포지토리를 직접 접근하지 않도록 함
    private final QuestionService questionService;
    private final UserService userService;

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
    @PreAuthorize("isAuthenticated()") // 이 애너테이션이 붙은 메서드는 로그인한 경우에만 실행된다 -> 로그인한 사용자만 호출할 수 있다 -> 호출되면 로그인 페이지로 강제 이동된다.
    public String questionCreate(QuestionForm questionForm){ // th:object 속성을 위해 QuestionForm 객체 전달
        return "question_form";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal){
        // QuestionForm 사용을 위해 매개변수를 QuestionForm 객체로 변경하였음.
        // 이름이 같으면 연결되는 폼 바인딩 기능을 활용함
        // @Valid 애너테이션이 있어야 QuestionForm의 @NotEmpty, @Size가 동작함
        // BindingResult 매개변수는 항상 @Valid 매개변수 바로 뒤에 위치해야 함.
        if(bindingResult.hasErrors()){
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal){
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id){
        if(bindingResult.hasErrors()){
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id){
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id){
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }
}
