package com.example.memberservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;

@Builder
public record Member(
        @Id
        Long id,

        @NotBlank(message = "name must be defined")
        String name,

        @Column("phone_number")
        @NotBlank(message = "phone number must be defined")
        @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$",
                message = "phone number must be valid.")
        String phoneNumber,

        @Version
        int version
) {
}
