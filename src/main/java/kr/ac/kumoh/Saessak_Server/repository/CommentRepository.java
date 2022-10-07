//package kr.ac.kumoh.Saessak_Server.repository;
//
//import kr.ac.kumoh.Saessak_Server.domain.Comment;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//
//@Repository
//@RequiredArgsConstructor
//public class CommentRepository {
//
//    private final EntityManager em;
//
//    //댓글 등록
//    public void createComment(Comment comment) {
//        em.persist(comment);
//    }
//
//    public Comment findOne(Long id) {
//        return em.find(Comment.class, id);
//    }
//
//    //댓글 수정
//    public void updateComment(Comment comment) {
//        Comment updateComment = findOne(comment.getId());
//        updateComment.setContent(comment.getContent());
//        updateComment.setUpdate_date(comment.getUpdate_date());
//    }
//
//    //댓글 삭제
//    public void deleteComment(Long id) {
//        Comment comment = findOne(id);
//        em.remove(comment);
//    }
//
//}
