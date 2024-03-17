package com.revature.RevWorkforce.serializable;

import java.io.Serializable;
import java.time.LocalDateTime;

public class NotificationId implements Serializable {
    private String description;
    private LocalDateTime create_date;

    public NotificationId() {

    }

    public NotificationId(String description, LocalDateTime create_date) {
        this.description = description;
        this.create_date = create_date;
    }
}