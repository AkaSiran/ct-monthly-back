package com.ruoyi.project.paper.dto;

import com.ruoyi.project.paper.domain.Pregnancy;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Flareon on 2022-2-10.
 */
@Data
public class RequestPregnancyDto extends Pregnancy
{
    private MultipartFile file;
}
