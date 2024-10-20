package com.adaa7.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {
    private static final long serialVersionUID = 1L;
    private int fileId;
    private int userId;
    private String filePid;
    private String fileName;
    private String fileCover;
    private String filePath;
    private String fileMd5;
    private int folderType;
    private int fileCategory;
    private long fileSize;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime lastUpdateTime;
}
