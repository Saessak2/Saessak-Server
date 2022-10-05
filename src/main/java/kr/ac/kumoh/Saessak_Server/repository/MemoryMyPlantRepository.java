package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MemoryMyPlantRepository implements MyPlantRepository{

//    private static ConcurrentHashMap<Long, MyPlant> store = new ConcurrentHashMap<>();
    private static Map<Long, MyPlant> store = new HashMap<>();
    private static long sequence = 0L;  //mysql - can't use sequence, just for test

    @Override
    public MyPlant save(MyPlant myPlant) {
        myPlant.setId(sequence++);
        store.put(myPlant.getId(), myPlant);
        return myPlant;  //plan - void (MyPlant - test)
    }  //mysql - identity(can't use sequence)

    @Override
    public void delete(Long myPlantId) {

    }  //need reference code

    @Override
    public List<MyPlant> findByUserId(Long userId) {
        return store.values().stream()
                .filter(myPlant -> myPlant.getUser_id().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MyPlant> findById(Long plantId) {
        return store.values().stream()
                .filter(myPlant -> myPlant.getId().equals(plantId))
                .findAny();
    }

    public void clearStore(){
        store.clear();
    }

}
