package com.is1g6.backend.dto;

import com.is1g6.backend.model.UserCredentials;
import lombok.NonNull;

public record UserLoginDTO(
        @NonNull String username,
        @NonNull String password
) implements UserCredentials {}
