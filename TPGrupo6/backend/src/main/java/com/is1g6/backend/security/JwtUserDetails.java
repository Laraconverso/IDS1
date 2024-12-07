package com.is1g6.backend.security;

public record JwtUserDetails (
        String username,
        String role
) {}