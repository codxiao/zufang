package cn.xiao.zufang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/helloword")
    public String index1(){
        return "hello";
    }

}
