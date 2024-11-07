package com.work.investitaly.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Request {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long requestId;

    private String customerName;
    private String customerEmail;
    private String customerPhone;

    private long advertiseId;

    private Date date = new Date();

}
