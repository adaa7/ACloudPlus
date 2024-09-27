package com.adaa7.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "请求 FilePageQueryDTO 对象")
public class FilePageQueryDTO {
    //页码
    @Schema(description = "页码")
    private int page;
    //每页记录数
    @Schema(description = "每页记录数")
    private int pageSize;
    //文件父目录
    //文件父目录
    @Schema(description = "文件父目录")
    private String filePid;
}
