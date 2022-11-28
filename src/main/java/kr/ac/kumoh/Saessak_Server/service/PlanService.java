package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import kr.ac.kumoh.Saessak_Server.domain.Plan;
import kr.ac.kumoh.Saessak_Server.domain.User;
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

    public void createPlans(MyPlant myPlant) {
        final int AVERAGE_WATER_CYCLE = 15;
        User user = myPlant.getUser();
        int cycle = myPlant.getWaterCycle();

        LocalDate inDate = myPlant.getLatestWaterDate().minusDays(cycle);
        while (!inDate.isAfter(myPlant.getLatestWaterDate())) {
            Plan plan = new Plan(0L, user, inDate, "water", myPlant, true);
            planRepo.save(plan);
            inDate = inDate.plusDays(cycle);
        }

        if (cycle <= AVERAGE_WATER_CYCLE)
            createPlansUntilN(myPlant, user, cycle);
        else
            createPlansForN(myPlant, user, cycle);
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

    public Optional<Long> checkPlansUpdate(Long userId){
        Long ret = userId;
        List<MyPlant> myPlantList = myPlantRepo.findByUserId(userId);
        for (MyPlant myPlant : myPlantList) {
            ret = checkIfPlanNeedsUpdate(myPlant);
        }
        return Optional.ofNullable(ret);
    }

    private Long checkIfPlanNeedsUpdate(MyPlant myPlant){
        Optional<Plan> data = planRepo
                .findTopByMyPlantAndPlanTypeAndDateIsBeforeOrderByDateDesc(
                        myPlant, "water", LocalDate.now());
        if(data.isPresent() && !data.get().isDone()){
            List<Plan> planList = planRepo.findPlansAfterInDate(
                    myPlant.getId(), myPlant.getLatestWaterDate(), "water");
            return updateDateOfPlans(planList, myPlant.getWaterCycle(),
                    LocalDate.now().minusDays(myPlant.getWaterCycle())).getId();
        }
        return myPlant.getId();
    }

    public Optional<Long> updatePlan(Long id, PlanReqDto planReqDto){
        Long ret = null;
        Optional<Plan> data = planRepo.findById(id);
        if(data.isPresent()){
            Plan plan = data.get();
            if(plan.getPlanType().equals("water")) {
                if (plan.isDone())
                    return updateWateringDone(plan.getMyPlant().getId());
                else
                    return updateWateringUndone(id);
            }
            plan.update(planReqDto);
            ret = planRepo.save(plan).getId();
        }
        return Optional.ofNullable(ret);
    }

    public Plan updateDateOfPlans(
            Long plantId, int waterCycle, LocalDate inDate) {
        List<Plan> data = planRepo.findPlansAfterInDate(plantId, inDate, "water");
        return updateDateOfPlans(data, waterCycle, inDate);
    }

    public Optional<Long> updateWateringDone(Long plantId) {
        Long ret = null;
        Optional<MyPlant> data = myPlantRepo.findById(plantId);
        if (data.isPresent()){
            MyPlant myPlant = data.get();
            Optional<Plan> passedPlan = planRepo
                    .findTopByPlanTypeAndMyPlantAndIsDoneIsTrueOrderByDateDesc(
                            "water", myPlant);
            if(passedPlan.isPresent()){
                return Optional.empty();
            }
            List<Plan> tempList = planRepo.findPlansAfterInDate(
                    plantId, myPlant.getLatestWaterDate(), "water");

            if(!tempList.isEmpty()) {
                Plan plan = tempList.get(0);
                if (Objects.equals(plan.getDate(), LocalDate.now()))
                    ret = water(myPlant, plan, tempList.get(tempList.size() - 1).getDate());
                else
                    ret = earlyWatered(myPlant, plan, tempList);
            }
        }
        return Optional.ofNullable(ret);
    }

    public Optional<Long> updateWateringUndone(Long plantId) {
        Long ret = null;
        Optional<MyPlant> myPlantDate = myPlantRepo.findById(plantId);
        if (myPlantDate.isPresent()) {
            MyPlant myPlant = myPlantDate.get();
            List<Plan> planData = planRepo.findPlanByDateAndType(
                    plantId, myPlant.getLatestWaterDate(), "water");
            if(!planData.isEmpty())
                ret = cancelWatering(myPlant, planData.get(0));
        }
        return Optional.ofNullable(ret);
    }

    public void deletePlan(Long id){
        planRepo.deleteById(id);
    }

    private void createPlansUntilN(MyPlant myPlant, User user, int cycle){
        final int AUTO_CREATING_PLAN_MONTH = 1;
        LocalDate inDate = myPlant.getLatestWaterDate().plusDays(cycle);
        LocalDate maxDate = LocalDate.now().plusMonths(AUTO_CREATING_PLAN_MONTH);

        while(!inDate.isAfter(maxDate)){
            Plan plan = new Plan(0L, user, inDate, "water", myPlant, false);
            planRepo.save(plan);
            inDate = inDate.plusDays(cycle);
        }
    }

    private void createPlansForN(MyPlant myPlant, User user, int cycle){
        final int AUTO_CREATING_PLAN_COUNT = 4;
        LocalDate inDate = myPlant.getLatestWaterDate().plusDays(cycle);

        for(int i = 0; i < AUTO_CREATING_PLAN_COUNT; i++){
            Plan plan = new Plan(0L, user, inDate, "water", myPlant, false);
            planRepo.save(plan);
            inDate = inDate.plusDays(cycle);
        }
    }

    private List<PlanResDto> convContentType(List<Plan> inList){
        List<PlanResDto> retList = new ArrayList<>();
        for (Plan plan : inList)
            retList.add(plan.toDto());
        return retList;
    }

    private Long water(MyPlant myPlant, Plan plan, LocalDate lastDate){
        plan.setDone(true);
        planRepo.save(plan);

        myPlant.setLatestWaterDate(LocalDate.now());
        myPlantRepo.save(myPlant);

        planRepo.save(new Plan(
                0L, myPlant.getUser(), lastDate.plusDays(myPlant.getWaterCycle()),
                "water", myPlant, false));

        return plan.getId();
    }

    private Long earlyWatered(MyPlant myPlant, Plan plan, List<Plan> planList){
        plan.setDate(LocalDate.now());
        plan.setDone(true);
        planRepo.save(plan);

        myPlant.setLatestWaterDate(LocalDate.now());
        myPlantRepo.save(myPlant);

        planList.remove(0);
        LocalDate lastDate = updateDateOfPlans(
                planList, myPlant.getWaterCycle(), LocalDate.now()).getDate();

        planRepo.save(new Plan(
                0L, myPlant.getUser(), lastDate.plusDays(myPlant.getWaterCycle()),
                "water", myPlant, false));

        return plan.getId();
    }

    private Long cancelWatering(MyPlant myPlant, Plan plan) {
        plan.setDone(false);
        planRepo.save(plan);

        LocalDate passedLWD = myPlant.getLatestWaterDate();
        Optional<Plan> passedPlan = planRepo
                .findTopByPlanTypeAndMyPlantAndIsDoneIsTrueOrderByDateDesc("water", myPlant);
        if(passedPlan.isEmpty())
            myPlant.setLatestWaterDate(
                    myPlant.getLatestWaterDate().minusDays(myPlant.getWaterCycle()));
        else
            myPlant.setLatestWaterDate(passedPlan.get().getDate());
        myPlantRepo.save(myPlant);

        List<Plan> tempList = planRepo.findPlansAfterInDate(
                myPlant.getId(), myPlant.getLatestWaterDate(), "water");
        Plan lastPlan = tempList.get(tempList.size() - 1);
        if(passedLWD != myPlant.getLatestWaterDate().plusDays(myPlant.getWaterCycle()))
            lastPlan = updateDateOfPlans(
                    myPlant.getId(), myPlant.getWaterCycle(), myPlant.getLatestWaterDate());

        planRepo.delete(lastPlan);

        return plan.getId();
    }

    private Plan updateDateOfPlans(List<Plan> data, int waterCycle, LocalDate inDate){
        Plan lastPlan = data.get(0);
        for(int i = 0 ; i < data.size(); i++) {
            LocalDate dueDate = inDate.plusDays((long) (i + 1) * waterCycle);
            data.get(i).setDate(dueDate);
            lastPlan = planRepo.save(data.get(i));
        }
        return lastPlan;
    }

}
