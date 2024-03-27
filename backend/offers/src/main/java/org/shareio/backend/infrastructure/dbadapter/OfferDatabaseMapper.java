package org.shareio.backend.infrastructure.dbadapter;

import org.shareio.backend.core.model.OfferSnapshot;

public class OfferDatabaseMapper {
    public static OfferEntity map(OfferSnapshot offerSnapshot) {
        return new OfferEntity(offerSnapshot.offerId().getId(),
                UserDatabaseMapper.map(offerSnapshot.owner()),
                AddressDatabaseMapper.map(offerSnapshot.address()),
                offerSnapshot.creationDate(),
                UserDatabaseMapper.map(offerSnapshot.receiver()),
                offerSnapshot.reservationDate(),
                offerSnapshot.title(),
                offerSnapshot.description(),
                offerSnapshot.photoId().getId()
        );
    }
}
