package com.thd.store.entity;

import com.thd.store.type.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "tbl_person")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Person extends BaseEntity {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "id_number")
    private String idNumber;
    @Column(name = "id_number_issue_by")
    private String idNumberIssueBy;
    @Column(name = "id_number_issue_date")
    private Date idNumberIssueDate;
    @Column(name = "email")
    private String email;
    @ManyToOne
    @JoinColumn(name = "photo_id")
    private File photoFile;
    @OneToOne(mappedBy = "person")
    private User user;

}
