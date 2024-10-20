package com.adaa7.server.mapper;

import com.adaa7.pojo.dto.UserDto;
import com.adaa7.pojo.entity.File;
import com.adaa7.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {


    void register(User user);
    User findUName(UserDto userDto);

    void uploadFileSize(File fileData);

    User findUID(@Param("userId") int userId);

    long findRoleSize(@Param("permissionsRole")Integer permissionsRole);

    void deleteFileSize(File file);
}
