package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionRepository {

    private final EntityManager em;

    //질문 등록
    public void createQuestion(Question question) {
        em.persist(question);
    }

    //질문 수정
    public void updateQuestion(Question question) {
        Question updateQuestion = findOne(question.getId());

        updateQuestion.setContent(question.getContent());
        updateQuestion.setImg_path(question.getImg_path());
        updateQuestion.setCategory(question.getCategory());
    }

    public Question findOne(Long id) {
        return em.find(Question.class, id);
    }

    //질문 삭제
    public void deleteQuestion(Long id) {
        Question question = findOne(id);
        em.remove(question);
    }

    //질문 조회
    public List<Object[]> readAll() {
        String jpql = "select q.id, q.content, q.create_date, q.img_path, q.category, q.user_id.id, q.user_id.userName from Question q";
        Query query  = em.createQuery(jpql);

        List<Object[]> resultList = query.getResultList();

        return resultList;
    }

    //질문 상세 조회
    public Question readOne(Long id) {
        return em.find(Question.class, id);
    }


}
