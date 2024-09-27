package com.adaa7.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "请求 FilePageQueryDTO 对象")
public class FileAddDTO {
    private String FilePid;
    private String FileName;
}
