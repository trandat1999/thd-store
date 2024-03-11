package com.thd.store.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author DatNuclear 16/01/2024
 * @project store-movie
 */
@Data
public class AuthRequest {
   @NotNull(message = "{store.validation.NotNull}")
   @NotBlank(message = "{store.validation.NotBlank}")
   private String username;
   @NotNull(message = "{store.validation.NotNull}")
   @NotBlank(message = "{store.validation.NotBlank}")
   private String password;
}
