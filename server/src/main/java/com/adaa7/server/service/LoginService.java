package com.adaa7.server.service;

import com.adaa7.pojo.dto.UserDto;
import com.adaa7.pojo.vo.UserLoginVo;

public interface LoginService {

    UserLoginVo login(UserDto userDto);

    UserLoginVo register(UserDto userDto);
}
