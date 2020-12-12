package org.robert.file.api.service.impl;

import com.aliyun.oss.OSS;
import lombok.extern.slf4j.Slf4j;
import org.robert.file.api.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private OSS ossClient;

    @Override
    public String upload(String applicationName, String moduleName, MultipartFile file) {
        return null;
    }
}
