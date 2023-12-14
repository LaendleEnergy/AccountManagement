package at.fhv.master.laendleenergy.view.DTOs;

import at.fhv.master.laendleenergy.domain.Gender;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Member;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class MemberDTO {
    private String name;
    private String dateOfBirth;
    private String gender;
    private String id;


    public MemberDTO() {

    }

    public MemberDTO(String name, String dateOfBirth, String gender, String id) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.id  = id;
    }

    public static MemberDTO create(Member member) {
        return new MemberDTO(member.getName(), member.getDateOfBirth().toString(), member.getGender().getName(), member.getId());
    }


    public static Member create(MemberDTO memberDTO, Household household) {
        return new Member(
                memberDTO.getName(),
                Optional.ofNullable(memberDTO.getDateOfBirth()).isPresent() ? Optional.of(LocalDate.parse(memberDTO.getDateOfBirth())) : Optional.empty(),
                Optional.ofNullable(memberDTO.getGender()).isPresent() ? Optional.of(Gender.get(memberDTO.getGender())) : Optional.empty(),
                household
        );
    }

    public static Member create(MemberDTO memberDTO, String memberId, Household household) {
        return new Member(
                memberId,
                memberDTO.getName(),
                Optional.ofNullable(memberDTO.getDateOfBirth()).isPresent() ? Optional.of(LocalDate.parse(memberDTO.getDateOfBirth())) : Optional.empty(),
                Optional.ofNullable(memberDTO.getGender()).isPresent() ? Optional.of(Gender.get(memberDTO.getGender())) : Optional.empty(),
                household
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
