package com.iron_fit.iron_fit_backend.core.utils;

import com.github.javafaker.Faker;

/**
 * Provider class for Faker to avoid Spring Boot DevTools classloader issues
 */
public class FakerProvider {

    public static Faker getFaker() {
        return new Faker();
    }
}
