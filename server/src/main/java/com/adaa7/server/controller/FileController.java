package com.adaa7.server.controller;

import com.adaa7.common.result.PageResult;
import com.adaa7.common.result.Result;
import com.adaa7.pojo.dto.FileChangeDTO;
import com.adaa7.pojo.dto.FileCreateDirDTO;
import com.adaa7.pojo.dto.FileDeleteDTO;
import com.adaa7.pojo.dto.FilePageQueryDTO;
import com.adaa7.server.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    FileService fileService;
    @GetMapping("/page")
    public Result<PageResult> page(FilePageQueryDTO filePageQueryDTO){
        return Result.success(fileService.page(filePageQueryDTO));
    }
    @PostMapping("/addfile")
    public Result addFile(@RequestParam("file") MultipartFile file, @RequestParam("filePid") String filePid){
        return Result.success(fileService.addFile(file,filePid));
    }
    @PostMapping("/adddir")
    public Result adddir(@RequestBody FileCreateDirDTO fileCreateDirDTO){
        return Result.success(fileService.adddir(fileCreateDirDTO.getFilePid(),fileCreateDirDTO.getDirName()));
    }
    @PostMapping("/delete")
    public Result fileDelete(@RequestBody FileDeleteDTO fileDeleteDTO){
        return Result.success(fileService.fileDelete(fileDeleteDTO));
    }
    @PostMapping("/change")
    public Result fileChange(@RequestBody FileChangeDTO fileChangeDTO){
        return Result.success(fileService.fileChange(fileChangeDTO));
    }
}
