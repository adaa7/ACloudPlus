package com.adaa7.server.mapper;

import com.adaa7.pojo.entity.File;
import com.adaa7.pojo.entity.FilePage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileMapper {

    File findFile(
            @Param("userId") int userId,
            @Param("fileName") String fileName,
            @Param("filePid") String filePid,
            @Param("folderType")int folderType
    );
    File findDir(
            @Param("userId") int userId,
            @Param("fileId") String fileId,
            @Param("filePid") String filePid,
            @Param("folderType")int folderType
    );
    File findFileById(
            @Param("userId") int userId,
            @Param("fileId") int fileId
    );
    File findMd5(@Param("fileMd5") String fileMd5);

    void uploadFile(File fileData);

    boolean deleteDir(@Param("userId")int userId,@Param("filePid") String filePid);

    void deleteFile(@Param("userId")int userId,@Param("fileId") int fileId);

    void fileChange(@Param("userId")int userId,
                    @Param("fileId") int fileId,
                    @Param("newFileName") String newFileName);

    List<File> page(@Param("userId") int userId, @Param("filePid") String filePid,
                    @Param("page") int page, @Param("pageSize") int pageSize);

    long pageTotal(@Param("userId") int userId, @Param("filePid") String filePid);
}
