package com.adaa7.server.service;

import com.adaa7.common.result.PageResult;
import com.adaa7.pojo.dto.FileAddDTO;
import com.adaa7.pojo.dto.FileChangeDTO;
import com.adaa7.pojo.dto.FileDeleteDTO;
import com.adaa7.pojo.dto.FilePageQueryDTO;
import com.adaa7.pojo.entity.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    PageResult page(FilePageQueryDTO filePageQueryDTO);
    PageResult pageImage(FilePageQueryDTO filePageQueryDTO);
    PageResult pageVideo(FilePageQueryDTO filePageQueryDTO);

    PageResult pageAudio(FilePageQueryDTO filePageQueryDTO);

    String addFile(MultipartFile file, String filePid);

    String adddir(String filePid, String dirName);

    String fileDelete(FileDeleteDTO fileDeleteDTO);

    String fileChange(FileChangeDTO fileChangeDTO);

    File fileDownload(int fileId);


}
