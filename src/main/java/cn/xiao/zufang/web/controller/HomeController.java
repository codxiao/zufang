package cn.xiao.zufang.web.controller;

import cn.xiao.zufang.base.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/helloword")
    public String index1(){
        return "hello";
    }
    @GetMapping("/get")
    @ResponseBody
    public ApiResponse get(){
        return  ApiResponse.ofMessage(200,"success");
    }

}
