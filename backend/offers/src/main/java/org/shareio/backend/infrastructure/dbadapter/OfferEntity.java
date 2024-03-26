package org.shareio.backend.infrastructure.dbadapter;

import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferEntity {
    private UUID offerId;
    @OneToOne
    private UserEntity owner;
    @OneToOne
    private AddressEntity address;
    private LocalDateTime creationDate;
    @OneToOne
    private UserEntity receiver;
    private LocalDateTime reservationDate;

    private String title;
    private String description;
    private UUID photoId;
}
