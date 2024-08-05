package com.thd.store.dto.customer;

import com.thd.store.entity.File;
import com.thd.store.entity.Person;
import com.thd.store.entity.User;
import com.thd.store.type.Gender;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Datnuclear 30/07/2024 11:57
 * @project thd-store
 * @package com.thd.store.dto.customer
 **/
@Data
@NoArgsConstructor
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String displayName;
    private Date birthDate;
    private Gender gender;
    private String phoneNumber;
    private String idNumber;
    private String idNumberIssueBy;
    private Date idNumberIssueDate;
    private String email;

    public CustomerDto(Person entity) {
        if (entity == null) {
            return;
        }
        this.id = entity.getId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.displayName = entity.getDisplayName();
        this.birthDate = entity.getBirthDate();
        this.gender = entity.getGender();
        this.phoneNumber = entity.getPhoneNumber();
        this.idNumber = entity.getIdNumber();
        this.idNumberIssueBy = entity.getIdNumberIssueBy();
        this.idNumberIssueDate = entity.getIdNumberIssueDate();
        this.email = entity.getEmail();
    }
}
