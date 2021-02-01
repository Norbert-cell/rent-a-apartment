package pl.coderslab.rentaapartment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.rentaapartment.model.Image;
import pl.coderslab.rentaapartment.service.ApartmentService;
import pl.coderslab.rentaapartment.service.ImageService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping("/app/image")
public class ImageController {

    private final ImageService imageService;
    private final ApartmentService apartmentService;

    public ImageController(ImageService imageService, ApartmentService apartmentService) {
        this.imageService = imageService;
        this.apartmentService = apartmentService;
    }

    @GetMapping("/get/{id}")
    public void getImageApartment(@PathVariable long id, HttpServletResponse response){

        try {
            OutputStream output = response.getOutputStream();
            List<Image> images = apartmentService.getOne(id).getImages();
            Image first = images.stream()
                    .findFirst().orElse(new Image());
            output.write(first.getByteImage());
            response.setContentType("image/jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/{imageId}")
    public void getImage(@PathVariable long imageId, HttpServletResponse response){
        Image img = imageService.findById(imageId);
        try {
            OutputStream outputStream =  response.getOutputStream();
            outputStream.write(img.getByteImage());
            response.setContentType("image/jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
