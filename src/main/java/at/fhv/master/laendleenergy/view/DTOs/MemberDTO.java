package at.fhv.master.laendleenergy.view.DTOs;

import at.fhv.master.laendleenergy.domain.Gender;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Member;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class MemberDTO {
    private String id;
    private String name;
    private String dateOfBirth;
    private String gender;

    public MemberDTO() {
        id = UUID.randomUUID().toString();
    }

    public MemberDTO(String id, String name, String dateOfBirth, String gender) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public static MemberDTO create(Member member) {
        return new MemberDTO(
                member.getId(),
                member.getName(),
                member.getDateOfBirth() != null ? member.getDateOfBirth().toString() : "",
                member.getGender() != null ? member.getGender().getName() : "");
    }


    public static Member create(MemberDTO memberDTO, Household household) {
        return new Member(
                memberDTO.getId(),
                memberDTO.getName(),
                Optional.ofNullable(memberDTO.getDateOfBirth()).isPresent() ? Optional.of(LocalDate.parse(memberDTO.getDateOfBirth())) : Optional.empty(),
                Optional.ofNullable(memberDTO.getGender()).isPresent() ? Optional.of(Gender.get(memberDTO.getGender())) : Optional.empty(),
                household.getId(),
                household.getDeviceId()
        );
    }

    public static Member create(String memberId, MemberDTO memberDTO, Household household) {
        return new Member(
                memberId,
                memberDTO.getName(),
                Optional.ofNullable(memberDTO.getDateOfBirth()).isPresent() ? Optional.of(LocalDate.parse(memberDTO.getDateOfBirth())) : Optional.empty(),
                Optional.ofNullable(memberDTO.getGender()).isPresent() ? Optional.of(Gender.get(memberDTO.getGender())) : Optional.empty(),
                household.getId(),
                household.getDeviceId()
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
