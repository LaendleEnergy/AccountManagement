package at.fhv.master.laendleenergy.view.DTOs;

import at.fhv.master.laendleenergy.domain.Gender;
import at.fhv.master.laendleenergy.domain.Member;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class MemberDTO {
    private String name;
    private String dateOfBirth;
    private String gender;


    public MemberDTO() {

    }

    public MemberDTO(String name, String dateOfBirth, String gender) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public static MemberDTO create(Member member) {
        return new MemberDTO(member.getName(), member.getDateOfBirth().toString(), member.getGender().getName());
    }


    public static Member create(MemberDTO memberDTO, String householdId) {
        return new Member(
                memberDTO.getName(),
                !Objects.equals(memberDTO.getDateOfBirth(), "") ? Optional.of(LocalDate.parse(memberDTO.getDateOfBirth())) : Optional.empty(),
                !Objects.equals(memberDTO.getGender(), "") ? Optional.of(Gender.get(memberDTO.getGender())) : Optional.empty(),
                householdId
        );
    }

    public static Member create(MemberDTO memberDTO, String memberId, String householdId) {
        return new Member(
                memberId,
                memberDTO.getName(),
                !Objects.equals(memberDTO.getDateOfBirth(), "") ? Optional.of(LocalDate.parse(memberDTO.getDateOfBirth())) : Optional.empty(),
                !Objects.equals(memberDTO.getGender(), "") ? Optional.of(Gender.get(memberDTO.getGender())) : Optional.empty(),               householdId
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

}
