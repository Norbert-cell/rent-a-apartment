package pl.coderslab.rentaapartment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.rentaapartment.model.Address;
import pl.coderslab.rentaapartment.repository.AddressRepository;

@Service
@Transactional
public class AddressService {

    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void saveAddress(Address address){
        addressRepository.save(address);
    }
}
