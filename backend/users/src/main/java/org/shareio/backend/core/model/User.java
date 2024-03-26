package org.shareio.backend.core.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.shareio.backend.core.model.vo.Address;
import org.shareio.backend.core.model.vo.Security;
import org.shareio.backend.core.model.vo.UserId;

import java.time.LocalDateTime;
import java.util.UUID;

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

    public User() {
        // TODO: User(UserDTO userDTO)
        this(new UserId(new UUID(0L, 0L)), "", "", null, null, null);
    }

    public UserSnapshot toSnapshot() {
        return new UserSnapshot(this);
    }
}
