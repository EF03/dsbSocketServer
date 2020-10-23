package com.imi.dsbsocket.controller;

import com.imi.dsbsocket.util.JwtUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ron
 * @date 2020/10/22 上午 09:39
 */
@Controller
public class SocketController {
    @GetMapping("/getToken")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("token", "token");
        return JwtUtils.getToken(map);
    }
}
