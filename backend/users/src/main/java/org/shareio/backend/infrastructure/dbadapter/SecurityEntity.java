package org.shareio.backend.infrastructure.dbadapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityEntity {
    private String pwHash;
    private LocalDateTime registrationDate;
    private LocalDateTime lastLoginDate;
}
