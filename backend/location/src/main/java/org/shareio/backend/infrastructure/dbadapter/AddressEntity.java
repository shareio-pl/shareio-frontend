package org.shareio.backend.infrastructure.dbadapter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String country;
    private String region;
    private String city;
    private String houseNumber;
    private String flatNumber;
    private String postCode;
    private Double latitude;
    private Double longitude;
}
