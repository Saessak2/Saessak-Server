package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantPostDto;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantDto;
import kr.ac.kumoh.Saessak_Server.repository.MyPlantRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MyPlantService {

    private final String FILE_PATH = "C:/Users/mare1/workspace/Saessak-Server/src/main/resources/images/";

    private final MyPlantRepository repository;

    public MyPlantService(MyPlantRepository repository){
        this.repository = repository;
    }

    public Optional<Long> createMyPlant(MyPlantPostDto myPlantDto){
        Long ret = null;
        try {
            ret = repository.save(new MyPlant(myPlantDto)).getId();
        } catch(Exception ignored){ }
        return Optional.ofNullable(ret);
    }

    public String updateImage(Long id, MultipartFile file){
        String fileName = new Date().getTime() + "-" + file.getOriginalFilename();
        String path = FILE_PATH + fileName;
        try {
            file.transferTo(new File(path));
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return "IMAGE UPLOADING FAILED";
        }

        Optional<MyPlant> data = repository.findById(id);
        if(data.isEmpty())
            return "IMAGE UPLOADED, 404 ON " + id;
        else {
            repository.updateImgUrlById(path, true, id);
            MyPlant myPlant = data.get();
            myPlant.setImgUrl(path);
            repository.save(myPlant);
            return "IMAGE UPLOADED, imgUrl of " + id + " was updated";
        }
    }

    public List<MyPlantDto> readMyPlantList(Long userId){
        return convContentType(repository.findByUserId(userId));
    }

    public Optional<MyPlantDto> readMyFirstPlant(Long userId){
        List<MyPlantDto> myPlantDtoList = readMyPlantList(userId);
        return myPlantDtoList.stream()
                .filter(MyPlantDto::getIsActive)
                .findAny();
    }

    public Optional<MyPlantDto> readMyPlant(Long id){
        MyPlantDto ret = null;
        Optional<MyPlant> data = repository.findById(id);
        if(data.isPresent())
            ret = data.get().toDto();
        return Optional.ofNullable(ret);
    }

    public Optional<Long> updateMyPlant(Long id, MyPlantDto myPlantDto){
        Long ret = null;
        Optional<MyPlant> data = repository.findById(id);
        if(data.isPresent()) {
            MyPlant myPlant = data.get();
            myPlant.update(myPlantDto);
            ret = repository.save(myPlant).getId();
        }
        return Optional.ofNullable(ret);
    }

    public Optional<Long> updateLatestWaterDate(Long id){
        Long ret = null;
        Optional<MyPlant> data = repository.findById(id);
        if(data.isPresent()){
            MyPlant myPlant = data.get();
            myPlant.updateLatestWaterDateOnly();
            ret = repository.save(myPlant).getId();
        }
        return Optional.ofNullable(ret);
    }

    public Optional<Long> updateAbility(Long id){
        Long ret = null;
        Optional<MyPlant> data = repository.findById(id);
        if(data.isPresent()){
            MyPlant myPlant = data.get();
            myPlant.updateAbilityOnly();
            ret = repository.save(myPlant).getId();
        }
        return Optional.ofNullable(ret);
    }

    public void deleteMyPlant(Long id){
        repository.deleteById(id);
    }

    private List<MyPlantDto> convContentType(List<MyPlant> inList){
        List<MyPlantDto> retList = new ArrayList<>();
        for (MyPlant myPlant : inList)
            retList.add(myPlant.toDto());

        return retList;
    }

}
