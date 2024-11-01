package com.work.investitaly.model;

import lombok.Getter;

@Getter
public enum RealEstateType {

    RESIDENTIAL ("Жилая"),
    COMMERCIAL ("Коммерческая");

    private final String type;

    RealEstateType(String type) {
        this.type = type;
    }

}
