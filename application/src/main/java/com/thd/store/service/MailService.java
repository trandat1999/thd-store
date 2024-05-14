package com.thd.store.service;

import com.thd.store.entity.EmailNotification;
import com.thd.store.entity.InvoiceImport;
import com.thd.store.entity.User;

import java.util.Map;

/**
 * @author DatNuclear 19/01/2024
 * @project store-movie
 */
public interface MailService {
    void sendMail(EmailNotification notificationEmail, Map<String, Object> attributes);
    void sendMailActive(User user);
    void sendMailForgotPassword(User user);
    void sendMailOrderInvoiceImport(InvoiceImport invoiceImport);
}
