package org.robert.file.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.robert.file.api.config.OssConfigBean;
import org.robert.file.api.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private OssConfigBean configBean;

    @Override
    public String upload(String applicationName, String moduleName, MultipartFile file) {
        byte[] bytes = null;
        PutObjectResult putObject = null;
        try {
            bytes = file.getBytes();
            OSS ossClient = new OSSClientBuilder().build(configBean.getEndpoint(),
                    configBean.getAccessKeyId(), configBean.getAccessKeySecret());

            putObject = ossClient.putObject(configBean.getBucketName(),
                    file.getOriginalFilename(), new ByteArrayInputStream(bytes));
            log.info(JSON.toJSONString(putObject));
            ossClient.shutdown();
            return putObject.getETag();
        } catch (IOException e) {
            log.error("上传文件到oss失败，{}",e);
        }

        return null;
    }

    @Override
    public String getSignUrl(String fileName) {
        OSS ossClient = new OSSClientBuilder().build(configBean.getEndpoint(),
                configBean.getAccessKeyId(), configBean.getAccessKeySecret());
        // 生成签名URL。
        Date expiration = null;
        try {
            expiration = DateUtil.parseRfc822Date(LocalDateTime.now().plusMinutes(10).toString());
        } catch (ParseException e) {
        }
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(configBean.getBucketName(), fileName, HttpMethod.PUT);
       // 设置过期时间。
        request.setExpiration(expiration);
        // 设置ContentType。
        request.setContentType("application/octet-stream");
        // 添加用户自定义元信息。
       // 通过HTTP PUT请求生成签名URL。
        URL signedUrl = ossClient.generatePresignedUrl(request);
        System.out.println("signed url for putObject: " + signedUrl);
        return signedUrl.getPath();
    }
}
