package at.fhv.master.laendleenergy.view.DTOs;

import at.fhv.master.laendleenergy.domain.Gender;
import at.fhv.master.laendleenergy.domain.Member;

import java.time.LocalDate;
import java.util.Optional;

public class MemberDTO {
    private String name;
    private String dateOfBirth;
    private String gender;
    private String deviceId;

    public MemberDTO() {

    }

    public MemberDTO(String name, String dateOfBirth, String gender, String deviceId) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.deviceId = deviceId;
    }

    public static MemberDTO create(Member member) {
        return new MemberDTO(member.getName(), member.getDateOfBirth().toString(), member.getGender().getName(), member.getDeviceId());
    }


    public static Member create(MemberDTO memberDTO) {
        return new Member(
                memberDTO.getName(),
                Optional.ofNullable(memberDTO.getDateOfBirth()).isPresent() ? Optional.of(LocalDate.parse(memberDTO.getDateOfBirth())) : Optional.empty(),
                Optional.ofNullable(memberDTO.getGender()).isPresent() ? Optional.of(Gender.get(memberDTO.getGender())) : Optional.empty(),
                memberDTO.getDeviceId()
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
