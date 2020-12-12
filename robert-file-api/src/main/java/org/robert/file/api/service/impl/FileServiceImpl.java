package org.robert.file.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.robert.file.api.config.OssConfigBean;
import org.robert.file.api.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private OssConfigBean ossConfigBean;

    @Override
    public String upload(String applicationName, String moduleName, MultipartFile file) {
        byte[] bytes = null;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
        }

        OSS ossClient = new OSSClientBuilder().build(OssConfigBean.endpoint,
                ossConfigBean.getAccessKeyId(), ossConfigBean.getAccessKeySecret());

        PutObjectResult putObject = ossClient.putObject(ossConfigBean.getBucketName(), file.getOriginalFilename(), new ByteArrayInputStream(bytes));
        log.info(JSON.toJSONString(putObject));
        ossClient.shutdown();
        return null;
    }
}
