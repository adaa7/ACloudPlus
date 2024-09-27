package com.adaa7.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "请求 User 对象")
public class UserDto {
    @Schema(description = "username 用户名", example = "username 必填参数")
    private String username;
    @Schema(description = "password 密码", example = "password 必填参数")
    private String password;
}
