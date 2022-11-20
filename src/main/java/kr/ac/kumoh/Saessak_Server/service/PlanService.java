package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import kr.ac.kumoh.Saessak_Server.domain.Plan;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlanReqDto;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlanResDto;
import kr.ac.kumoh.Saessak_Server.repository.MyPlantRepository;
import kr.ac.kumoh.Saessak_Server.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PlanService {

    private final PlanRepository planRepo;
    private final MyPlantRepository myPlantRepo;

    public PlanService(PlanRepository planRepo,
            MyPlantRepository myPlantRepo){
        this.planRepo = planRepo;
        this.myPlantRepo = myPlantRepo;
    }

    public Optional<Long> createPlan(PlanReqDto planReqDto){
        Long ret = null;
        try{
            ret = planRepo.save(new Plan(planReqDto)).getId();
        } catch(Exception ignored) { }
        return Optional.ofNullable(ret);
    }

    public List<PlanResDto> readUserMonthlyPlanList(
            int year, int month, Long userId){
        return convContentType(
                planRepo.findPlansByUserAndMonth(userId, year, month));
    }

    public List<PlanResDto> readMyPlantMonthlyPlanList(
            int year, int month, Long plantId){
        return convContentType(
                planRepo.findPlansByMyPlantAndMonth(plantId, year, month));
    }

    public List<PlanResDto> readUserDailyPlanList(
            int year, int month, int day, Long userId){
        LocalDate date = LocalDate.of(year, month, day);
        return convContentType(
                planRepo.findPlansByUserAndDay(userId, date));
    }

    public Optional<PlanResDto> readPlan(Long id){
        PlanResDto ret = null;
        Optional<Plan> data = planRepo.findById(id);
        if(data.isPresent())
            ret = data.get().toDto();
        return Optional.ofNullable(ret);
    }

    public Optional<Long> updatePlan(Long id, PlanReqDto planReqDto){
        Long ret = null;
        Optional<Plan> data = planRepo.findById(id);
        if(data.isPresent()){
            Plan plan = data.get();
            plan.update(planReqDto);
            ret = planRepo.save(plan).getId();
        }
        return Optional.ofNullable(ret);
    }

    //TODO: 물주기 작동 검증
    public Optional<Long> doWatering(Long id) {
        Long ret = null;
        Optional<Plan> data = planRepo.findById(id);
        if (data.isPresent()){
            Plan plan = data.get();
            if(!plan.getPlanType().equals("water")
                    || !(Objects.equals(plan.getDate(), LocalDate.now())))
                return Optional.empty();

            else{
                plan.setDone(true);
                planRepo.save(plan);
                MyPlant myPlant = plan.getMyPlant();
                myPlant.setLatestWaterDate(LocalDate.now());
                myPlantRepo.save(myPlant);
                ret = plan.getMyPlant().getId();
            }
        }
        return Optional.ofNullable(ret);
    }

    //TODO: 물주기 취소 검증 - save 누락되어 작동 안함
    public Optional<Long> undoWatering(Long id) {
        Long ret = null;
        Optional<Plan> data = planRepo.findById(id);
        if (data.isPresent()) {
            Plan plan = data.get();
            if (!plan.getPlanType().equals("water")
                    || !(Objects.equals(plan.getDate(), LocalDate.now())))
                return Optional.empty();

            else {
                plan.setDone(false);
                planRepo.save(plan);

                List<Plan> planList = planRepo.findPlansByMyPlantAndMonth(
                        plan.getMyPlant().getId(), LocalDate.now().getYear(),
                        LocalDate.now().getMonthValue());

                Plan latestPlan;
                for (int i = 1; i < planList.size(); i++) {
                    if (planList.get(i).getDate().equals(LocalDate.now())) {
                        latestPlan = planList.get(i - 1);
                        MyPlant myPlant = plan.getMyPlant();
                        myPlant.setLatestWaterDate(latestPlan.getDate());
                        myPlantRepo.save(myPlant);
                        ret = latestPlan.getId();
                    }
                }
            }
        }
        return Optional.ofNullable(ret);
    }

    public void deletePlan(Long id){
        planRepo.deleteById(id);
    }

    private List<PlanResDto> convContentType(List<Plan> inList){
        List<PlanResDto> retList = new ArrayList<>();
        for (Plan plan : inList)
            retList.add(plan.toDto());
        return retList;
    }

}
