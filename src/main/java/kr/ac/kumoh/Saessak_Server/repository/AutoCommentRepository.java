package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.AutoComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AutoCommentRepository {

    private final EntityManager em;

    //자동댓글 저장
    public Long createAutoComment(AutoComment autoComment) {
        em.persist(autoComment);
        return autoComment.getId();
    }

    public AutoComment findOne(Long id) {
        return em.find(AutoComment.class, id);
    }

    //자동댓글 조회
    public List<Object[]> readAutoCommentList(Long question_id) {
        String jpql = "select a.link, a.title, a.similarity, a.answer from AutoComment a where a.question_id.id = :question_id";
        Query query  = em.createQuery(jpql).setParameter("question_id", question_id);

        List<Object[]> resultList = query.getResultList();

        return resultList;
    }

    //자동댓글 삭제
    public void delete(Long question_id) {
        List<AutoComment> autoCommentList = em.createQuery("select a from AutoComment a where a.question_id.id = :question_id", AutoComment.class)
                .setParameter("question_id", question_id)
                .getResultList();

        Long id = autoCommentList.get(0).getId();
        em.remove(id);
    }

}
