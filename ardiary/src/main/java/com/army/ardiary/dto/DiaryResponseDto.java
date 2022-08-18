package com.army.ardiary.dto;

import com.army.ardiary.domain.entity.DiaryEntity;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class DiaryResponseDto {
    ArrayList<DiaryEntity> diaryList;
}
