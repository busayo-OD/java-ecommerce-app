package com.busayo.ecommercebackend.dto.notification;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class NotificationDto {

    private Long id;

    private String title;

    private String text;

    private Date createdOn;

    private String image;

    private String status;
}
