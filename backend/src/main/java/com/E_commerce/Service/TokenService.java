package com.E_commerce.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.E_commerce.Exception.AppException;
import com.E_commerce.Exception.ErrorCode;
import com.E_commerce.Repository.InvalidTokenRepository;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;

@Service
public class TokenService {
    @Value("${jwt.signerkey}")
    private String SECRET_KEY;
    @Autowired
    private InvalidTokenRepository invalidTokenRepository;
    public SignedJWT verifyToken(String token) {
        try {
            JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            if (!signedJWT.verify(verifier) || expiryTime.before(new Date())) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }
            if (invalidTokenRepository.existsById(
                    signedJWT.getJWTClaimsSet().getJWTID())) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }
            return signedJWT;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
    }
    
}