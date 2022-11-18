package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.Plan;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlanReqDto;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlanResDto;
import kr.ac.kumoh.Saessak_Server.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PlanService {

    private final PlanRepository repository;

    public PlanService(PlanRepository repository){
        this.repository = repository;
    }

    public Optional<Long> createPlan(PlanReqDto planReqDto){
        Long ret = null;
        try{
            ret = repository.save(new Plan(planReqDto)).getId();
        } catch(Exception ignored) { }
        return Optional.ofNullable(ret);
    }

    public List<PlanResDto> readUserMonthlyPlanList(
            int year, int month, Long userId){
        return convContentType(
                repository.findPlansByUserAndMonth(userId, year, month));
    }

    public List<PlanResDto> readMyPlantMonthlyPlanList(
            int year, int month, Long plantId){
        return convContentType(
                repository.findPlansByMyPlantAndMonth(plantId, year, month));
    }

    public List<PlanResDto> readUserDailyPlanList(
            int year, int month, int day, Long userId){
        LocalDate date = LocalDate.of(year, month, day);
        return convContentType(
                repository.findPlansByUserAndDay(userId, date));
    }

    public Optional<PlanResDto> readPlan(Long id){
        PlanResDto ret = null;
        Optional<Plan> data = repository.findById(id);
        if(data.isPresent())
            ret = data.get().toDto();
        return Optional.ofNullable(ret);
    }

    public Optional<Long> updatePlan(Long id, PlanResDto planResDto){
        Long ret = null;
        Optional<Plan> data = repository.findById(id);
        if(data.isPresent()){
            Plan plan = data.get();
            plan.update(planResDto);
            ret = repository.save(plan).getId();
        }
        return Optional.ofNullable(ret);
    }

    public Optional<Long> updateWaterPlan(Long planId) {
        Long ret = null;
        Optional<Plan> data = repository.findById(planId);
        if (data.isPresent()){
            Plan plan = data.get();
            if (Objects.equals(plan.getDate(), LocalDate.now()))
                ret = setWatered(plan);
            else
                ret = undoWatering(plan);
        }
        return Optional.ofNullable(ret);
    }

    public void deletePlan(Long id){
        repository.deleteById(id);
    }

    private List<PlanResDto> convContentType(List<Plan> inList){
        List<PlanResDto> retList = new ArrayList<>();
        for (Plan plan : inList)
            retList.add(plan.toDto());
        return retList;
    }

    private Long setWatered(Plan plan){
        plan.setDone(true);
        plan.getMyPlant().setLatestWaterDate(LocalDate.now());
        plan.getMyPlant().setLatestWaterDate(LocalDate.now());
        return plan.getMyPlant().getId();
    }

    private Long undoWatering(Plan plan){
        plan.setDone(false);

        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        List<Plan> planList = repository.findPlansByMyPlantAndMonth(
                plan.getMyPlant().getId(), year, month);

        Plan latestPlan;
        for(int i = 1; i < planList.size(); i++){
            if(planList.get(i).getDate().equals(LocalDate.now())){
                planList.get(i).setDone(false);
                latestPlan = planList.get(i - 1);
                plan.getMyPlant().setLatestWaterDate(latestPlan.getDate());
                return latestPlan.getId();
            }
        }
        return plan.getId();
    }

}
