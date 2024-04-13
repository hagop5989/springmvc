package hello.springmvc.basic.request;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class requestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest  request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        // 스트림은 byte 코드고 byte 코드로 문자를 받을떈 인코딩 방식을 지정해줘야만 한다(아님 default 값 써짐)

        log.info("messageBody={}",messageBody);
        response.getWriter().write("ok");
    }

    @PostMapping("/request-body-string-v2")
    // 스프링은 inputStream Reader, OutputStream Writer 둘다 지원한다.
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}",messageBody);
        responseWriter.write("ok");
    }

    @PostMapping("/request-body-string-v3")
    // 스프링은 inputStream Reader, OutputStream Writer 둘다 지원한다.
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        // http 메세지를 스펙화 해놓은 것
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}",messageBody);

        return new HttpEntity<>("ok");
    }

    @ResponseBody
    @PostMapping("/request-body-string-v4")
    // 스프링은 inputStream Reader, OutputStream Writer 둘다 지원한다.
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {
        // @RequestBody 진짜 많이씀
        // http 메세지를 스펙화 해놓은 것
        // 헤더 정보 필요시엔 HttpEntity 쓰거나 @RequestHeader 쓰면 됨
        // 이건 메시지 바디를 가져오는 것임, RequestParam, ModelAttribute 와 전혀 상관없는 방식
        log.info("messageBody={}",messageBody);
        return "ok";
    }
}
