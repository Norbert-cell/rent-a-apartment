package pl.coderslab.rentaapartment.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.coderslab.rentaapartment.model.Address;
import pl.coderslab.rentaapartment.repository.AddressRepository;

@Component
public class AddressConverter implements Converter<String, Address> {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address convert(String s) {
        return addressRepository.findById(Long.parseLong(s)).orElseThrow();
    }
}
