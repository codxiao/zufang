package cn.xiao.zufang.web.controller;

import cn.xiao.zufang.base.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/404")
    public String errorPage(){
        return "404";
    }
    @GetMapping("/500")
    public String innerErrorPage(){
        return "500";
    }
    @GetMapping("/403")
    public String accessErrorPage(){
        return "403";
    }
    @GetMapping("/logout/page")
    public String logoutPage(){
        return "logout";
    }

//    @GetMapping("/helloword")
//    public String index1(){
//        return "hello";
//    }
//    @GetMapping("/get")
//    @ResponseBody
//    public ApiResponse get(){
//        return  ApiResponse.ofMessage(200,"success");
//    }

}
