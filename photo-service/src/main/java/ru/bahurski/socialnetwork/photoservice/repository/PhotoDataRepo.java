package ru.bahurski.socialnetwork.photoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bahurski.socialnetwork.core.model.photo.PhotoData;

/**
 * Created by Ivan on 22/12/2016.
 */
public interface PhotoDataRepo extends JpaRepository<PhotoData, Long> {
}
