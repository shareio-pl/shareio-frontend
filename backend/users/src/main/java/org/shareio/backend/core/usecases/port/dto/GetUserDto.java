package org.shareio.backend.core.usecases.port.dto;

import lombok.Getter;
import org.shareio.backend.core.model.vo.UserId;

import java.time.LocalDateTime;

@Getter
public record GetUserDto(
        UserId userId,
        String email,
        String name,
        LocalDateTime dateOfBirth,
        String country,
        String city,
        LocalDateTime lastLoginDate) {
}
