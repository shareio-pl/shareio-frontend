package org.shareio.backend.core.model;

import org.shareio.backend.core.model.vo.Address;
import org.shareio.backend.core.model.vo.OfferId;
import org.shareio.backend.core.model.vo.PhotoId;

import java.time.LocalDateTime;

public record OfferSnapshot(OfferId offerId, User owner, Address address, LocalDateTime creationDate, User receiver,
                            LocalDateTime reservationDate, String title, String description, PhotoId photoId) {
    public OfferSnapshot(Offer offer) {
        this(offer.getOfferId(), offer.getOwner(), offer.getAddress(), offer.getCreationDate(),
                offer.getReceiver(), offer.getReservationDate(), offer.getTitle(), offer.getDescription(), offer.getPhotoId());
    }
}
