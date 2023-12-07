package at.fhv.master.laendleenergy.application.authentication;

import at.fhv.master.laendleenergy.domain.Role;
import at.fhv.master.laendleenergy.domain.User;
import at.fhv.master.laendleenergy.domain.exceptions.UserNotFoundException;
import at.fhv.master.laendleenergy.persistence.UserRepository;
import at.fhv.master.laendleenergy.view.DTOs.AuthRequest;
import at.fhv.master.laendleenergy.view.DTOs.AuthResponse;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.security.Principal;

@ApplicationScoped
public class AuthenticationServiceImpl implements AuthenticationService {
    @ConfigProperty(name = "com.ard333.quarkusjwt.jwt.duration")
    Long duration;
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @Inject
    PBKDF2Encoder passwordEncoder;
    @Inject
    UserRepository userRepository;

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) throws Exception {
        try {
            User u = userRepository.getUserByEmail(authRequest.getEmailAddress());

            if (u.getPassword().equals(passwordEncoder.encode(authRequest.getPassword()))) {
                try {
                    return new AuthResponse(TokenUtils.generateToken(u.getEmailAddress(), u.getRole().getName(), u.getId(), u.getHouseholdId(), duration, issuer));
                } catch (Exception e) {
                    throw new UnauthorizedException();
                }
            }
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        }
        throw new UnauthorizedException();
    }

    public boolean verifiedCaller(String caller, String jwtName) {
        String name = caller == null ? "anonymous" : caller;

        return name.equals(jwtName);
    }
}

