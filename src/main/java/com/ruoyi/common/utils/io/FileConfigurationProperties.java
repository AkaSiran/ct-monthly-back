package com.ruoyi.common.utils.io;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "file")
public class FileConfigurationProperties
{

    private String saveUrl;

    private String localFileRequest;
}
