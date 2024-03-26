package org.shareio.backend.core.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Address {
    private String country;
    private String region;
    private String city;
    private String houseNumber;
    private String flatNumber;
    private String postCode;
    private Location location;
}
