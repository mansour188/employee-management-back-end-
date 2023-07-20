package com.example.employe.management.config;

import com.example.employe.management.Repo.EmployerRepository;
import com.example.employe.management.model.Role;
import com.example.employe.management.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@NoArgsConstructor
@AllArgsConstructor
@Component
public class JwtTokenUtil  {
    @Autowired
    EmployerRepository userRepo;
    public static final java.lang.String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


    public Boolean validateToken(final java.lang.String token) {
        try{
            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    public boolean isTokenValid(java.lang.String token, UserDetails userDetails) {
        java.lang.String username=getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    private boolean isTokenExpired(java.lang.String token) {
        Date expiration=getClaims(token).getExpiration();
        return expiration.before(new Date());


    }
    private Claims getClaims(java.lang.String token){
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            claims=null;
        }
        return claims;
    }




    public java.lang.String generateToken(java.lang.String userName) {


        Map<java.lang.String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private java.lang.String createToken(Map<java.lang.String, Object> claims, java.lang.String userName) {
        Users user=userRepo.findByEmail(userName);
        Role role=user.getRole();
        claims.put("role",role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public java.lang.String getUsername(java.lang.String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

}
