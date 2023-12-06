package at.fhv.master.laendleenergy.application.authentication;

import at.fhv.master.laendleenergy.view.DTOs.AuthRequest;
import at.fhv.master.laendleenergy.view.DTOs.AuthResponse;

public interface AuthenticationService {
    AuthResponse authenticate(AuthRequest authRequest) throws Exception;
}
