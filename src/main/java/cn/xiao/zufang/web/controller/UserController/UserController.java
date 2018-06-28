package cn.xiao.zufang.web.controller.UserController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/user/login")
    public String userLogin(){
        return "user/login";
    }
    @GetMapping("/user/center")
    public String center(){
        return "user/center";
    }

}
