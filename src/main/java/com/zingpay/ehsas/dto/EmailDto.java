package com.zingpay.ehsas.dto;

import lombok.Data;
import org.springframework.core.io.FileSystemResource;

/**
 * @author Bilal Hassan on 20-Nov-21
 * @project ehsas-integration-microservice
 */

@Data
public class EmailDto {
    private String to;
    private String text;
    private String subject;
    private FileSystemResource file;
}
