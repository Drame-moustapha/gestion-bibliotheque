package sn.smd.gestionbibliotheque.backend.payload;

//import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {

//    @NotBlank(message = "Token obligatoire")
    private String token;

//    @NotBlank(message = "Nouveau mot de passe requis")
    private String newPassword;
}