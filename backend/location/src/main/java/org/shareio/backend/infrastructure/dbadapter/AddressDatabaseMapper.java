package org.shareio.backend.infrastructure.dbadapter;

import org.shareio.backend.core.model.vo.Address;

public class AddressDatabaseMapper {
    public static AddressEntity map(final Address address) {
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
}
