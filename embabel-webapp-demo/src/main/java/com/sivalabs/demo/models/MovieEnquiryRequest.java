package com.sivalabs.demo.models;

import jakarta.validation.constraints.NotBlank;

public record MovieEnquiryRequest(@NotBlank String request) {
}
