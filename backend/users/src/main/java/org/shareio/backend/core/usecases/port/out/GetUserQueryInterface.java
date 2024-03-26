package org.shareio.backend.core.usecases.port.out;

import org.shareio.backend.core.usecases.port.dto.GetUserDto;

import java.util.Optional;
import java.util.UUID;

public interface GetUserQueryInterface {
    Optional<GetUserDto> getUserDto(UUID id);
}
