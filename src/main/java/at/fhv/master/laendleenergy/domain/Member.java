package at.fhv.master.laendleenergy.domain;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class Member {
    private String id;
    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String householdId;

    public Member() {
        this.id =  UUID.randomUUID().toString();
    }

    public Member(String name, Optional<LocalDate> dateOfBirth, Optional<Gender> gender, String householdId) {
        this.id =  UUID.randomUUID().toString();
        this.name = name;
        this.dateOfBirth = dateOfBirth.orElse(null);
        this.gender = gender.orElse(null);
        this.householdId = householdId;
    }

    public Member(String id, String name, Optional<LocalDate> dateOfBirth, Optional<Gender> gender, String householdId) {
        this.id =  id;
        this.name = name;
        this.dateOfBirth = dateOfBirth.orElse(null);
        this.gender = gender.orElse(null);
        this.householdId = householdId;
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

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                '}';
    }
}
