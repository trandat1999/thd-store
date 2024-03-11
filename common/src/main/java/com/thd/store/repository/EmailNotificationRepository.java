package com.thd.store.repository;

import com.thd.store.entity.EmailNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author DatNuclear 19/01/2024
 * @project store-movie
 */
@Repository
public interface EmailNotificationRepository extends JpaRepository<EmailNotification,Long>  {
}
