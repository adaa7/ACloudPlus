package com.adaa7.server.service.Impl;

import com.adaa7.common.exception.AccountNotFoundException;
import com.adaa7.common.exception.PasswordErrorException;
import com.adaa7.common.utils.JwtUtil;
import com.adaa7.pojo.dto.UserDto;
import com.adaa7.pojo.entity.User;
import com.adaa7.pojo.vo.UserLoginVo;
import com.adaa7.server.mapper.LoginMapper;
import com.adaa7.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    LoginMapper loginMapper;
    @Override
    public UserLoginVo login(UserDto userDto) {
        UserLoginVo userLoginVo = new UserLoginVo();
        User user = loginMapper.findUName(userDto);
        if(user == null) {
            throw new AccountNotFoundException("用户名不存在");
        }
        if(!user.getPassword().equals(userDto.getPassword())){
            throw new PasswordErrorException("密码错误");
        }
        userLoginVo.setUsername(user.getUserName());
        userLoginVo.setAvatar(user.getAvatar());
        userLoginVo.setId(user.getUserId());
        userLoginVo.setToken(JwtUtil.createJwtToken(user.getUserId()));
        return userLoginVo;
    }

    @Override
    public UserLoginVo register(UserDto userDto) {
        if(loginMapper.findUName(userDto)!=null){
            throw new AccountNotFoundException("账号已存在");
        }
        User user = User.builder()
                .userName(userDto.getUsername())
                .password(userDto.getPassword())
                .avatar("/uploads/default.jpeg")
                .status(1)
                .build();
        try {
            loginMapper.register(user);
        }catch (Exception e){
            throw new AccountNotFoundException("账号注册失败");
        }
        return this.login(userDto);
    }
}
