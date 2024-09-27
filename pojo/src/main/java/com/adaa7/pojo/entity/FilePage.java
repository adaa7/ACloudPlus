package com.adaa7.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilePage {
    private static final long serialVersionUID = 1L;
    private int fileId;
    private int userId;
    private String filePid;
    private String fileName;
    private String fileCover;
    private int folderType;
    private int fileCategory;
    private Date createTime;
    private Date lasUpdateTime;
}
