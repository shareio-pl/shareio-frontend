package org.shareio.backend.infrastructure.dbadapter;

import org.shareio.backend.core.usecases.port.dto.GetUserDto;
import org.shareio.backend.core.usecases.port.out.GetUserQueryInterface;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class UserAdapter implements GetUserQueryInterface {

    UserRepository userRepository;

    public UserAdapter(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<GetUserDto> getUserDto(UUID id) {
        Optional<UserEntity> userEntity = userRepository.findByUserId(id);
        if (userEntity.isEmpty()) {
            throw new RuntimeException();
        }
        return userEntity.map(UserMapper::toDto);
    }
}
