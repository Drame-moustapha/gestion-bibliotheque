package sn.smd.gestionbibliotheque.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.smd.gestionbibliotheque.backend.payload.LoginRequest;
import sn.smd.gestionbibliotheque.backend.service.Impl.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    // =========================
    // LOGIN (PRODUCTION ENDPOINT)
    // =========================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        log.info("Login attempt: {}", request.getUsername());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            String username = authentication.getName();

            Map<String, String> token = authService.generateToken(username);

            log.info("Login success: {}", username);

            return ResponseEntity.ok(token);

        } catch (BadCredentialsException e) {

            log.warn("Bad credentials for user: {}", request.getUsername());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Identifiants incorrects");

        } catch (DisabledException e) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Compte désactivé");

        } catch (LockedException e) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Compte verrouillé");

        } catch (Exception e) {

            log.error("Login error", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur serveur");
        }
    }
}