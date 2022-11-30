package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.domain.Comment;
import kr.ac.kumoh.Saessak_Server.domain.Question;
import kr.ac.kumoh.Saessak_Server.domain.User;
import kr.ac.kumoh.Saessak_Server.domain.dto.CommentDTO;
import kr.ac.kumoh.Saessak_Server.service.CommentService;
import kr.ac.kumoh.Saessak_Server.service.QuestionService;
import kr.ac.kumoh.Saessak_Server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final QuestionService questionService;
    private final UserService userService;

    //댓글 등록
    @PostMapping("comments/createComment")
    public @ResponseBody ResponseEntity<List<Map<String, Object>>> createComment(@RequestBody CommentDTO commentDTO) {
        Comment comment = new Comment();
        User user = userService.findOne(commentDTO.getUser_id());
        Question question = questionService.findOne(commentDTO.getQuestion_id());
        question.setAnswer_count(question.getAnswer_count()+1);

        comment.setContent(commentDTO.getContent());
        String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd HH:mm"));
        comment.setCreate_date(formatDate);
        comment.setQuestion_id(question);
        comment.setUser_id(user);

        commentService.create(comment);

        List<Map<String, Object>> list = new ArrayList<>();
        List<Object[]> comments = commentService.readCommentList(comment.getQuestion_id().getId());

        for(int i = 0; i < comments.toArray().length; i++) {
            Map<String, Object> map = new HashMap<>();
            Object[] o_comment = (Object[]) comments.toArray()[i];
            map.put("id", o_comment[0]);
            map.put("content", o_comment[1]);
            map.put("dateTime", o_comment[2]);
            map.put("user_id", o_comment[3]);
            map.put("userName", o_comment[4]);
            list.add(map);
        }

        return ResponseEntity.ok(list);
    }

    //댓글 수정
    @PutMapping("comments/updateComment/{id}")
    public @ResponseBody ResponseEntity<List<Map<String, Object>>> updateComment(@RequestBody CommentDTO commentDTO, @PathVariable("id") Long id) {
        Comment comment = commentService.findOne(id);
        comment.setContent(commentDTO.getContent());

        commentService.update(comment);

        List<Map<String, Object>> list = new ArrayList<>();
        List<Object[]> comments = commentService.readCommentList(comment.getQuestion_id().getId());

        for(int i = 0; i < comments.toArray().length; i++) {
            Map<String, Object> map = new HashMap<>();
            Object[] o_comment = (Object[]) comments.toArray()[i];
            map.put("id", o_comment[0]);
            map.put("content", o_comment[1]);
            map.put("dateTime", o_comment[2]);
            map.put("user_id", o_comment[3]);
            map.put("userName", o_comment[4]);
            list.add(map);
        }

        return ResponseEntity.ok(list);
    }

    //댓글 삭제
    @DeleteMapping("comments/deleteComment/{id}")
    public void deleteComment(@PathVariable("id") Long id) {
        commentService.delete(id);
        Comment comment = commentService.findOne(id);
        Question question = questionService.findOne(comment.getQuestion_id().getId());
        question.setAnswer_count(question.getAnswer_count()-1);
    }

    //댓글 리스트 조회 //질문 id 담기
    @GetMapping("comments/readOne/{id}")
    public @ResponseBody ResponseEntity<List<Map<String, Object>>> readOne(@PathVariable("id") Long id) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Object[]> comments = commentService.readCommentList(id);

        for(int i = 0; i < comments.toArray().length; i++) {
            Map<String, Object> map = new HashMap<>();
            Object[] comment = (Object[]) comments.toArray()[i];
            map.put("id", comment[0]);
            map.put("content", comment[1]);
            map.put("dateTime", comment[2]);
            map.put("user_id", comment[3]);
            map.put("userName", comment[4]);
            list.add(map);
        }

        return ResponseEntity.ok(list);
    }

}
