package pl.coderslab.rentaapartment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.rentaapartment.model.Image;

import java.util.List;


@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {

}
