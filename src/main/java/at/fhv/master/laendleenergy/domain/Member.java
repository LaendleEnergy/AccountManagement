package at.fhv.master.laendleenergy.domain;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class Member {

    private String id;
    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;

    public Member() {
        this.id =  UUID.randomUUID().toString();
    }

    public Member(String name, Optional<LocalDate> dateOfBirth, Optional<Gender> gender) {
        this.id =  UUID.randomUUID().toString();
        this.name = name;
        this.dateOfBirth = dateOfBirth.orElse(null);
        this.gender = gender.orElse(null);
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
}
