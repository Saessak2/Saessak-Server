package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;


public class MemoryMyPlantRepository extends SimpleJpaRepository<MyPlant, Long> implements MyPlantRepository{

    public MemoryMyPlantRepository(JpaEntityInformation<MyPlant, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public MemoryMyPlantRepository(Class<MyPlant> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public List<MyPlant> findByUserId(Long userId) {
        List<MyPlant> tempList = findAll();
        return tempList.stream()
                .filter(myPlant -> myPlant.getUser_id().equals(userId))
                .collect(Collectors.toList());
    }

}
