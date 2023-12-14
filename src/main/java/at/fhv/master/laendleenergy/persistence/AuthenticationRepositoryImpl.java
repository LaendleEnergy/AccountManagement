package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Token;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class AuthenticationRepositoryImpl implements AuthenticationRepository {
    @Inject
    EntityManager entityManager;
    @Override
    public void addToBlacklist(Token token) {
        entityManager.persist(token);
    }
}
