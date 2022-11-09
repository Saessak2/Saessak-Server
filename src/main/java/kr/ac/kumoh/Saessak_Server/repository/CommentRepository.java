package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    //댓글 등록
    public void createComment(Comment comment) {
        em.persist(comment);
    }

    public Comment findOne(Long id) {
        return em.find(Comment.class, id);
    }

    //댓글 수정
    public void updateComment(Comment comment) {
        Comment updateComment = findOne(comment.getId());

        updateComment.setContent(comment.getContent());
    }

    //댓글 삭제
    public void deleteComment(Long id) {
        Comment comment = findOne(id);
        em.remove(comment);
    }

    //댓글 조회
    public List<Object[]> readCommentList(Long question_id) {
        String jpql = "select c.id, c.content, c.create_date, c.user_id.id, c.user_id.userName from Comment c where c.question_id = :question_id";
        Query query  = em.createQuery(jpql).setParameter("question_id", question_id);

        List<Object[]> resultList = query.getResultList();

        return resultList;
    }

    public int CommentCnt(Long question_id) {
        List<Object[]> list = readCommentList(question_id);
        int count = 0;
        for(int i = 0; i < list.size(); i++){
            count++;
        }
        return count;
    }

}
