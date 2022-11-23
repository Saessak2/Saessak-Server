package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.domain.dto.PlanReqDto;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlanResDto;
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
    public ResponseEntity<Long> createPlan(@RequestBody PlanReqDto planReqDto){
        Optional<Long> ret = service.createPlan(planReqDto);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @GetMapping("/{plan-id}")
    public ResponseEntity<PlanResDto> readPlan(@PathVariable("plan-id") Long id){
        Optional<PlanResDto> ret = service.readPlan(id);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/{year}/{month}/{day}/user={user-id}")
    public ResponseEntity<List<PlanResDto>> readUserDailyPlan(
            @PathVariable("year") int year, @PathVariable("month") int month,
            @PathVariable("day") int day, @PathVariable("user-id") Long userId){
        List<PlanResDto> ret = service.readUserDailyPlanList(year, month, day, userId);
        return ResponseEntity.ok(ret);
    }

    @GetMapping("/{year}/{month}/user={user-id}")
    public ResponseEntity<List<PlanResDto>> readUserMonthlyPlan(
            @PathVariable("user-id") Long userId, @PathVariable("year") int year,
            @PathVariable("month") int month){
        List<PlanResDto> ret = service.readUserMonthlyPlanList(year, month, userId);
        return ResponseEntity.ok(ret);
    }

    @GetMapping("/{year}/{month}/my-plant={plant-id}")
    public ResponseEntity<List<PlanResDto>> readMyPlantMonthlyPlan(
            @PathVariable("plant-id") Long plantId, @PathVariable("year") int year,
            @PathVariable("month") int month) {
        List<PlanResDto> ret = service.readMyPlantMonthlyPlanList(year, month, plantId);
        return ResponseEntity.ok(ret);
    }

    @PutMapping("/{plan-id}")
    public ResponseEntity<Long> updatePlan(
            @PathVariable("plan-id") Long id, @RequestBody PlanReqDto planReqDto){
        Optional<Long> ret = service.updatePlan(id, planReqDto);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/{plant-id}/water/do")
    public ResponseEntity<Long> updateWateringDone(@PathVariable("plant-id") Long plantId) {
        Optional<Long> ret = service.updateWateringDone(plantId);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/{plant-id}/water/undo")
    public ResponseEntity<Long> updateWateringUndone(@PathVariable("plant-id") Long plantId) {
        Optional<Long> ret = service.updateWateringUndone(plantId);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @DeleteMapping("/{plan-id}")
    public ResponseEntity<PlanResDto> deletePlan(@PathVariable("plan-id") Long id){
        service.deletePlan(id);
        return ResponseEntity.noContent().build();
    }

}
