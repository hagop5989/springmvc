package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "Hello!");
        return mav;
    }

    @RequestMapping("/response-view-v2") // 적당함
    public String responseViewV2(Model model) {
        model.addAttribute("data","hello!@");
        return "response/hello";
    }

    @RequestMapping("/response/hello") // 비추천, 컨트롤러 뷰 이름과 논리적이름이 같으면 생략가능, 명시성 너무 떨어짐
    public void responseViewV3(Model model) {
        model.addAttribute("data","hello!@");
    }


}
