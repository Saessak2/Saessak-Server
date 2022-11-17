package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.domain.dto.PlanDto;
import kr.ac.kumoh.Saessak_Server.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService service;

    @PostMapping()
    public ResponseEntity<Long> createPlan(@RequestBody PlanDto planDto){
        Optional<Long> ret = service.createPlan(planDto);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @GetMapping("/{plan-id}")
    public ResponseEntity<PlanDto> readPlan(@PathVariable("plan-id") Long id){
        Optional<PlanDto> ret = service.readPlan(id);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/{year}/{month}/{day}/user={user-id}")
    public ResponseEntity<List<PlanDto>> readUserDailyPlan(
            @PathVariable("year") int year, @PathVariable("month") int month,
            @PathVariable("day") int day, @PathVariable("user-id") Long userId){
        List<PlanDto> ret = service.readUserDailyPlanList(year, month, day, userId);
        if(!ret.isEmpty())
            return ResponseEntity.ok(ret);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{year}/{month}/user={user-id}")
    public ResponseEntity<List<PlanDto>> readUserMonthlyPlan(@PathVariable("user-id") Long userId,
            @PathVariable("year") int year, @PathVariable("month") int month){
        List<PlanDto> ret = service.readUserMonthlyPlanList(year, month, userId);
        if(!ret.isEmpty())
            return ResponseEntity.ok(ret);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{year}/{month}/my-plant={plant-id}")
    public ResponseEntity<List<PlanDto>> readMyPlantMonthlyPlan(@PathVariable("plant-id") Long plantId,
            @PathVariable("year") int year, @PathVariable("month") int month) {
        List<PlanDto> ret = service.readMyPlantMonthlyPlanList(year, month, plantId);
        if(!ret.isEmpty())
            return ResponseEntity.ok(ret);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{plan-id}")
    public ResponseEntity<Long> updatePlan(
            @PathVariable("plan-id") Long planId, @RequestBody PlanDto planDto){
        Optional<Long> ret = service.updatePlan(planId, planDto);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/{plan-id}/water")
    public ResponseEntity<Long> updateWatering(@PathVariable("plan-id") Long planId) {
        Optional<Long> ret = service.updateWaterPlan(planId);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @DeleteMapping("/{plan-id}")
    public ResponseEntity<PlanDto> deletePlan(@PathVariable("plan-id") Long planId){
        service.deletePlan(planId);
        return ResponseEntity.noContent().build();
    }

}
