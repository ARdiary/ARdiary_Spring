package com.army.ardiary.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
@Data
@Builder
public class EmailRequestDto {
    @Email
    String email;
}
