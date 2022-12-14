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
        updateQuestion.setCategory(question.getCategory());
    }

    //댓글 수 update
    public void updateCommentCnt(Question question, int temp) {
        Question updateQuestion = findOne(question.getId());
        int count = updateQuestion.getAnswer_count();
        if(temp == 1) {
            updateQuestion.setAnswer_count(count+1);
        } else {
            updateQuestion.setAnswer_count(count-1);
        }


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
        String jpql = "select q.id, q.content, q.create_date, q.category, q.user_id.id, q.user_id.userName, q.answer_count, q.img from Question q order by q.id desc";
        Query query  = em.createQuery(jpql);

        List<Object[]> resultList = query.getResultList();

        return resultList;
    }

    //질문 상세 조회
    public Question readOne(Long id) {
        return em.find(Question.class, id);
    }

    //이미지 저장
    public void updateImage(Question question) {
        Question updateQuestion = findOne(question.getId());

        updateQuestion.setImage(question.getImage());
        updateQuestion.setImg(question.isImg());
    }

    //이미지 파일이름 조회
    public List<String> readAllImage() {
        String jpql = "select q.image.fileName from Question q";
        Query query = em.createQuery(jpql);

        List<String> list = query.getResultList();

        return list;
    }

}
