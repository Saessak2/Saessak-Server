package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import kr.ac.kumoh.Saessak_Server.domain.Plan;
import kr.ac.kumoh.Saessak_Server.domain.User;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlanReqDto;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlanResDto;
import kr.ac.kumoh.Saessak_Server.repository.MyPlantRepository;
import kr.ac.kumoh.Saessak_Server.repository.PlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //TODO: 일정 자동 생성 검증
    public void createPlans(MyPlant myPlant){
        User user = myPlant.getUser();
        int cycle = myPlant.getWaterCycle();
        LocalDate inDate = myPlant.getLatestWaterDate();
        LocalDate maxDate = LocalDate.now().plusMonths(1);

        while(!inDate.isAfter(maxDate)){
            Plan plan = new Plan(0L, user, inDate, "water", myPlant, false);
            planRepo.save(plan);
            inDate = inDate.plusDays(cycle);
        }

        Plan firstPlan = planRepo.findPlanByMyPlantAndDate(
                myPlant.getId(), myPlant.getLatestWaterDate());
        firstPlan.setDone(true);
        planRepo.save(firstPlan);
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
            if(plan.getPlanType().equals("water")) {
                if (plan.isDone())
                    return doWatering(id);
                else
                    return undoWatering(id);
            }
            plan.update(planReqDto);
            ret = planRepo.save(plan).getId();
        }
        return Optional.ofNullable(ret);
    }

    public void updateDateOfPlans(
            Long plantId, int waterCycle, LocalDate inDate){
        List<Plan> data = planRepo.findPlansAfterInDate(plantId, inDate, "water");
        if(!data.isEmpty()){
            for(int i = 0 ; i < data.size(); i++){
                LocalDate dueDate = inDate.plusDays((long) (i + 1) * waterCycle);
                data.get(i).setDate(dueDate);
                planRepo.save(data.get(i));
            }
        }
    }

    @Transactional
    public Optional<Long> doWatering(Long id) {
        Long ret = null;
        Optional<Plan> data = planRepo.findById(id);
        if (data.isPresent()){
            Plan plan = data.get();
            if(!plan.getPlanType().equals("water"))
                return Optional.empty();

            else{
                plan.setDone(true);
                planRepo.save(plan);
                MyPlant myPlant = plan.getMyPlant();
                myPlant.setLatestWaterDate(LocalDate.now());
                myPlantRepo.save(myPlant);
                ret = plan.getId();
            }
        }
        return Optional.ofNullable(ret);
    }

    //TODO: 물주기 취소 검증
    @Transactional
    public Optional<Long> undoWatering(Long id) {
        Long ret = null;
        Optional<Plan> data = planRepo.findById(id);
        if (data.isPresent()) {
            Plan plan = data.get();
            if (!plan.getPlanType().equals("water"))
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
