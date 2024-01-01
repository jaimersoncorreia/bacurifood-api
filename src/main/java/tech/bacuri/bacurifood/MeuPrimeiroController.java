package tech.bacuri.bacurifood;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MeuPrimeiroController {
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello";
    }
}
