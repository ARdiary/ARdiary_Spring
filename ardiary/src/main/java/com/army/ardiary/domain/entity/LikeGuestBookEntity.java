package com.army.ardiary.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeGuestBookEntity {
    int likeGuestBookId;
    int userId;
    int guestBookId;
}
