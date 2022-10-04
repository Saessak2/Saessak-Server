package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.Notification;
import kr.ac.kumoh.Saessak_Server.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationRepository {

    private final EntityManager em;

    //알림함 조회
    public List<Notification> readSetting() {
        return em.createQuery("select n from Notification n", Notification.class)
                .getResultList();
    }

    //캘린더 알림

    //날씨별 관리 알림

    //댓글 알림
}
