package com.iron_fit.iron_fit_backend.features.auth.domain.dto.login;

public record LoginRequestDto(
        String username,
        String password
) {
}
