package org.robert.file.api.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssClientConfig {

    @Autowired
    private OssConfigBean ossConfigBean;

    @Bean
    public OSS getOssClient () {
        OSS oss = new OSSClientBuilder().build(OssConfigBean.endpoint,
                ossConfigBean.getAccessKeyId(), ossConfigBean.getAccessKeySecret());
        return oss;
    }

}
