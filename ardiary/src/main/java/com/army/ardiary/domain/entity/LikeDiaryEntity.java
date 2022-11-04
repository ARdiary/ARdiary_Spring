package com.army.ardiary.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeDiaryEntity {
    int likeDiaryId;
    int userId;
    int diaryId;
}
