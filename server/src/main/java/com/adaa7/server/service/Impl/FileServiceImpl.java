package com.adaa7.server.service.Impl;

import com.adaa7.common.context.BaseContext;
import com.adaa7.common.exception.*;
import com.adaa7.common.result.PageResult;
import com.adaa7.common.utils.FileUtil;
import com.adaa7.pojo.dto.FileChangeDTO;
import com.adaa7.pojo.dto.FileDeleteDTO;
import com.adaa7.pojo.dto.FilePageQueryDTO;
import com.adaa7.pojo.entity.File;
import com.adaa7.pojo.entity.User;
import com.adaa7.server.mapper.FileMapper;
import com.adaa7.server.mapper.LoginMapper;
import com.adaa7.server.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {
    @Autowired
    FileMapper fileMapper;
    @Autowired
    LoginMapper loginMapper;
    private static String[] removeFirstElement(String[] array) {
        if (array == null || array.length <= 1) {
            return new String[0];
        }
        String[] newArray = new String[array.length - 1];
        System.arraycopy(array, 1, newArray, 0, newArray.length);
        return newArray;
    }
    private String dirPidDetection(String filePid){
        if (filePid.equals("")) {
            throw new FilePidNullException("文件路径不为空");
        }
        String[] dirs = filePid.split("/");
        dirs = removeFirstElement(dirs);
        String dir = "";
        if(dirs.length!=0){
            if(dirs.length-1==0){
                dir="/";
                if(fileMapper.findDir(BaseContext.getCurrentId(),dirs[dirs.length-1],dir,1) == null) {
                    throw new PasswordErrorException("目录未查询");
                }
                return dir+dirs[dirs.length-1];
            }else {
                for (int i=0; i< dirs.length-1; i++){
                    dir+="/"+dirs[i];
                }
            }
            File findDir = fileMapper.findDir(BaseContext.getCurrentId(),dirs[dirs.length-1],dir,1);
            if(findDir == null) {
                throw new PasswordErrorException("目录未查询");
            }
            return dir+"/"+dirs[dirs.length-1];
        }else {
            return "/";
        }
    }
    @Override
    public PageResult page(FilePageQueryDTO filePageQueryDTO) {
        // 确保页码和每页大小有效
        if (filePageQueryDTO.getPage() <= 0 || filePageQueryDTO.getPageSize() <= 0) {
            throw new IllegalArgumentException("Page number and page size must be greater than zero.");
        }
        long Total = fileMapper.pageTotal(BaseContext.getCurrentId(),filePageQueryDTO.getFilePid());
        int page = (filePageQueryDTO.getPage()-1)*filePageQueryDTO.getPageSize();
        List<File> Page = fileMapper.page(BaseContext.getCurrentId(),filePageQueryDTO.getFilePid(),page,filePageQueryDTO.getPageSize());
        return getPageResult(Total, Page);
    }
    @Override
    public PageResult pageImage(FilePageQueryDTO filePageQueryDTO) {
        // 确保页码和每页大小有效
        if (filePageQueryDTO.getPage() <= 0 || filePageQueryDTO.getPageSize() <= 0) {
            throw new IllegalArgumentException("Page number and page size must be greater than zero.");
        }
        long Total = fileMapper.pageTotal(BaseContext.getCurrentId(),filePageQueryDTO.getFilePid());
        int page = (filePageQueryDTO.getPage()-1)*filePageQueryDTO.getPageSize();
        List<File> Page = fileMapper.pageImage(BaseContext.getCurrentId(),page,filePageQueryDTO.getPageSize());
        return getPageResult(Total, Page);
    }

    @Override
    public PageResult pageVideo(FilePageQueryDTO filePageQueryDTO) {
        // 确保页码和每页大小有效
        if (filePageQueryDTO.getPage() <= 0 || filePageQueryDTO.getPageSize() <= 0) {
            throw new IllegalArgumentException("Page number and page size must be greater than zero.");
        }
        long Total = fileMapper.pageTotal(BaseContext.getCurrentId(),filePageQueryDTO.getFilePid());
        int page = (filePageQueryDTO.getPage()-1)*filePageQueryDTO.getPageSize();
        List<File> Page = fileMapper.pageVideo(BaseContext.getCurrentId(),page,filePageQueryDTO.getPageSize());
        return getPageResult(Total, Page);
    }

    @Override
    public PageResult pageAudio(FilePageQueryDTO filePageQueryDTO) {
        // 确保页码和每页大小有效
        if (filePageQueryDTO.getPage() <= 0 || filePageQueryDTO.getPageSize() <= 0) {
            throw new IllegalArgumentException("Page number and page size must be greater than zero.");
        }
        long Total = fileMapper.pageTotal(BaseContext.getCurrentId(),filePageQueryDTO.getFilePid());
        int page = (filePageQueryDTO.getPage()-1)*filePageQueryDTO.getPageSize();
        List<File> Page = fileMapper.pageAudio(BaseContext.getCurrentId(),page,filePageQueryDTO.getPageSize());
        return getPageResult(Total, Page);
    }

    private PageResult getPageResult(long total, List<File> page) {
        PageResult pageResult = new PageResult();
        pageResult.setTotal(total);
        pageResult.setRecords(page);
        User user = loginMapper.findUID(BaseContext.getCurrentId());
        long roleSize = loginMapper.findRoleSize(user.getPermissionsRole());
        pageResult.setTotalSize(roleSize);
        pageResult.setUsedSize(user.getUseSize());
        return pageResult;
    }

    @Override
    public String addFile(MultipartFile file, String filePid) {
        File fileData = new File();
        User user =loginMapper.findUID(BaseContext.getCurrentId());
        if(user.getUseSize()+file.getSize() >= loginMapper.findRoleSize(user.getPermissionsRole())){
            throw new FileNotFindException("文件过大，请扩容");
        }
        try{
            fileData.setFileMd5(org.springframework.util.DigestUtils.md5DigestAsHex(file.getInputStream()));
        }catch(Exception e) {
            throw new FilePidNullException("MD5加载失败");
        }
        String dir = dirPidDetection(filePid);
        String fileName = file.getOriginalFilename();
        fileData.setUserId(BaseContext.getCurrentId());
        fileData.setFilePid(dir);
        String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')+1);
        String baseName = file.getOriginalFilename().substring(0, fileName.lastIndexOf("."));
        if (fileMapper.findFile(BaseContext.getCurrentId(), fileName, filePid,0) == null) {
            fileData.setFileName(fileName);
        }else {
            fileName = baseName+"_"+UUID.randomUUID().toString().substring(0, 8).replace("-","")+"."+fileSuffix;
            fileData.setFileName(fileName);
        }
        try {
            File Md5 = fileMapper.findMd5(fileData.getFileMd5());
            if(Md5!=null){
                fileData.setFilePath(Md5.getFilePath());
            }else {
                try {
                switch (fileSuffix) {
                    case "mp4" -> {
                        fileData.setFilePath(FileUtil.uploadsFile(file));
                        fileData.setFileCover("/admin/uploads/mp5default.png");
                        fileData.setFileCategory(1);
                    }
                    case "mp3" -> {
                        fileData.setFilePath(FileUtil.uploadsFile(file));
                        fileData.setFileCover("/admin/uploads/musicdefault.png");
                        fileData.setFileCategory(2);
                    }
                    case "png", "jpeg", "jpg" -> {
                        String path = FileUtil.uploadsFileImager(file);
                        int lastSlashIndex = path.lastIndexOf("/");
                        String filename = path.substring(lastSlashIndex + 1);
                        fileData.setFileCover("/admin/uploads/"+filename);
                        fileData.setFilePath(path);
                        fileData.setFileCategory(3);
                    }
                    case "txt", "doc" -> {
                        fileData.setFilePath(FileUtil.uploadsFile(file));
                        fileData.setFileCover("/admin/uploads/textdefault.png");
                        fileData.setFileCategory(4);
                    }
                    default -> {
                        fileData.setFilePath(FileUtil.uploadsFile(file));
                        fileData.setFileCover("/admin/uploads/other-default.png");
                        fileData.setFileCategory(5);
                    }
                }
                } catch (IOException e) {
                    throw new FilePidNullException("文件上传失败");
                }
            }
            fileData.setFileSize(file.getSize());
            fileData.setFolderType(0);
            try {
                fileMapper.uploadFile(fileData);
                System.out.println(fileData);
                loginMapper.uploadFileSize(fileData);
            }catch (Exception e) {
                throw new FilePidNullException("Sql上传失败");
            }
        }catch (Exception e) {
            throw new FilePidNullException("Md5查询失败");
        }
        return "上传成功";
    }

    @Override
    public String adddir(String filePid, String dirName) {
        String dir = dirPidDetection(filePid);
        if (dirName.equals("")){
            dirName="新建文件";
            int i = 1;
            while (fileMapper.findFile(BaseContext.getCurrentId(),dirName,dir,1)!=null){
                dirName="新建文件_" + i;
                i++;
            }
        }else {
            if(fileMapper.findFile(BaseContext.getCurrentId(),dirName,dir,1)!=null) {
                throw new FilePidNullException("命名重复");
            }
        }
        File dirFile = File.builder()
                .userId(BaseContext.getCurrentId())
                .filePid(dir)
                .fileName(dirName)
                .fileCover("/admin/uploads/dir-default.png")
                .fileMd5("Dir")
                .folderType(1)
                .fileCategory(5)
                .filePath("Dir")
                .fileSize(0)
                .build();
        try {
            fileMapper.uploadFile(dirFile);
        }catch (Exception e) {
            throw new FilePidNullException("Sql上传失败");
        }
        return "创建成功";
    }

    @Override
    public String fileDelete(FileDeleteDTO fileDeleteDTO) {
        File file = fileMapper.findFileById(BaseContext.getCurrentId(),fileDeleteDTO.getFileId());
        if(file==null){
            throw new FileNotFindException("文件未找到");
        }
        if(file.getFolderType()==1){
            String filePid="";
            if (file.getFilePid().equals("/")) {
                filePid= "/"+file.getFileId();
            }else {
                filePid= file.getFilePid()+"/"+file.getFileId();
            }
            List<File> fileList = fileMapper.findFiles(BaseContext.getCurrentId(),filePid);
            long size = 0;
            for (File item : fileList) {
                size+=item.getFileSize();
            }
            System.out.println(size);
            fileMapper.deleteDir(BaseContext.getCurrentId(),filePid);
            fileMapper.deleteFile(BaseContext.getCurrentId(),file.getFileId());
            file.setFileSize(size);
            loginMapper.deleteFileSize(file);
        }else {
            fileMapper.deleteFile(BaseContext.getCurrentId(),file.getFileId());
            loginMapper.deleteFileSize(file);
        }
        return "删除成功";
    }
    @Override
    public String fileChange(FileChangeDTO fileChangeDTO) {
        File file = fileMapper.findFileById(BaseContext.getCurrentId(),fileChangeDTO.getFileId());
        if(file==null){
            throw new FileNotFindException("文件未找到");
        }
        try {
            fileMapper.fileChange(BaseContext.getCurrentId(),file.getFileId(),fileChangeDTO.getNewFileName());
        }catch (Exception e) {
            throw new FileChangeException("重命名失败");
        }
        if (file.getFolderType()==1){
            return "目录夹命名成功";
        }
        return "文件重命名成功";
    }

    @Override
    public File fileDownload(int fileId) {
        File file = fileMapper.findFileById(BaseContext.getCurrentId(),fileId);
        if(file==null){
            throw new FileNotFindException("文件未找到");
        }
        if(file.getFolderType()==1){
            throw new FileNotFindException("需要一个文件");
        }
        return file;
    }
}
