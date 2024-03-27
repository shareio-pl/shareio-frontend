package org.shareio.backend.infrastructure.dbadapter;

import org.shareio.backend.core.model.vo.UserId;
import org.shareio.backend.core.usecases.port.dto.UserProfileGetDto;

public class UserMapper {

    public static UserProfileGetDto toDto(UserEntity userEntity) {
        return new UserProfileGetDto(
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
