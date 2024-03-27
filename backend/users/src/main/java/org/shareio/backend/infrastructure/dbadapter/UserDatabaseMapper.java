package org.shareio.backend.infrastructure.dbadapter;

import org.shareio.backend.core.model.UserSnapshot;


public class UserDatabaseMapper {
    public static UserEntity map(UserSnapshot userSnapshot) {
        return new UserEntity(null, userSnapshot.userId().getId(), userSnapshot.email(),
                userSnapshot.name(), userSnapshot.dateOfBirth(),
                AddressDatabaseMapper.map(userSnapshot.address()), UserDatabaseMapper.mapSecurity(userSnapshot));
    }

    private static SecurityEntity mapSecurity(UserSnapshot userSnapshot) {
        return new SecurityEntity(
                null,
                userSnapshot.security().getPwHash(),
                userSnapshot.security().getRegistrationDate(),
                userSnapshot.security().getLastLoginDate());
    }
}
