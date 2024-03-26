package org.shareio.backend.infrastructure.dbadapter;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private UUID userId;
    private String email;
    private String name;
    private LocalDateTime dateOfBirth;
    @OneToOne
    private AddressEntity address;
    @OneToOne
    private SecurityEntity security;
}
