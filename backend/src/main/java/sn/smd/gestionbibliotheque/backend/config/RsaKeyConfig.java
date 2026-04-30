//package sn.smd.gestionbibliotheque.backend.config;
//
//import com.nimbusds.jose.jwk.*;
//import com.nimbusds.jose.jwk.source.*;
//import com.nimbusds.jose.proc.SecurityContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//import org.springframework.security.oauth2.jwt.*;
//
//import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
//import java.security.*;
//import java.security.interfaces.*;
//import java.security.spec.*;
//import java.util.Base64;
//
//@Configuration
//public class RsaKeyConfig {
//
//    private final RSAPublicKey publicKey;
//    private final RSAPrivateKey privateKey;
//
//    public RsaKeyConfig(Resource publicKeyPath,
//                        Resource privateKeyPath) throws Exception {
//
//        this.publicKey = loadPublicKey(publicKeyPath);
//        this.privateKey = loadPrivateKey(privateKeyPath);
//    }
//
//    // =========================
//    // LOAD PUBLIC KEY SAFE
//    // =========================
//    private RSAPublicKey loadPublicKey(Resource resource) throws Exception {
//
//        String key = readResource(resource);
//
//        key = key
//                .replace("-----BEGIN PUBLIC KEY-----", "")
//                .replace("-----END PUBLIC KEY-----", "")
//                .replaceAll("\\s+", "");
//
//        byte[] decoded = Base64.getDecoder().decode(key);
//
//        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
//        KeyFactory factory = KeyFactory.getInstance("RSA");
//
//        return (RSAPublicKey) factory.generatePublic(spec);
//    }
//
//    // =========================
//    // LOAD PRIVATE KEY SAFE
//    // =========================
//    private RSAPrivateKey loadPrivateKey(Resource resource) throws Exception {
//
//        String key = readResource(resource);
//
//        key = key
//                .replace("-----BEGIN PRIVATE KEY-----", "")
//                .replace("-----END PRIVATE KEY-----", "")
//                .replaceAll("\\s+", "");
//
//        byte[] decoded = Base64.getDecoder().decode(key);
//
//        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
//        KeyFactory factory = KeyFactory.getInstance("RSA");
//
//        return (RSAPrivateKey) factory.generatePrivate(spec);
//    }
//
//    // =========================
//    // SAFE RESOURCE READER (JAR SAFE)
//    // =========================
//    private String readResource(Resource resource) throws Exception {
//
//        try (InputStream is = resource.getInputStream()) {
//            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
//        }
//    }
//
//    // =========================
//    // JWT ENCODER
//    // =========================
//    @Bean
//    public JwtEncoder jwtEncoder() {
//
//        RSAKey rsaKey = new RSAKey.Builder(publicKey)
//                .privateKey(privateKey)
//                .keyID("smd-key")
//                .build();
//
//        JWKSource<SecurityContext> source =
//                new ImmutableJWKSet<>(new JWKSet(rsaKey));
//
//        return new NimbusJwtEncoder(source);
//    }
//
//    // =========================
//    // JWT DECODER
//    // =========================
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        return NimbusJwtDecoder
//                .withPublicKey(publicKey)
//                .build();
//    }
//}