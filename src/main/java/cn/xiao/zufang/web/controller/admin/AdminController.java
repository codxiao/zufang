package cn.xiao.zufang.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin/center")
    public String center(){
        return "admin/center";
    }
    @GetMapping("admin/welcome")
    public String wekcomePage(){
        return "admin/welcome";
    }
    @GetMapping("user/login")
    public String loginPage(){
        return "user/login";
    }

}
