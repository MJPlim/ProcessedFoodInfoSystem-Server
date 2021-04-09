package com.plim.plimserver.domain.user.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Role Test"})
@RestController
public class RoleTestController {

    @ApiOperation(value = "유저 권한 테스트", notes = "유저의 권한을 테스트한다")
    @GetMapping("api/v1/user")
    public String user() {
        return "user";
    }

    @ApiOperation(value = "관리자 권한 테스트", notes = "관리자의 권한을 테스트한다")
    @GetMapping("api/v1/admin")
    public String admin() {
        return "admin";
    }

}
