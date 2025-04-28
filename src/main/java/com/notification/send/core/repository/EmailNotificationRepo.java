package com.notification.send.core.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notification.send.core.entity.EmailNotification;

public interface EmailNotificationRepo extends JpaRepository<EmailNotification, UUID>{

}
