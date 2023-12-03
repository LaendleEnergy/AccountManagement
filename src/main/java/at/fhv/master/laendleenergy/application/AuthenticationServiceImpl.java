package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.authentication.PBKDF2Encoder;
import at.fhv.master.laendleenergy.authentication.TokenUtils;
import at.fhv.master.laendleenergy.domain.Role;
import at.fhv.master.laendleenergy.domain.User;
import at.fhv.master.laendleenergy.domain.exceptions.EmailNotFoundException;
import at.fhv.master.laendleenergy.persistence.UserRepository;
import at.fhv.master.laendleenergy.view.DTOs.AuthRequest;
import at.fhv.master.laendleenergy.view.DTOs.AuthResponse;
import at.fhv.master.laendleenergy.view.DTOs.LoginDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class AuthenticationServiceImpl implements AuthenticationService {
    @ConfigProperty(name = "com.ard333.quarkusjwt.jwt.duration")
    public Long duration;
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    public String issuer;

    @Inject
    UserService userService;
    @Inject
    PBKDF2Encoder passwordEncoder;
    @Inject
    UserRepository userRepository;

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) throws Exception {
        UserDTO u = userService.getUserByEmail(authRequest.getEmailAddress());

        if (u != null && u.getPassword().equals(passwordEncoder.encode(authRequest.getPassword()))) {
            return new AuthResponse(TokenUtils.generateToken(u.getEmailAddress(), Role.get(u.getRole()), duration, issuer));
        } else {
            throw new EmailNotFoundException();
        }
    }

    @Override
    public LoginDTO login(AuthRequest authRequest) throws Exception {
        try {
            User u = userRepository.getUserByEmail(authRequest.getEmailAddress());

            if (u.getPassword().equals(passwordEncoder.encode(authRequest.getPassword()))) {
                try {
                    return new LoginDTO(TokenUtils.generateToken(u.getEmailAddress(), Role.get(u.getRole().getName()), duration, issuer), u.getDeviceId());
                } catch (Exception e) {
                    throw new UnauthorizedException();
                }
            }
        } catch (EmailNotFoundException e) {
            throw new EmailNotFoundException();
        }
        throw new UnauthorizedException();
    }
}

