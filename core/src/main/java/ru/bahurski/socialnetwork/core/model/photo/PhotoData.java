package ru.bahurski.socialnetwork.core.model.photo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.Serializable;

/**
 * photo binary data entity
 */
@Entity(name = "PHOTOS_DATA")
public class PhotoData implements Serializable {
    private static final long serialVersionUID = -3359125567163762116L;

    @Id
    @Column(name = "photo_id")
    private long photoId;

    @Lob
    @Column(name = "bytes")
    private byte[] bytes;


    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
