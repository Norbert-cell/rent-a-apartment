package pl.coderslab.rentaapartment.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.coderslab.rentaapartment.model.Apartment;
import pl.coderslab.rentaapartment.repository.ApartmentRepository;

@Component
public class ApartmentConverter implements Converter<String, Apartment> {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Override
    public Apartment convert(String s) {
        return apartmentRepository.findById(Long.parseLong(s)).orElseThrow();
    }
}
