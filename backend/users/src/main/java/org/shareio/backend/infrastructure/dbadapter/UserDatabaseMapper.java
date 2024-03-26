package org.shareio.backend.infrastructure.dbadapter;

import org.shareio.backend.core.model.UserSnapshot;
import org.shareio.backend.core.model.vo.Address;


public class UserDatabaseMapper {
    public static UserEntity map(UserSnapshot userSnapshot) {
        return new UserEntity(null, userSnapshot.userId().getId(), userSnapshot.email(),
                userSnapshot.name(), userSnapshot.dateOfBirth(),
                UserDatabaseMapper.mapAddress(userSnapshot.address()), UserDatabaseMapper.mapSecurity(userSnapshot));
    }

    public static AddressEntity mapAddress(final Address address) {
        return new AddressEntity(
                null,
                address.getCountry(),
                address.getRegion(),
                address.getCity(),
                address.getHouseNumber(),
                address.getFlatNumber(),
                address.getPostCode(),
                address.getLocation().getLatitude(),
                address.getLocation().getLongitude());
    }

    private static SecurityEntity mapSecurity(UserSnapshot userSnapshot) {
        return new SecurityEntity(
                null,
                userSnapshot.security().getPwHash(),
                userSnapshot.security().getRegistrationDate(),
                userSnapshot.security().getLastLoginDate());
    }
}
