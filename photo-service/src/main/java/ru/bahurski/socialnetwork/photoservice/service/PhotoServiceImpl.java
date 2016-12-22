package ru.bahurski.socialnetwork.photoservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import ru.bahurski.socialnetwork.core.model.photo.Photo;
import ru.bahurski.socialnetwork.core.model.photo.PhotoData;
import ru.bahurski.socialnetwork.core.service.PhotoService;
import ru.bahurski.socialnetwork.photoservice.repository.PhotoDataRepo;
import ru.bahurski.socialnetwork.photoservice.repository.PhotoRepo;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by Ivan on 22/12/2016.
 */
public class PhotoServiceImpl implements PhotoService {
    private final PlatformTransactionManager transactionManager;
    private final PhotoDataRepo photoDataRepo;
    private final PhotoRepo photoRepo;

    @Autowired
    public PhotoServiceImpl(PlatformTransactionManager transactionManager,
                            PhotoDataRepo photoDataRepo, PhotoRepo photoRepo) {
        this.transactionManager = transactionManager;
        this.photoDataRepo = photoDataRepo;
        this.photoRepo = photoRepo;
    }

    @Override
    public Optional<byte[]> getPhotoBytes(long photoId) {
        final TransactionTemplate transaction = new TransactionTemplate(transactionManager);

        return transaction.execute(status -> {
            PhotoData pd = photoDataRepo.findOne(photoId);
            return pd == null ? Optional.empty() : Optional.ofNullable(pd.getBytes());
        });
    }

    @Override
    public Optional<Photo> uploadPhoto(Photo photo, byte[] bytes) {
        final TransactionTemplate transaction = new TransactionTemplate(transactionManager);

        photo.setId(0);
        photo.setTimeUpload(LocalDateTime.now());

        if (photo.getUploaderId() < 0 || bytes == null || bytes.length == 0)
            return Optional.empty();

        return transaction.execute((status) -> {
            Photo p = photoRepo.save(photo);
            PhotoData photoData = new PhotoData();

            photoData.setBytes(bytes);
            photoData.setPhotoId(p.getId());

            photoDataRepo.save(photoData);

            return Optional.of(p);
        });
    }

    @Override
    public Optional<Photo> getPhotoById(long id) {
        final TransactionTemplate transaction = new TransactionTemplate(transactionManager);
        return transaction.execute((status) -> {
            Photo p = photoRepo.findOne(id);
            return p == null ? Optional.empty() : Optional.of(p);
        });
    }
}
