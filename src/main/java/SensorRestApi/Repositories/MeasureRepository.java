package SensorRestApi.Repositories;

import SensorRestApi.Models.Measurements;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasureRepository extends JpaRepository<Measurements, Integer>{

}
