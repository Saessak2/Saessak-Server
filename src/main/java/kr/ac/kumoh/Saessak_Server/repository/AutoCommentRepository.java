package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.AutoComment;
import kr.ac.kumoh.Saessak_Server.domain.dto.AutoCommentDTO;
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
        String jpql = "select a.link, a.title, a.tags, a.answer, a.date_time from AutoComment a where a.question_id.id = :question_id";
        Query query  = em.createQuery(jpql).setParameter("question_id", question_id);

        List<Object[]> resultList = query.getResultList();

        return resultList;
    }

    //자동댓글 삭제
    public Long update(Long question_id) {
        List<AutoComment> autoCommentList = em.createQuery("select a from AutoComment a where a.question_id.id = :question_id", AutoComment.class)
                .setParameter("question_id", question_id)
                .getResultList();

        Long id = autoCommentList.get(0).getId();
        return id;
    }

    //자동댓글 수정
    public void updateAutoComment(AutoComment autoComment) {
        AutoComment autoComment1 = new AutoComment();

        autoComment1.setTags(autoComment.getTags());
        autoComment1.setAnswer(autoComment.getAnswer());
        autoComment1.setQuestion_id(autoComment.getQuestion_id());
        autoComment1.setId(autoComment.getId());
        autoComment1.setLink(autoComment.getLink());
        autoComment1.setTitle(autoComment.getTitle());
    }
}
