package com.adaa7.server;

import com.adaa7.common.exception.AccountNotFoundException;
import com.adaa7.common.exception.PasswordErrorException;
import com.adaa7.common.utils.JwtUtil;
import com.adaa7.pojo.dto.UserDto;
import com.adaa7.pojo.entity.User;
import com.adaa7.pojo.vo.UserLoginVo;
import com.adaa7.server.mapper.LoginMapper;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServerApplicationTests{

}