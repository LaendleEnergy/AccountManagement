package at.fhv.master.laendleenergy.application.authentication;

import at.fhv.master.laendleenergy.view.DTOs.AuthRequest;
import at.fhv.master.laendleenergy.view.DTOs.AuthResponse;
import jakarta.ws.rs.core.SecurityContext;

public interface AuthenticationService {
    AuthResponse authenticate(AuthRequest authRequest) throws Exception;
    boolean verifiedCaller(SecurityContext ctx, String jwtName);
}
