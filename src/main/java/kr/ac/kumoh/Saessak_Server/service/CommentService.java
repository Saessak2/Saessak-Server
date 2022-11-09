package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.Comment;
import kr.ac.kumoh.Saessak_Server.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;


    //댓글 등록
    @Transactional
    public void create(Comment comment) {
        commentRepository.createComment(comment);
    }

    //댓글 수정
    @Transactional
    public void update(Comment comment) {
        commentRepository.updateComment(comment);
    }

    //댓글 삭제
    @Transactional
    public void delete(Long id) {
        commentRepository.deleteComment(id);
    }

    public Comment findOne(Long id) {
        return commentRepository.findOne(id);
    }

    //댓글 리스트 조회
    public List<Object[]> readCommentList(Long question_id) {
        return commentRepository.readCommentList(question_id);
    }

    public int CommentCnt(Long question_id) {
        return commentRepository.CommentCnt(question_id);
    }
}
