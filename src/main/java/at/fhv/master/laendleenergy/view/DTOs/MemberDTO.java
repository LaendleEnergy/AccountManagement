package at.fhv.master.laendleenergy.view.DTOs;

import at.fhv.master.laendleenergy.domain.Gender;
import at.fhv.master.laendleenergy.domain.Member;

import java.time.LocalDate;
import java.util.Optional;

public class MemberDTO {
    private String memberId;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String householdId;


    public MemberDTO() {

    }

    public MemberDTO(String memberId, String name, String dateOfBirth, String gender, String householdId) {
        this.memberId = memberId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.householdId = householdId;
    }

    public static MemberDTO create(Member member) {
        return new MemberDTO(member.getId(), member.getName(), member.getDateOfBirth().toString(), member.getGender().getName(), member.getHouseholdId());
    }


    public static Member create(MemberDTO memberDTO) {
        return new Member(
                memberDTO.getMemberId(),
                memberDTO.getName(),
                Optional.ofNullable(memberDTO.getDateOfBirth()).isPresent() ? Optional.of(LocalDate.parse(memberDTO.getDateOfBirth())) : Optional.empty(),
                Optional.ofNullable(memberDTO.getGender()).isPresent() ? Optional.of(Gender.get(memberDTO.getGender())) : Optional.empty(),
                memberDTO.getHouseholdId()
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
