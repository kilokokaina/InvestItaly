package com.work.investitaly.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class AdvertiseDTO {

    private String title;
    private String address;
    private String description;
    private String thumbName;
    private String type;

    private float square;
    private float price;

    private int bedroom;
    private int bathroom;

    private MultipartFile[] files;

}
