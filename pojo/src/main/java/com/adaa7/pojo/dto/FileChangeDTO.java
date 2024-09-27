package com.adaa7.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileChangeDTO implements Serializable {
    private String newFileName;
    private int fileId;
}