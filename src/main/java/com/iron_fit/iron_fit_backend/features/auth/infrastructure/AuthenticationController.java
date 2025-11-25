package com.iron_fit.iron_fit_backend.features.auth.infrastructure;

import com.iron_fit.iron_fit_backend.features.auth.application.services.CustomUserDetailsService;
import com.iron_fit.iron_fit_backend.features.auth.domain.dto.login.LoginRequestDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthenticationController {

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {

        HashMap<String, Object> result = userDetailsService.loginUser(loginRequestDto);

        if (result.containsKey("error")) {
            Cookie cookie = new Cookie("jwt", "");
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setDomain("localhost");
            cookie.setMaxAge(0);
            cookie.setAttribute("SameSite", "Lax");
            response.addCookie(cookie);

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }

        long defaultExpiration = 60 * 60 * 1000L; // 1 hour default
        String token = result.get("token").toString();

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // ⚠️ usa 'true' solo si estás en HTTPS, sino no se envía en localhost
        cookie.setPath("/"); // permite que se envíe en todas las rutas
        cookie.setDomain("localhost"); // Permite compartir la cookie entre puertos en localhost
        cookie.setMaxAge((int) (defaultExpiration / 1000));

        // 💡 A partir de Java 11 puedes establecer SameSite manualmente:
        cookie.setAttribute("SameSite", "Lax"); // O "Lax" si estás sin HTTPS
        response.addCookie(cookie);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletResponse response) {
        // limpiar sesión: borrar cookie jwt
        Cookie cookie = new Cookie("jwt", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setMaxAge(0);
        cookie.setAttribute("SameSite", "Lax");
        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
}
