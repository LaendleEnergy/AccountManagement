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
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class AuthenticationServiceImpl implements AuthenticationService {
    @ConfigProperty(name = "com.ard333.quarkusjwt.jwt.duration") public Long duration;
    @ConfigProperty(name = "mp.jwt.verify.issuer") public String issuer;

    @Inject
    UserService userService;
    @Inject
    PBKDF2Encoder passwordEncoder;
    @Inject
    UserRepository userRepository;

    private boolean passwordCorrect(AuthRequest authRequest, UserDTO user) {
        return user.getPassword().equals(passwordEncoder.encode(authRequest.getPassword()));
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) throws Exception {
        UserDTO u = userService.getUserByEmail(authRequest.getEmailAddress());
        if (u != null) {
            return new AuthResponse(TokenUtils.generateToken(u.getEmailAddress(), Role.get(u.getRole()), duration, issuer));
        } else {
            throw new EmailNotFoundException();
        }
    }

    @Override
    public LoginDTO login(AuthRequest authRequest) throws Exception {
        User u = userRepository.getUserByEmail(authRequest.getEmailAddress());
        if (u != null) {
            return new LoginDTO(TokenUtils.generateToken(u.getEmailAddress(), Role.get(u.getRole().getName()), duration, issuer), u.getHousehold().getDeviceId());
        } else {
            throw new EmailNotFoundException();
        }
    }
}

