package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {
    // JSON 타입이라 ObjectMapper 필요
    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info(messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {},age = {} ", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        log.info(messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {},age = {} ", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) { // 여기서는 RequestBody 생략하면 안됨, 그럼 ModelAttribute 로 인식함
        // HttpEntity, RequestBody 를 사용하면 HTTP 메세지 컨버터가 HTTP message 바디의 내용을 우리가 원하는 문자로 바꿔준다.
        // HelloData helloData = objectMapper.readValue(messageBody, HelloData.class); // <- 컨버터가 이 코드를 대신 실행해주고 생성된 데이터를 매개변수 HelloData 에 넣어줌
        // HTTP 요청 시 content-type 이 application/json 인지 꼭 확인!! 맞아야 HTTP 메시지 컨버터가 실행됨
        log.info("username = {},age = {} ", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) { // 여기서는 RequestBody 생략하면 안됨, 그럼 ModelAttribute 로 인식함
        HelloData data = httpEntity.getBody();
        log.info("username = {},age = {} ", data.getUsername(), data.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) { // 여기서는 RequestBody 생략하면 안됨, 그럼 ModelAttribute 로 인식함
        // HTTP 컨버터가 들어올때도 적용이 되지만, ResponseBody 가 있으면 나갈 떄도 적용이 됨.
        // JSON 이 객체로 들어왔다가 나갈때 다시 객체가 된 경우
        log.info("username = {},age = {} ", data.getUsername(), data.getAge());
        return data;
    }
}
