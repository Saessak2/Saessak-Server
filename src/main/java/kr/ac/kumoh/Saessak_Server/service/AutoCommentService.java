package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.AutoComment;
import kr.ac.kumoh.Saessak_Server.domain.dto.AutoCommentDTO;
import kr.ac.kumoh.Saessak_Server.repository.AutoCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AutoCommentService {

    private final AutoCommentRepository autoCommentRepository;

    //자동댓글 등록
    @Transactional
    public Long createAutoComment(AutoComment autoComment) {
        return autoCommentRepository.createAutoComment(autoComment);
    }

    public AutoComment findOne(Long id) {
        AutoComment autoComment = autoCommentRepository.findOne(id);
        return autoComment;
    }

    //자동댓글 조회
    public List<Object[]> readAutoCommentList(Long question_id) {
        return autoCommentRepository.readAutoCommentList(question_id);
    }

    //자동댓글 삭제
    public Long update(Long question_id) {
        return autoCommentRepository.update(question_id);
    }

}
