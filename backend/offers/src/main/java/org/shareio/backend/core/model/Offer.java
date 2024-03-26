package org.shareio.backend.core.model;

import org.shareio.backend.core.model.vo.Address;
import org.shareio.backend.core.model.vo.OfferId;
import org.shareio.backend.core.model.vo.PhotoId;

import java.time.LocalDateTime;


public class Offer {
    private OfferId offerId;
    private User owner;
    private Address address;
    private LocalDateTime creationDate;
    private User receiver;
    private LocalDateTime reservationDate;

    private String title;
    private String description;
    private PhotoId photoId;
}
