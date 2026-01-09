package com.andrewpg.springboottemplate.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

  @NotBlank(message = "El nombre de usuario es obligatorio")
  @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
  private String username;

  @NotBlank(message = "El email es obligatorio")
  @Size(max = 100)
  @Email(message = "El email debe ser válido")
  private String email;

  @NotBlank(message = "La contraseña es obligatoria")
  @Size(min = 6, max = 120, message = "La contraseña debe tener entre 6 y 120 caracteres")
  private String password;
}
