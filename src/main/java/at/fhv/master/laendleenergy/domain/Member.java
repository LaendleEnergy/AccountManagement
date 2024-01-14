package at.fhv.master.laendleenergy.domain;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name = "household_member")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Member {
    @Id
    @Column(name = "household_member_id")
    private String id;
    @Column(name = "member_name")
    private String name;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "household_id")
    private String householdId;
    @Column(name = "device_id")
    private String deviceId;

    public Member() {

    }

    public Member(String name, Optional<LocalDate> dateOfBirth, Optional<Gender> gender, String householdId, String deviceId) {
        this.id =  UUID.randomUUID().toString();
        this.name = name;
        this.dateOfBirth = dateOfBirth.orElse(null);
        this.gender = gender.orElse(null);
        this.householdId = householdId;
        this.deviceId = deviceId;
    }

    public Member(String id, String name, Optional<LocalDate> dateOfBirth, Optional<Gender> gender, String householdId, String deviceId) {
        this.id =  id;
        this.name = name;
        this.dateOfBirth = dateOfBirth.orElse(null);
        this.gender = gender.orElse(null);
        this.householdId = householdId;
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
