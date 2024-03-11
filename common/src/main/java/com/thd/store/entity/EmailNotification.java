package com.thd.store.entity;

import com.thd.store.type.TypeEmail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author DatNuclear 19/01/2024
 * @project store-movie
 */
@Entity
@Table(name = "tbl_email_notification")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotification extends BaseEntity{
    @Column(name = "email")
    private String email;
    @Column(name = "subject", length = 500)
    private String subject;
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeEmail type;
    @Column(name = "link")
    private String link;
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;
}
