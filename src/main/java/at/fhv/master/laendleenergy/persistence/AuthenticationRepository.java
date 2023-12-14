package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Token;

public interface AuthenticationRepository {
    void addToBlacklist(Token token);
}
