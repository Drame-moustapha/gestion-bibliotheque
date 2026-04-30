package sn.smd.gestionbibliotheque.backend.service.Impl;

import lombok.RequiredArgsConstructor;
//import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import sn.smd.gestionbibliotheque.backend.entity.Utilisateur;
import sn.smd.gestionbibliotheque.backend.exceptions.NotFoundExceptions;
import sn.smd.gestionbibliotheque.backend.repository.UtilisateurRepository;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UtilisateurRepository utilisateurRepository;
//	private final JwtEncoder jwtEncoder;
//	private final JwtDecoder jwtDecoder;

//	public Map<String, String> generateToken(String username) {
//
//		Utilisateur user = utilisateurRepository.findByUsername(username)
//				.orElseThrow(() -> new NotFoundExceptions("Utilisateur introuvable"));
//
//		List<String> roles = user.getRoles()
//				.stream()
//				.map(role -> role.getName())
//				.toList();
//
//		Instant now = Instant.now();
//
//		JwtClaimsSet claims = JwtClaimsSet.builder()
//				.issuer("gestion-bibliotheque-api")
//				.issuedAt(now)
//				.expiresAt(now.plusSeconds(86400)) // 24h
//				.subject(user.getUsername())
//				.claim("roles", roles)
//				.claim("userId", user.getId())
//				.build();
//
//		String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
//
//		return Map.of(
//				"access_token", token,
//				"type", "Bearer",
//				"expires_in", "86400"
//		);
//	}

//	public boolean validateToken(String token) {
//		try {
//			jwtDecoder.decode(token);
//			return true;
//		} catch (JwtException e) {
//			return false;
//		}
//	}
}