package com.adaa7.server.controller;

import com.adaa7.common.exception.BeanException;
import com.adaa7.common.result.PageResult;
import com.adaa7.common.result.Result;
import com.adaa7.pojo.dto.FileChangeDTO;
import com.adaa7.pojo.dto.FileCreateDirDTO;
import com.adaa7.pojo.dto.FileDeleteDTO;
import com.adaa7.pojo.dto.FilePageQueryDTO;
import com.adaa7.pojo.entity.File;
import com.adaa7.server.service.FileService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    @GetMapping("/download/{fileId}")
    public void fileDownload(@PathVariable("fileId") Integer fileId, HttpServletResponse response) throws IOException {
        File filePath = fileService.fileDownload(fileId);
        java.io.File file = new java.io.File(filePath.getFilePath());
        ServletOutputStream os = null;
        try {
            response.setCharacterEncoding("UTF-8");
            os = response.getOutputStream();
            response.setHeader("Content-Type","text/plain");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.addHeader("Content-Disposition", URLEncoder.encode(filePath.getFileName(),"UTF-8"));
            response.setContentType("application/octet-stream");
            os.write(Files.readAllBytes(Paths.get(file.getPath())));
            os.flush();
        }catch (Exception e){
            os.flush();
            os.close();
            throw new BeanException("下载失败");
        }finally {
            os.flush();
            os.close();
        }
    }
}