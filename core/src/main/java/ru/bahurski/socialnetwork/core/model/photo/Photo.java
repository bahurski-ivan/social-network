package ru.bahurski.socialnetwork.core.model.photo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * photo meta information entity
 */
@Entity(name = "PHOTOS")
public class Photo implements Serializable {
    private static final long serialVersionUID = -6164794479045446769L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private long id;

    @Column(name = "uploader_id", nullable = false, updatable = false)
    private long uploaderId;

    @Column(name = "time_upload", nullable = false, updatable = false)
    private LocalDateTime timeUpload;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(long uploaderId) {
        this.uploaderId = uploaderId;
    }

    public LocalDateTime getTimeUpload() {
        return timeUpload;
    }

    public void setTimeUpload(LocalDateTime timeUpload) {
        this.timeUpload = timeUpload;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", uploaderId=" + uploaderId +
                ", timeUpload=" + timeUpload +
                '}';
    }
}
