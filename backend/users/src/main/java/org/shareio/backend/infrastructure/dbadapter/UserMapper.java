package org.shareio.backend.infrastructure.dbadapter;

import org.shareio.backend.core.model.vo.UserId;
import org.shareio.backend.core.usecases.port.dto.GetUserDto;

public class UserMapper {

    public static GetUserDto toDto(UserEntity userEntity) {
        return new GetUserDto(
                new UserId(userEntity.getUserId()),
                userEntity.getEmail(),
                userEntity.getName(),
                userEntity.getDateOfBirth(),
                userEntity.getAddress().getCountry(),
                userEntity.getAddress().getCity(),
                userEntity.getSecurity().getLastLoginDate()
        );
    }
}
