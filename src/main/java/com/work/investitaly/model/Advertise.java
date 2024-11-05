package com.work.investitaly.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Advertise {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String placement;
    private RealEstateType type;
    private String status;

    @Lob
    private String text;

    private float square;
    private float pricePerSquare;

    private int bedroomCount;
    private int bathroomCount;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "add_photos", joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    private Set<FileModel> photos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "file_id")
    private FileModel thumbnail;

    private Date date = new Date();

}
