package com.adaa7.server.controller;

import com.adaa7.common.result.Result;
import com.adaa7.pojo.dto.UserDto;
import com.adaa7.pojo.vo.UserLoginVo;
import com.adaa7.server.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Tag(name = "admin登入接口")
public class LoginController {
    @Autowired
    LoginService loginService;
    @PostMapping("/login")
    @Operation(summary = "登入请求")
    @Parameters({
            @Parameter(name = "username",description = "用户名"),
            @Parameter(name = "password",description = "用户密码"),
    })
    @ResponseBody
    public Result<UserLoginVo> login(@RequestBody UserDto userDto){
        return Result.success(loginService.login(userDto));
    }

    @PostMapping("/register")
    @Operation(summary = "注册请求")
    @Parameters({
            @Parameter(name = "username",description = "用户名"),
            @Parameter(name = "password",description = "用户密码"),
    })
    public Result register(@RequestBody UserDto userDto) {
        return Result.success(loginService.register(userDto));
    }

}
