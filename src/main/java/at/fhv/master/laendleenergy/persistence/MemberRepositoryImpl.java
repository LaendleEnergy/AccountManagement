package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.MemberNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.*;

@ApplicationScoped
public class MemberRepositoryImpl implements MemberRepository {

    @Inject
    EntityManager entityManager;

    @Override
    public void addHouseholdMember(Member member) {
        entityManager.persist(member);
    }

    @Override
    public void removeHouseholdMember(String memberId) throws MemberNotFoundException {
        Member toRemove = entityManager.find(Member.class, memberId);
        if (toRemove == null) throw new MemberNotFoundException();

        entityManager.remove(toRemove);
    }

    @Override
    public List<Member> getAllMembersOfHousehold(String householdId) throws HouseholdNotFoundException {
        String jpqlQuery = "SELECT m FROM Member m WHERE m.household.id =: householdId";

        return entityManager.createQuery(jpqlQuery, Member.class)
                .setParameter("householdId", householdId)
                .getResultList();
    }

    @Override
    public Member getMemberById(String memberId) throws MemberNotFoundException {
        Member member = entityManager.find(Member.class, memberId);
        if(member == null) throw new MemberNotFoundException();

        return  member;
    }

    @Override
    public void updateMember(Member member) throws MemberNotFoundException {
        Member toUpdate = entityManager.find(Member.class, member.getId());
        if (toUpdate == null) throw new MemberNotFoundException();

        entityManager.merge(member);
    }
}
