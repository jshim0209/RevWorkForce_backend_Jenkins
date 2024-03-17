package com.revature.RevWorkforce.entity;

import com.revature.RevWorkforce.serializable.NotificationId;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "notifications")
@Table(name = "notifications")
@IdClass(NotificationId.class)
public class Notification {
    private Integer account_id;
    @Id
    private String description;
    @Id
    private LocalDateTime create_date;
    private String link;

    public Notification() {

    }

    public Notification(Integer account_id, String description) {
        this.account_id = account_id;
        this.description = description;
        this.create_date = LocalDateTime.now();
    }

    public Notification(Integer account_id, String description, LocalDateTime create_date) {
        this.account_id = account_id;
        this.description = description;
        this.create_date = create_date;
    }

    public Notification(Integer account_id, String description, String link) {
        this.account_id = account_id;
        this.description = description;
        this.link = link;
        this.create_date = LocalDateTime.now();
    }

    public Notification(Integer account_id, String description, LocalDateTime create_date, String link) {
        this.account_id = account_id;
        this.description = description;
        this.create_date = create_date;
        this.link = link;
    }

    public String getDescription() { return this.description; }
    public LocalDateTime getCreate_date() { return this.create_date; }
    public String getLink() { return this.link; }
    public Integer getAccount_id() { return this.account_id; }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public void setAccount_id(Integer account_id) { this.account_id = account_id; }

    @Override
    public String toString() {
        return "Notification: { " +
                "account_id: " + account_id +
                ", description: " + description +
                ", create_date: " + create_date.toString() +
                ", link: " + link + " }";
    }
}
