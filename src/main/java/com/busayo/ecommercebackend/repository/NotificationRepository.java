package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
