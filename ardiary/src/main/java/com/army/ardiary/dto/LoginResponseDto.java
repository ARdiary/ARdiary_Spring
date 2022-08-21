package com.army.ardiary.dto;

import com.army.ardiary.domain.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    String accessToken;
    String refreshToken;
}
