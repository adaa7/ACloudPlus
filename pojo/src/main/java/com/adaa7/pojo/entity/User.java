package com.adaa7.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userId;
    private String userName;
    private Integer permissionsRole;
    private String password;
    private String avatar;
    private int status;
    private long useSize;
    private Date createTime;
    private Date changeTime;
}
