package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.Diary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DiaryRepository {

    private final EntityManager em;

    //일기 등록
    public void createDiary(Diary diary) {
        em.persist(diary);
    }

    //일기 수정
    public void updateDiary(Diary diary) {
        Diary updateDiary = findOne(diary.getId());

        updateDiary.setContent(diary.getContent());
        updateDiary.setWeather(diary.getWeather());
        updateDiary.setCond(diary.getCond());
        updateDiary.setActivity1(diary.getActivity1());
        updateDiary.setActivity2(diary.getActivity2());
        updateDiary.setActivity3(diary.getActivity3());
    }

    //일기 삭제
    public void deleteDiary(Long id) {
        Diary diary = findOne(id);
        em.remove(diary);
    }

    public Diary findOne(Long id) {
        return em.find(Diary.class, id);
    }

    //일기 전체 조회
    public List<Diary> readDiaryList(Long user_id) {
        List<Diary> diaryList = em.createQuery("select d from Diary d where d.user_id = :user_id", Diary.class)
                .setParameter("user_id", user_id)
                .getResultList();

        return diaryList;
    }

    //일기 상세 조회
    public Diary readDiaryDetail(Long id) {
        Diary diary = em.find(Diary.class, id);

        return diary;
    }

    //식물별 일기 조회
    public List<Diary> readDiaryByPlant(Long myplant_id) {
        List<Diary> diaryList = em.createQuery("select d from Diary d where d.myplant_id = :myplant_id", Diary.class)
                .setParameter("myplant_id", myplant_id)
                .getResultList();

        return diaryList;
    }

    //식물별 일기 조회 (최신 3개)
    public List<Diary> readDiaryByRecent(Long myplant_id) {
        if(myplant_id == 0) {
            List<Diary> diaryList = em.createQuery("select d from Diary d inner join MyPlant m on m.listOrder = (select min(m.listOrder) from MyPlant m) and m.isActive = 1 order by d.id desc")
                    .setFirstResult(0)
                    .setMaxResults(3)
                    .getResultList();

            return diaryList;
        } else {
            List<Diary> diaryList = em.createQuery("select d from Diary d where d.myplant_id = :myplant_id order by d.id desc",
                            Diary.class)
                    .setParameter("myplant_id", myplant_id)
                    .setFirstResult(0) //몇번째부터
                    .setMaxResults(3) //몇개까지
                    .getResultList();

            return diaryList;
        }
    }

    //이미지 저장
    public void updateImage(Diary diary) {
        Diary updateDiary = findOne(diary.getId());

        updateDiary.setImage(diary.getImage());
        updateDiary.setImg(diary.isImg());
    }

    //이미지 파일이름 조회
    public List<String> readAllImage() {
        String jpql = "select d.image.fileName from Diary d";
        Query query = em.createQuery(jpql);

        List<String> list = query.getResultList();

        return list;
    }

}
