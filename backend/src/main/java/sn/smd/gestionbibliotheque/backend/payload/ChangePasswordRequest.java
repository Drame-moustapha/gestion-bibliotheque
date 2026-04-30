package sn.smd.gestionbibliotheque.backend.payload;

//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
//
//    @NotNull(message = "User ID obligatoire")
    private Long userId;

//    @NotBlank(message = "Ancien mot de passe requis")
    private String oldPassword;

//    @NotBlank(message = "Nouveau mot de passe requis")
//    @Size(min = 8, message = "Minimum 8 caractères")
    private String newPassword;
}