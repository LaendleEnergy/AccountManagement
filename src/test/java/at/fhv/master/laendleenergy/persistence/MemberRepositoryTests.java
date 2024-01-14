package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.MemberNotFoundException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.wildfly.common.Assert.assertNotNull;

@QuarkusTest
@TestTransaction
public class MemberRepositoryTests {

    @Inject
    MemberRepository memberRepository;
    @InjectMock
    EntityManager entityManager;

    static Member member;
    static final String memberId = "memberId1";
    static Household household;
    static final String householdId = "householdId1";

    @BeforeEach
    void setUp() {
        household = new Household(householdId, "d1", ElectricityPricingPlan.DAYNIGHT, List.of(new Member()));
        member = new Member(memberId, "Testperson", Optional.of(LocalDate.of(1980, 1, 1)), Optional.of(Gender.FEMALE), household.getId(), household.getDeviceId());
    }

    @Test
    public void getMemberById() throws MemberNotFoundException {
        Mockito.when(entityManager.find(Member.class, memberId)).thenReturn(member);

        Member retrievedMember = memberRepository.getMemberById(memberId);
        assertEquals(retrievedMember, member);
    }

    @Test
    public void getMemberById_MemberDoesNotExist() {
        assertThrows(MemberNotFoundException.class, () -> memberRepository.getMemberById(memberId));
    }
    @Test
    public void addHouseholdTest() {
        memberRepository.addHouseholdMember(member);

        Mockito.verify(entityManager, times(1)).persist(any());
    }

    @Test
    public void removeHouseholdMemberTest() throws MemberNotFoundException {
        Mockito.when(entityManager.find(Member.class, memberId)).thenReturn(member);

        memberRepository.removeHouseholdMember(memberId);

        Mockito.verify(entityManager, times(1)).remove(member);
    }

    @Test
    public void removeHouseholdMemberTestException() {
        assertThrows(MemberNotFoundException.class, () -> memberRepository.removeHouseholdMember(memberId));
        Mockito.verify(entityManager, times(0)).remove(member);
    }

    @Test
    public void getAllMembersOfHouseholdTest() throws HouseholdNotFoundException {
        Query<Member> queryMock = Mockito.mock(Query.class);

        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(Member.class))).thenReturn(queryMock);
        Mockito.when(queryMock.setParameter(Mockito.anyString(), Mockito.any())).thenReturn(queryMock);
        Mockito.when(queryMock.getResultList()).thenReturn(household.getMembers());

        List<Member> actualMembers = memberRepository.getAllMembersOfHousehold(householdId);

        assertNotNull(actualMembers);
    }

    @Test
    public void getAllMembersOfHouseholdTestException() {
        Query<Member> queryMock = Mockito.mock(Query.class);

        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(Member.class))).thenReturn(queryMock);
        Mockito.when(queryMock.setParameter(Mockito.anyString(), Mockito.any())).thenReturn(queryMock);
        Mockito.when(queryMock.getResultList()).thenReturn(new LinkedList<>());

        assertThrows(HouseholdNotFoundException.class, () -> memberRepository.getAllMembersOfHousehold(householdId));
    }
}
