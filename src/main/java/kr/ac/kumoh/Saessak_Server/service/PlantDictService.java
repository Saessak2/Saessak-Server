package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.dto.PlantDict1Dto;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlantDict2Dto;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlantDict3Dto;
import kr.ac.kumoh.Saessak_Server.repository.PlantDict2Repository;
import kr.ac.kumoh.Saessak_Server.repository.PlantDict3Repository;
import kr.ac.kumoh.Saessak_Server.repository.PlantDict1Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlantDictService {

    private final PlantDict1Repository repository1;
    private final PlantDict2Repository repository2;
    private final PlantDict3Repository repository3;

    public PlantDictService(PlantDict1Repository repository1,
                            PlantDict2Repository repository2,
                            PlantDict3Repository repository3){
        this.repository1 = repository1;
        this.repository2 = repository2;
        this.repository3 = repository3;
    }

    public Optional<PlantDict1Dto> readOneFrom1(Long id){
        return Optional.of(new PlantDict1Dto(repository1.findByIdFrom1(id).get()));
    }

    public Optional<PlantDict2Dto> readOneFrom2(Long id){
        PlantDict2Dto tmp = new PlantDict2Dto(repository2.findByIdFrom2(id).get());
        return Optional.of(tmp);
    }

    public Optional<PlantDict3Dto> readOneFrom3(Long id){
        return Optional.of(new PlantDict3Dto(repository3.findByIdFrom3(id).get()));
    }
}
