package cn.xiao.zufang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Controller
public class ZufangApplication {

    @GetMapping("/hello")
    public String test(){
        return "heel";
    }

    public static void main(String[] args) {
        SpringApplication.run(ZufangApplication.class, args);
    }
}
