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

    @GetMapping()
    public ResponseEntity<PlanDto> readPlan(@RequestParam("plan-id") Long id){
        Optional<PlanDto> ret = service.readPlan(id);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/{year}/{month}/{day}")
    public ResponseEntity<List<PlanDto>> readUserDailyPlan(
            @PathVariable("year") int year, @PathVariable("month") int month,
            @PathVariable("day") int day, @RequestParam("user-id") Long userId){
        List<PlanDto> ret = service.readUserDailyPlanList(year, month, day, userId);
        if(!ret.isEmpty())
            return ResponseEntity.ok(ret);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<PlanDto>> readUserMonthlyPlan(@RequestParam("user-id") Long userId,
            @PathVariable("year") int year, @PathVariable("month") int month){
        List<PlanDto> ret = service.readUserMonthlyPlanList(year, month, userId);
        if(!ret.isEmpty())
            return ResponseEntity.ok(ret);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<PlanDto>> readMonthlyPlan(@RequestParam("plant-id") Long plantId,
            @PathVariable("year") int year, @PathVariable("month") int month) {
        List<PlanDto> ret = service.readMyPlantMonthlyPlanList(year, month, plantId);
        if(!ret.isEmpty())
            return ResponseEntity.ok(ret);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{plan-id}/{type}")
    public ResponseEntity<Long> updatePlan(@PathVariable("plan-id") Long planId,
            @PathVariable("type") String updateType, @RequestBody PlanDto planDto){
        Optional<Long> ret = Optional.empty();
        switch (updateType) {
            case "all":
                ret = service.updatePlan(planId, planDto);
                break;
            case "water":
                ret = service.updateWaterPlan(planId);
                break;
        }
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @DeleteMapping("/{plan-id}")
    public ResponseEntity<PlanDto> deletePlan(@PathVariable("plan-id") Long planId){
        service.deletePlan(planId);
        return ResponseEntity.noContent().build();
    }

}
