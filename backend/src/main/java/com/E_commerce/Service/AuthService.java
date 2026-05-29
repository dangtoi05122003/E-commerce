package com.E_commerce.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.E_commerce.Entity.InvalidToken;
import com.E_commerce.Entity.UserEntity;
import com.E_commerce.Enum.StatusUser;
import com.E_commerce.Exception.AppException;
import com.E_commerce.Exception.ErrorCode;
import com.E_commerce.Repository.InvalidTokenRepository;
import com.E_commerce.Repository.UserRepository;
import com.E_commerce.dto.Request.AuthRequest;
import com.E_commerce.dto.Request.LogoutRequest;
import com.E_commerce.dto.Response.AuthResponse;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InvalidTokenRepository invalidTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;
    @Value("${jwt.signerkey}")
    private String SECRET_KEY;
    public AuthResponse login(AuthRequest request) {
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (user.getStatus() != StatusUser.ACTIVE) {
            throw new AppException(ErrorCode.USER_NOT_ACTIVE);
        }
        boolean auth = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!auth) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return new AuthResponse(generateToken(user));
    }
    public String generateToken(UserEntity user) {
        try {
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
            JWSSigner signer = new MACSigner(SECRET_KEY.getBytes());
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId().toString())
                .expirationTime(Date.from(Instant.now().plus(12, ChronoUnit.HOURS)))
                .claim("role", user.getRole())
                .claim("username", user.getUsername())
                .jwtID(UUID.randomUUID().toString())
                .build();
            SignedJWT signedJWT = new SignedJWT(header, claimsSet);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (Exception e) {
            throw new AppException(ErrorCode.TOKEN_GENERATION_FAILED);
        }
    }
    public void logout(LogoutRequest request) {
        try {
            var signedJWT = tokenService.verifyToken(request.getToken());
            String jti = signedJWT.getJWTClaimsSet().getJWTID();
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            if (!invalidTokenRepository.existsById(jti)) {
                InvalidToken invalidToken = InvalidToken.builder().id(jti).expiryTime(expiryTime).build();
                invalidTokenRepository.save(invalidToken);
            }
        } catch (Exception e) {
            throw new AppException(ErrorCode.LOGOUT_ERROR);
        }
    }
}
