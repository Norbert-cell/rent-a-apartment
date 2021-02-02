package pl.coderslab.rentaapartment.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.rentaapartment.model.Image;
import pl.coderslab.rentaapartment.repository.ImageRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class ImageService {

    private final ImageRepository imageRepository;



    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    public void saveImage(Image image) {
        imageRepository.save(image);
    }

    public Image findById(long imageId) {
        return imageRepository.findById(imageId).orElse(new Image());
    }

    public List<Image> uploadFile(List<MultipartFile> images) {
        List<Image> imagesList = new ArrayList<>();
        for (MultipartFile multipartFile : images) {
            Image image = new Image();
            image.setName(multipartFile.getOriginalFilename());
            try {
                image.setByteImage(multipartFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            imagesList.add(image);
        }
        return imagesList;
    }
}
