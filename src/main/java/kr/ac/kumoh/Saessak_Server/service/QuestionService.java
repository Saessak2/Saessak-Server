package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.Question;
import kr.ac.kumoh.Saessak_Server.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    //질문 등록
    @Transactional
    public void create(Question question) {
        questionRepository.createQuestion(question);
    }

    //질문 수정
    @Transactional
    public void update(Question question) {
        questionRepository.updateQuestion(question);
    }

    //댓글 수 update
    @Transactional
    public void updateCommentCnt(Question question, int temp) {
        questionRepository.updateCommentCnt(question, temp);
    }

    //이미지 등록
    @Transactional
    public void updateImage(Question question) { questionRepository.updateImage(question); }

    //질문 삭제 (댓글도 같이 삭제)
    @Transactional
    public void delete(Long id) {
        questionRepository.deleteQuestion(id);
    }

    public Question findOne(Long id) {
        Question question = questionRepository.findOne(id);
        return question;
    }

    //질문 전체 조회
    public List<Object[]> readAll() {
        return questionRepository.readAll();
    }

    //질문 상세 조회
    public Question readOne(Long id) {
        Question question = questionRepository.readOne(id);
        return question;
    }

    public List<String> readAllImage() {
        return questionRepository.readAllImage();
    }


}
