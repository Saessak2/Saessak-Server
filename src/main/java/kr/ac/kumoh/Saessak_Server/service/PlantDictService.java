package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.PlantDict;
import kr.ac.kumoh.Saessak_Server.domain.PlantDict1;
import kr.ac.kumoh.Saessak_Server.domain.PlantDict2;
import kr.ac.kumoh.Saessak_Server.domain.PlantDict3;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlantDict1Dto;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlantDict2Dto;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlantDict3Dto;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlantDictDto;
import kr.ac.kumoh.Saessak_Server.repository.PlantDict2Repository;
import kr.ac.kumoh.Saessak_Server.repository.PlantDict3Repository;
import kr.ac.kumoh.Saessak_Server.repository.PlantDict1Repository;
import kr.ac.kumoh.Saessak_Server.repository.PlantDictRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlantDictService {

    private final PlantDictRepository dictRepo;
    private final PlantDict1Repository dict1Repo;
    private final PlantDict2Repository dict2Repo;
    private final PlantDict3Repository dict3Repo;

    public PlantDictService(PlantDictRepository dictRepo,
                            PlantDict1Repository dict1Repo,
                            PlantDict2Repository dict2Repo,
                            PlantDict3Repository dict3Repo){
        this.dictRepo = dictRepo;
        this.dict1Repo = dict1Repo;
        this.dict2Repo = dict2Repo;
        this.dict3Repo = dict3Repo;
    }

    public List<PlantDictDto> readList(){
        return convContentType(dictRepo.findAll());
    }

    public Optional<PlantDict1Dto> readOneFrom1(Long id){
        PlantDict1Dto ret = null;
        Optional<PlantDict1> data = dict1Repo.findById(id);
        if(data.isPresent())
            ret = data.get().toDto();
        return Optional.ofNullable(ret);
    }

    public Optional<PlantDict2Dto> readOneFrom2(Long id){
        PlantDict2Dto ret = null;
        Optional<PlantDict2> data = dict2Repo.findById(id);
        if(data.isPresent())
            ret = data.get().toDto();
        return Optional.ofNullable(ret);
    }

    public Optional<PlantDict3Dto> readOneFrom3(Long id){
        PlantDict3Dto ret = null;
        Optional<PlantDict3> data = dict3Repo.findById(id);
        if(data.isPresent())
            ret = data.get().toDto();
        return Optional.ofNullable(ret);
    }

    private List<PlantDictDto> convContentType(List<PlantDict> inList){
        List<PlantDictDto> retList = new ArrayList<>();
        for (PlantDict plantDict : inList) {
            retList.add(plantDict.toDto());
        }
        return retList;
    }
 }
