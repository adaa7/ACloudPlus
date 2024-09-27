package com.adaa7.server.mapper;

import com.adaa7.pojo.dto.UserDto;
import com.adaa7.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {


    void register(User user);
    User findUName(UserDto userDto);
}
