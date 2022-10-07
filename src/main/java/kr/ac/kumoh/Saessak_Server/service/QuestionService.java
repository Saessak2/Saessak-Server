//package kr.ac.kumoh.Saessak_Server.service;
//
//import kr.ac.kumoh.Saessak_Server.domain.Question;
//import kr.ac.kumoh.Saessak_Server.domain.User;
//import kr.ac.kumoh.Saessak_Server.repository.QuestionRepository;
//import kr.ac.kumoh.Saessak_Server.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class QuestionService {
//
//    private final QuestionRepository questionRepository;
//    private final UserRepository userRepository;
//
//    //질문 등록
//    @Transactional
//    public Long create(Question q) {
//        questionRepository.createQuestion(q);
//        return q.getId();
//    }
//
//    //질문 수정
//    public void update(Question q) {
//        questionRepository.updateQuestion(q);
//    }
//
//    //질문 삭제
//    public void delete(Long id) {
//        questionRepository.deleteQuestion(id);
//    }
//
//    //질문 조회
//
//}
