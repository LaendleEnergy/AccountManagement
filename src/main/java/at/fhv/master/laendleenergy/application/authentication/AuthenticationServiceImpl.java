package at.fhv.master.laendleenergy.application.authentication;

import at.fhv.master.laendleenergy.domain.Role;
import at.fhv.master.laendleenergy.domain.User;
import at.fhv.master.laendleenergy.domain.exceptions.UserNotFoundException;
import at.fhv.master.laendleenergy.persistence.UserRepository;
import at.fhv.master.laendleenergy.view.DTOs.AuthRequest;
import at.fhv.master.laendleenergy.view.DTOs.AuthResponse;
import at.fhv.master.laendleenergy.view.DTOs.LoginDTO;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

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
        User u = userRepository.getUserByEmail(authRequest.getEmailAddress());

        if (u != null && u.getPassword().equals(passwordEncoder.encode(authRequest.getPassword()))) {
            return new AuthResponse(TokenUtils.generateToken(u.getEmailAddress(), Role.get(u.getRole().getName()), duration, issuer), u.getId());
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public LoginDTO login(AuthRequest authRequest) throws Exception {
        try {
            User u = userRepository.getUserByEmail(authRequest.getEmailAddress());

            if (u.getPassword().equals(passwordEncoder.encode(authRequest.getPassword()))) {
                try {
                    return new LoginDTO(TokenUtils.generateToken(u.getEmailAddress(), Role.get(u.getRole().getName()), duration, issuer), u.getDeviceId(), u.getId());
                } catch (Exception e) {
                    throw new UnauthorizedException();
                }
            }
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        }
        throw new UnauthorizedException();
    }
}

