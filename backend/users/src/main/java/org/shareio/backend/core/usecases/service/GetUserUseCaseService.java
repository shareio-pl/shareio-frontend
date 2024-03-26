package org.shareio.backend.core.usecases.service;

import lombok.AllArgsConstructor;
import org.shareio.backend.core.model.User;
import org.shareio.backend.core.model.UserSnapshot;
import org.shareio.backend.core.usecases.port.dto.GetUserDto;
import org.shareio.backend.core.usecases.port.in.GetUserUseCaseInterface;
import org.shareio.backend.core.usecases.port.out.GetUserQueryInterface;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GetUserUseCaseService implements GetUserUseCaseInterface {

    GetUserQueryInterface getUserQueryInterface;


    @Override
    public UserSnapshot getUserSnapshot(UUID id) {
        Optional<GetUserDto> getUserDto = getUserQueryInterface.getUserDto(id);
        if (getUserDto.isEmpty()) {
            throw new RuntimeException();
        }
        return getUserDto.map(User::fromDto).get().toSnapshot();
    }
}
