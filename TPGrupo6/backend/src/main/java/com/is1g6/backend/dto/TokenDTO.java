package com.is1g6.backend.dto;

import lombok.NonNull;

public record TokenDTO(
        @NonNull String accessToken,
        String userRole
) {
}
