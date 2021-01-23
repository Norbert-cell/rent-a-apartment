package pl.coderslab.rentaapartment.service;

import org.springframework.stereotype.Service;
import pl.coderslab.rentaapartment.model.Apartment;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.repository.ApartmentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class CountBillsService {

    private final ApartmentRepository apartmentRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public CountBillsService(ApartmentRepository apartmentRepository, EntityManager entityManager) {
        this.apartmentRepository = apartmentRepository;
        this.entityManager = entityManager;
    }

    public double countBills(User user){
        List<Apartment> allByOwnerUser = apartmentRepository.findAllByOwnerUser(user);
        Double sumBills = allByOwnerUser.stream()
                .map(x -> x.getMyBills())
                .mapToDouble(Double::doubleValue)
                .sum();
        System.out.println(sumBills);
        double priceFromApartment = allByOwnerUser.stream()
                .map(x -> x.getPrice())
                .mapToDouble(Double::doubleValue)
                .sum();
        System.out.println(priceFromApartment);
        System.out.println(priceFromApartment - sumBills);
        return priceFromApartment - sumBills;
    }


    public double countEarningFromRentedApartment(List<Apartment> apartments) {
        return apartments.stream()
                .filter(x -> x.getTenantUser() != null)
                .map(Apartment::getPrice)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public double fullCostsUpKeepApartments(List<Apartment> apartments) {
        return apartments.stream()
                .map(Apartment::getMyBills)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public double costsUnrentedApartments(List<Apartment> allApartmentsUser) {
        return allApartmentsUser.stream().filter(x -> x.getTenantUser() == null)
                .map(x->x.getMyBills())
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
