package ru.bahurski.socialnetwork.photoservice.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.bahurski.socialnetwork.core.model.photo.Photo;
import ru.bahurski.socialnetwork.core.service.PhotoService;

import java.util.Optional;

/**
 * Created by Ivan on 22/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class, loader = AnnotationConfigContextLoader.class)
public class PhotoServiceImplTest {

    @Autowired
    PhotoService photoService;

    @Test
    public void getPhotoBytes() throws Exception {
        Photo photo = new Photo();
        byte[] bytes = {1, 2, 3, 4, 5};

        photo.setUploaderId(1);

        Optional<Photo> p = photoService.uploadPhoto(photo, bytes);

        Assert.assertTrue(p.isPresent());

        Photo pp = p.get();

        Optional<Photo> rr = photoService.getPhotoById(pp.getId());
        Optional<byte[]> rb = photoService.getPhotoBytes(pp.getId());

        Assert.assertTrue(rr.isPresent() && rb.isPresent());

        Assert.assertArrayEquals(bytes, rb.get());

        Photo rrr = rr.get();

        Assert.assertEquals(pp.getId(), rrr.getId());
        Assert.assertEquals(pp.getTimeUpload(), rrr.getTimeUpload());
        Assert.assertEquals(pp.getUploaderId(), rrr.getUploaderId());
    }
}