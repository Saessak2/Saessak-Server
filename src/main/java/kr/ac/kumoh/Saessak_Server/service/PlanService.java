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

    public PlanService(PlanRepository planRepo, MyPlantRepository myPlantRepo){
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

    @Transactional
    public Optional<Long> checkPlansUpdate(Long userId){
        Long ret = userId;
        List<MyPlant> myPlantList = myPlantRepo.findByUserId(userId);
        for (MyPlant myPlant : myPlantList) {
            ret = checkIfPlanNeedsUpdate(myPlant);
        }
        return Optional.ofNullable(ret);
    }

    public void createPlansForNewPlant(MyPlant myPlant) {
        User user = myPlant.getUser();
        int cycle = myPlant.getWaterCycle();

        LocalDate inDate = myPlant.getLatestWaterDate().minusDays(cycle);
        while (!inDate.isAfter(myPlant.getLatestWaterDate())) {
            Plan plan = new Plan(0L, user, inDate, "water", myPlant, true);
            planRepo.save(plan);
            inDate = inDate.plusDays(cycle);
        }

        if (cycle <= 15)
            createPlansUntilM(myPlant, user, cycle);
        else
            createPlansForN(myPlant, user, cycle);
    }

    public Optional<PlanResDto> readPlan(Long id){
        PlanResDto ret = null;
        Optional<Plan> data = planRepo.findById(id);
        if(data.isPresent())
            ret = data.get().toDto();
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
        return convContentType(
                planRepo.findPlansByUserAndDay(
                        userId, LocalDate.of(year, month, day)));
    }

    public Optional<Long> updatePlan(Long id, PlanReqDto planReqDto){
        Optional<Plan> data = planRepo.findById(id);
        String[] attrs = new String[4];
        if(data.isPresent()){
            Plan plan = data.get();
            attrs[0] = plan.getPlanType();
            attrs[1] = String.valueOf(plan.isDone());
            attrs[2] = planReqDto.getPlanType();
            attrs[3] = String.valueOf(planReqDto.getDone());

            plan.update(planReqDto);
            switch(checkUpdatedAttrs(attrs)){
                case  1:
                    return Optional.ofNullable(updateWaterPlanDone(plan));
                case  0:
                    plan.update(planReqDto);
                    return Optional.ofNullable(planRepo.save(plan).getId());
                case -1:
                    return Optional.ofNullable(updateWaterPlanUndone(plan));
            }
        }
        return Optional.empty();
    }

    public Optional<Long> updateWaterPlanDoneWithPlantId(Long plantId) {
        Long ret = null;
        Optional<MyPlant> data = myPlantRepo.findById(plantId);
        if (data.isPresent()){
            MyPlant myPlant = data.get();
            if (wateredToday(myPlant))
                return Optional.empty();

            List<Plan> tempList = planRepo.findPlansAfterInDate(
                    plantId, myPlant.getLatestWaterDate(), "water");
            if(!tempList.isEmpty()) {
                Plan plan = tempList.get(0);
                if(!Objects.equals(plan.getDate(), LocalDate.now()))
                    plan.setDate(LocalDate.now());
                ret = updateWaterPlanDone(plan);
            }
        }
        return Optional.ofNullable(ret);
    }

    private boolean wateredToday(MyPlant myPlant){
        List<Plan> tempList = planRepo.findPlanByDateAndType(
                myPlant.getId(), myPlant.getLatestWaterDate(), "water");
        return !tempList.isEmpty() && tempList.get(0).isDone();
    }

    public Optional<Long> updateWaterPlanUndoneWithPlantId(Long plantId) {
        Long ret = null;
        Optional<MyPlant> myPlantDate = myPlantRepo.findById(plantId);
        if (myPlantDate.isPresent()) {
            List<Plan> tempList = planRepo.findPlanByDateAndType(
                    plantId, LocalDate.now(), "water");
            if(!tempList.isEmpty() && tempList.get(0).isDone())
                ret = updateWaterPlanUndone(tempList.get(0));
        }
        return Optional.ofNullable(ret);
    }

    public void updateDateOfPlans(
            Long plantId, int waterCycle, LocalDate inDate) {
        List<Plan> data = planRepo.findPlansAfterInDate(plantId, inDate, "water");
        updateDateOfPlans(data, waterCycle, inDate);
    }

    public void deletePlan(Long id){
        planRepo.deleteById(id);
    }

    private Long checkIfPlanNeedsUpdate(MyPlant myPlant){
        Optional<Plan> data = planRepo
                .findTopByMyPlantAndPlanTypeAndIsDoneAndDateIsBeforeOrderByDateDesc(
                        myPlant, "water", false, LocalDate.now());
        if(data.isPresent()){
            List<Plan> planList = planRepo.findPlansAfterInDate(
                    myPlant.getId(), myPlant.getLatestWaterDate(), "water");
            return updateDateOfPlans(planList, myPlant.getWaterCycle(),
                    LocalDate.now().minusDays(myPlant.getWaterCycle())).getId();
        }
        return myPlant.getId();
    }

    private void createPlansUntilM(MyPlant myPlant, User user, int cycle){
        LocalDate inDate = myPlant.getLatestWaterDate().plusDays(cycle);
        LocalDate maxDate = LocalDate.now().plusMonths(1);
        while(!inDate.isAfter(maxDate)){
            Plan plan = new Plan(0L, user, inDate, "water", myPlant, false);
            planRepo.save(plan);
            inDate = inDate.plusDays(cycle);
        }
    }

    private void createPlansForN(MyPlant myPlant, User user, int cycle){
        LocalDate inDate = myPlant.getLatestWaterDate().plusDays(cycle);
        for(int i = 0; i < 4; i++){
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

    private int checkUpdatedAttrs(String[] attrs){
        if(attrs[0].equals(attrs[2]) && attrs[0].equals("water")){
            if(!attrs[1].equals(attrs[3]) && attrs[1].equals("true"))
                return -1;
            if(!attrs[1].equals(attrs[3]) && attrs[3].equals("true"))
                return 1;
        }
        return 0;
    }

    private Long updateWaterPlanDone(Plan plan){
        MyPlant myPlant = plan.getMyPlant();
        water(plan);
        LocalDate updatedLWD = updateLWD(plan, myPlant);
        updateDateOfPlans(myPlant.getId(), myPlant.getWaterCycle(), updatedLWD);
        createExtraPlan(plan.getMyPlant());
        return plan.getId();
    }

    private void water(Plan plan){
        plan.setDone(true);
        planRepo.save(plan);
    }

    private LocalDate updateLWD(Plan plan, MyPlant myPlant){
        if(myPlant.getLatestWaterDate().isBefore(plan.getDate()))
            myPlant.setLatestWaterDate(plan.getDate());
        return myPlantRepo.save(myPlant).getLatestWaterDate();
    }

    private void createExtraPlan(MyPlant myPlant){
        Optional<Plan> lastPlan =
                planRepo.findTopByMyPlantAndPlanTypeAndDateIsAfterOrderByDateDesc(
                        myPlant, "water",  myPlant.getLatestWaterDate());
        lastPlan.ifPresent(plan -> planRepo.save(new Plan(
                0L, myPlant.getUser(),
                plan.getDate().plusDays(myPlant.getWaterCycle()),
                "water", myPlant, false)));
    }

    private Long updateWaterPlanUndone(Plan plan) {
        MyPlant myPlant = plan.getMyPlant();
        cancelWatering(plan);
        LocalDate updatedLWD = rollbackLWD(plan.getMyPlant());
        updateDateOfPlans(myPlant.getId(), myPlant.getWaterCycle(), updatedLWD);
        deleteExtraPlan(plan.getMyPlant());
        return plan.getId();
    }

    private void cancelWatering(Plan plan) {
        plan.setDone(false);
        planRepo.save(plan);
    }

    private LocalDate rollbackLWD(MyPlant myPlant){
        Optional<Plan> passedPlan = planRepo
                .findTopByMyPlantAndPlanTypeAndIsDoneAndDateIsBeforeOrderByDateDesc(
                        myPlant, "water", true, myPlant.getLatestWaterDate());
        if(passedPlan.isPresent())
            myPlant.setLatestWaterDate(passedPlan.get().getDate());
        else
            myPlant.setLatestWaterDate(
                    myPlant.getLatestWaterDate().minusDays(myPlant.getWaterCycle()));
        return myPlantRepo.save(myPlant).getLatestWaterDate();
    }

    private void deleteExtraPlan(MyPlant myPlant) {
        Optional<Plan> data = planRepo
                .findTopByMyPlantAndPlanTypeAndDateIsAfterOrderByDateDesc(
                        myPlant, "water", myPlant.getLatestWaterDate());
        data.ifPresent(planRepo::delete);
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
