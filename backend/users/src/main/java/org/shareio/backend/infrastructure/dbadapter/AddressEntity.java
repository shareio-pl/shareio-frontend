package org.shareio.backend.infrastructure.dbadapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shareio.backend.core.model.vo.Location;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {
    private String country;
    private String region;
    private String city;
    private String houseNumber;
    private String flatNumber;
    private String postCode;
    private Double latitude;
    private Double longitude;
}
