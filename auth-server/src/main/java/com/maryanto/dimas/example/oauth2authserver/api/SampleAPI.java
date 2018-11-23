package com.maryanto.dimas.example.oauth2authserver.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/example")
public class SampleAPI {

    @GetMapping("/data")
    public Map<String, Object> result(Principal principal) {
        Map<String, Object> param = new HashMap<>();
        param.put("username", principal.getName());
        param.put("time", new Date());
        return param;
    }
}
