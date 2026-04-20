package sn.smd.gestionbibliotheque.backend.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Username obligatoire")
    private String username;

    @NotBlank(message = "Password obligatoire")
    private String password;
}