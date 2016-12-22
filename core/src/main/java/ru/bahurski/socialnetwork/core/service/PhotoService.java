package ru.bahurski.socialnetwork.core.service;

import ru.bahurski.socialnetwork.core.model.photo.Photo;

import java.util.Optional;

/**
 * photo ru.bahurski.socialnetwork.friendservice.service interface
 */
public interface PhotoService {
    /**
     * get image bytes
     *
     * @param photoId requested photo id
     * @return array of photo bytes or empty if not found
     */
    Optional<byte[]> getPhotoBytes(long photoId);

    /**
     * store photo
     *
     * @param photo meta data
     * @param bytes photo bytes
     * @return uploaded photo object OR empty if userId not set
     */
    Optional<Photo> uploadPhoto(Photo photo, byte[] bytes);

    /**
     * returns photo by it's id
     *
     * @return empty if not found
     */
    Optional<Photo> getPhotoById(long id);
}
