package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@ComponentScan
@Repository
@Component
public class MemoryMyPlantRepository extends SimpleJpaRepository<MyPlant, Long> implements MyPlantRepository{

    @Autowired
    public MemoryMyPlantRepository(Class<MyPlant> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public List<MyPlant> findByuser_id(Long user_id) {
        List<MyPlant> tempList = findAll();
        return tempList.stream()
                .filter(myPlant -> myPlant.getUser().equals(user_id))
                .collect(Collectors.toList());
    }

}
