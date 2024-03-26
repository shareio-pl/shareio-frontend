package org.shareio.backend.core.usecases.port.in;

import org.shareio.backend.core.model.UserSnapshot;

import java.util.UUID;

public interface GetUserUseCaseInterface {
    UserSnapshot getUserSnapshot(UUID id);
}
