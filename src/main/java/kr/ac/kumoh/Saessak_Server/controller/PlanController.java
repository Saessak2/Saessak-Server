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
        if(!ret.isEmpty())
            return ResponseEntity.ok(ret);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{year}/{month}/user={user-id}")
    public ResponseEntity<List<PlanResDto>> readUserMonthlyPlan(
            @PathVariable("user-id") Long userId, @PathVariable("year") int year,
            @PathVariable("month") int month){
        List<PlanResDto> ret = service.readUserMonthlyPlanList(year, month, userId);
        if(!ret.isEmpty())
            return ResponseEntity.ok(ret);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{year}/{month}/my-plant={plant-id}")
    public ResponseEntity<List<PlanResDto>> readMyPlantMonthlyPlan(
            @PathVariable("plant-id") Long plantId, @PathVariable("year") int year,
            @PathVariable("month") int month) {
        List<PlanResDto> ret = service.readMyPlantMonthlyPlanList(year, month, plantId);
        if(!ret.isEmpty())
            return ResponseEntity.ok(ret);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //TODO: Req 로 변경함, 검증 필요 - 문제 있을 시 Res 로 변경
    @PutMapping("/{plan-id}")
    public ResponseEntity<Long> updatePlan(
            @PathVariable("plan-id") Long id, @RequestBody PlanReqDto planReqDto){
        Optional<Long> ret = service.updatePlan(id, planReqDto);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    //TODO: planType: water 아닌 경우에 403 리턴 검증
    //TODO: 물 주기 갱신/취소 작동 검증
    @PutMapping("/{plan-id}/water/do")
    public ResponseEntity<Long> updateWateringDone(@PathVariable("plan-id") Long id) {
        Optional<Long> ret = service.doWatering(id);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/{plan-id}/water/undo")
    public ResponseEntity<Long> updateWateringUndone(@PathVariable("plan-id") Long id) {
        Optional<Long> ret = service.undoWatering(id);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @DeleteMapping("/{plan-id}")
    public ResponseEntity<PlanResDto> deletePlan(@PathVariable("plan-id") Long id){
        service.deletePlan(id);
        return ResponseEntity.noContent().build();
    }

}
