package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.Plan;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlanDto;
import kr.ac.kumoh.Saessak_Server.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    private final PlanRepository repository;

    public PlanService(PlanRepository repository){
        this.repository = repository;
    }

    public Optional<Long> createPlan(PlanDto planDto){
        Long ret = null;
        try{
            ret = repository.save(new Plan(planDto)).getId();
        } catch(Exception ignored) { }
        return Optional.ofNullable(ret);
    }

    public List<PlanDto> readUserMonthlyPlanList(
            int year, int month, Long userId){
        return convContentType(
                repository.findPlansByUserAndMonth(userId, year, month));
    }

    public List<PlanDto> readMyPlantMonthlyPlanList(
            int year, int month, Long plantId){
        return convContentType(
                repository.findPlansByMyPlantAndMonth(plantId, year, month));
    }

    public List<PlanDto> readUserDailyPlanList(
            int year, int month, int day, Long userId){
        String date = year + "-" + month + "-" + day;
        return convContentType(
                repository.findPlansByUserAndDay(date, userId));
    }

    public Optional<PlanDto> readPlan(Long id){
        PlanDto ret = null;
        Optional<Plan> data = repository.findById(id);
        if(data.isPresent())
            ret = data.get().toDto();
        return Optional.ofNullable(ret);
    }

    // waterPlanUpdate arg id
    //TODO: 물 주기 아이콘 기능 구현
    public Optional<Long> updateWaterPlan(Long id){
        Long ret = null;


        return Optional.ofNullable(ret);
    }

    public Optional<Long> updatePlan(Long id, PlanDto planDto){
        Long ret = null;
        Optional<Plan> data = repository.findById(id);
        if(data.isPresent()){
            Plan plan = data.get();
            plan.update(planDto);
            ret = repository.save(plan).getId();
        }
        return Optional.ofNullable(ret);
    }

    public void deletePlan(Long id){
        repository.deleteById(id);
    }

    private List<PlanDto> convContentType(List<Plan> inList){
        List<PlanDto> retList = new ArrayList<>();
        for (Plan plan : inList)
            retList.add(plan.toDto());

        return retList;

    }

}
