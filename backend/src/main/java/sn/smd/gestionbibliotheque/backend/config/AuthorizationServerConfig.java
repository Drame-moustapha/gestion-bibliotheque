//package sn.smd.gestionbibliotheque.backend.config;
//
//import com.nimbusds.jose.jwk.*;
//import com.nimbusds.jose.jwk.source.*;
//import com.nimbusds.jose.proc.SecurityContext;
//import org.springframework.context.annotation.*;
//import org.springframework.core.Ordered;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.*;
//import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
//import org.springframework.security.web.SecurityFilterChain;
//
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.util.UUID;
//
//@Configuration
//public class AuthorizationServerConfig {
//
//	private final PasswordEncoder passwordEncoder;
//
//	public AuthorizationServerConfig(PasswordEncoder passwordEncoder) {
//		this.passwordEncoder = passwordEncoder;
//	}
//
//	// =========================
//	// SECURITY FILTER CHAIN
//	// =========================
//	@Bean
//	public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
//
//		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
//
//		return http
//				.formLogin(Customizer.withDefaults())
//				.build();
//	}
//
//	// =========================
//	// JWT KEYS (RSA)
//	// =========================
//	@Bean
//	public JWKSource<SecurityContext> jwkSource() {
//		RSAKey rsaKey = generateRsaKey();
//		JWKSet jwkSet = new JWKSet(rsaKey);
//
//		return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
//	}
//
//	private RSAKey generateRsaKey() {
//		KeyPair keyPair = generateKeyPair();
//
//		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//
//		return new RSAKey.Builder(publicKey)
//				.privateKey(privateKey)
//				.keyID(UUID.randomUUID().toString())
//				.build();
//	}
//
//	private KeyPair generateKeyPair() {
//		try {
//			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
//			generator.initialize(2048);
//			return generator.generateKeyPair();
//		} catch (Exception e) {
//			throw new IllegalStateException("Erreur génération clé RSA", e);
//		}
//	}
//
//	// =========================
//	// ISSUER CONFIG
//	// =========================
//	@Bean
//	public AuthorizationServerSettings authorizationServerSettings() {
//		return AuthorizationServerSettings.builder()
//				.issuer("http://localhost:8080")
//				.build();
//	}
//}