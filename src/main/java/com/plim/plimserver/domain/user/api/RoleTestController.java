package com.plim.plimserver.domain.user.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleTestController {

    @GetMapping("api/v1/user")
    public String user() {
        return "user";
    }

    @GetMapping("api/v1/admin")
    public String admin() {
        return "admin";
    }

}
