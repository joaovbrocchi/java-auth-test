package com.joaovbrocchi.authTest.entity.user;

public record RegisterDTO(UserRole role, String email, String password) {
}
