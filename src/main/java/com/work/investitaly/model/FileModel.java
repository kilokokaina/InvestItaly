package com.work.investitaly.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class FileModel {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "file_id")
    private Long fileId;

    private String fileName;
    private String filePath;

    private Date date = new Date();

}
