package pl.coderslab.rentaapartment.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.rentaapartment.model.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImageConverter implements Converter<List<MultipartFile>, List<Image>> {


    @Override
    public List<Image> convert(List<MultipartFile> multipartFiles) {
        List<Image> images = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            try {
                InputStream inputStream = multipartFile.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       return null;
    }
}
