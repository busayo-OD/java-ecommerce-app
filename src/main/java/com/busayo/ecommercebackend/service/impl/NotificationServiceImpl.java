package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.notification.NotificationDto;
import com.busayo.ecommercebackend.exception.NotificationNotFoundException;
import com.busayo.ecommercebackend.model.Notification;
import com.busayo.ecommercebackend.repository.NotificationRepository;
import com.busayo.ecommercebackend.service.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<NotificationDto> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream().map((notification) -> modelMapper.map(notification, NotificationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteNotification(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));
        notificationRepository.delete(notification);
    }
}

