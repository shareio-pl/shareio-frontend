package org.shareio.backend.core.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.shareio.backend.core.model.vo.Address;
import org.shareio.backend.core.model.vo.Security;
import org.shareio.backend.core.model.vo.UserId;
import org.shareio.backend.core.usecases.port.dto.GetUserDto;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    @Setter(AccessLevel.NONE)
    private UserId userId;
    private String email;
    private String name;
    private LocalDateTime dateOfBirth;
    private Address address;
    private Security security;

    public static User fromDto(GetUserDto getUserDto) {

        return new User(
                getUserDto.getUserId(),
                getUserDto.getEmail(),
                getUserDto.getName(),
                getUserDto.getDateOfBirth(),
                new Address(
                        getUserDto.getCountry(),
                        null,
                        getUserDto.getCity(),
                        null,
                        null,
                        null,
                        null
                ),
                new Security(
                        null,
                        null,
                        getUserDto.getLastLoginDate()
                )
        );
    }

    public UserSnapshot toSnapshot() {
        return new UserSnapshot(this);
    }
}
