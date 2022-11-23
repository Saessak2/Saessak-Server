package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.Diary;
import kr.ac.kumoh.Saessak_Server.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    //일기 등록
    @Transactional
    public void create(Diary diary) {
        diaryRepository.createDiary(diary);
    }

    //일기 수정
    @Transactional
    public void update(Diary diary) {
        diaryRepository.updateDiary(diary);
    }

    //일기 삭제
    @Transactional
    public void delete(Long id) {
        diaryRepository.deleteDiary(id);
    }

    public Diary findOne(Long id) {
        Diary diary = diaryRepository.findOne(id);
        return diary;
    }

    //일기 전체 조회
    public List<Diary> readDiaryList(Long user_id) {
        List<Diary> diaryList = diaryRepository.readDiaryList(user_id);
        return diaryList;
    }

    //일기 상세 조회
    public Diary readDiaryDetail(Long diary_id) {
        Diary diary = diaryRepository.readDiaryDetail(diary_id);
        return diary;
    }

    //식물별 일기 조회
    public List<Diary> readDiaryByPlant(Long myplant_id) {
        List<Diary> diaryList = diaryRepository.readDiaryByPlant(myplant_id);
        return  diaryList;
    }

    //식물별 일기 조회 (최신 3개)
    public List<Diary> readDiaryByRecent(Long my_plant_id) {
        List<Diary> diaryList = diaryRepository.readDiaryByRecent(my_plant_id);
        return diaryList;
    }

    //이미지 등록
    @Transactional
    public void updateImage(Diary diary) { diaryRepository.updateImage(diary); }


}
