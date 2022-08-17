package com.army.ardiary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequestDto {
    @Email
    String email;
}
