package com.mysite.sbb;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

@Component // 스프링 부트가 관리하는 빈으로 등록되며, 등록된 컴포넌트는 템플릿에서 사용할 수 있게 됨
public class CommonUtil {
    // markdown 메서드는 마크다운 문법이 적용된 일반 텍스트를 html 문서로 변환해 리턴한다
    public String markdown(String markdown){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
