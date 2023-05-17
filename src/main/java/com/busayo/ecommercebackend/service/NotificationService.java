package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.notification.NotificationDto;

import java.util.List;

public interface NotificationService {
    List<NotificationDto> getAllNotifications();
    void deleteNotification(Long id);
}
