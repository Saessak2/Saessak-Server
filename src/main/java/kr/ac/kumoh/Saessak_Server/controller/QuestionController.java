package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.domain.Question;
import kr.ac.kumoh.Saessak_Server.domain.User;
import kr.ac.kumoh.Saessak_Server.dto.QuestionDTO;
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
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;
    private final CommentService commentService;

    //질문 등록
    @PostMapping("questions/createQuestion")
    public @ResponseBody ResponseEntity createQuestion(@RequestBody QuestionDTO questionDTO) {
        Question question = new Question();
        User user = userService.findOne(questionDTO.getUser_id());

        question.setContent(questionDTO.getContent());
        String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd HH:mm"));
        question.setCreate_date(formatDate);
        question.setImg_path(questionDTO.getImg_path());
        question.setCategory(questionDTO.getCategory());
        question.setUser_id(user);

        questionService.create(question);

        Question question2 = questionService.readOne(question.getId());
        QuestionDTO questionDTO2 = new QuestionDTO();

        questionDTO2.setUser_id(question2.getUser_id().getId());
        questionDTO2.setCategory(question2.getCategory());
        questionDTO2.setContent(question2.getContent());
        questionDTO2.setDateTime(question2.getCreate_date());
        questionDTO2.setUserName(question2.getUser_id().getUserName());
        questionDTO2.setImg_path(question2.getImg_path());
        List<Object> list = new ArrayList<>();
        list.add(questionDTO2);

        return ResponseEntity.ok(list);

    }

    //질문 수정
    @PutMapping("questions/updateQuestion/{id}")
    public @ResponseBody ResponseEntity<List<Object>> updateQuestion(@RequestBody QuestionDTO questionDTO, @PathVariable("id") Long id) {
        Question question = questionService.findOne(id);

        question.setContent(questionDTO.getContent());
        question.setCategory(questionDTO.getCategory());
        question.setImg_path(questionDTO.getImg_path());

        questionService.update(question);

        Question question2 = questionService.readOne(question.getId());
        QuestionDTO questionDTO2 = new QuestionDTO();


        questionDTO2.setUser_id(question2.getUser_id().getId());
        questionDTO2.setCategory(question2.getCategory());
        questionDTO2.setContent(question2.getContent());
        questionDTO2.setDateTime(question2.getCreate_date());
        questionDTO2.setUserName(question2.getUser_id().getUserName());
        questionDTO2.setImg_path(question2.getImg_path());
        List<Object> list = new ArrayList<>();
        list.add(questionDTO2);

        return ResponseEntity.ok(list);
    }

    //질문 삭제
    @DeleteMapping("questions/deleteQuestion/{id}")
    public void deleteQuestion(@PathVariable("id") Long id) {
        questionService.delete(id);
    }

    //질문 전체 조회
    @GetMapping("questions/readAll")
    public @ResponseBody ResponseEntity<List<Map<String, Object>>> readAll() {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Object[]> questionList = questionService.readAll();

        for(int i = 0; i < questionList.toArray().length; i++) {
            Map<String, Object> map = new HashMap<>();
            Object[] question = (Object[]) questionList.toArray()[i];
            map.put("id", question[0]);
            map.put("content", question[1]);
            map.put("dateTime", question[2]);
            map.put("img_path", question[3]);
            map.put("category", question[4]);
            map.put("user_id", question[5]);
            map.put("userName", question[6]);

            Long temp = Long.valueOf(commentService.CommentCnt((Long) question[0]));
            String count = Long.toString(temp);
            map.put("commentCnt", count);

            list.add(map);
        }

        return ResponseEntity.ok(list);
    }

    //질문 상세 조회 (댓글 리스트도 같이 조회)
    @GetMapping("questions/readOne/{id}")
    public @ResponseBody ResponseEntity readOne(@PathVariable("id") Long id) {
        Question question = questionService.readOne(id);
        QuestionDTO questionDTO = new QuestionDTO();

        questionDTO.setId(question.getId());
        questionDTO.setUser_id(question.getUser_id().getId());
        questionDTO.setCategory(question.getCategory());
        questionDTO.setContent(question.getContent());
        questionDTO.setDateTime(question.getCreate_date());
        questionDTO.setUserName(question.getUser_id().getUserName());
        questionDTO.setImg_path(question.getImg_path());

        int temp = commentService.CommentCnt(question.getId());
        String count = Integer.toString(temp);
        questionDTO.setCommentCnt(count);

        List<QuestionDTO> list = new ArrayList<>();
        list.add(questionDTO);
//        questionDTO.setDateTime(question.getCreate_date());

        //댓글 리스트 담기
//        List<Map<String, Object>> list = new ArrayList<>();
//        List<Object[]> comments = commentService.readCommentList(id);
//
//        for(int i = 0; i < comments.toArray().length; i++) {
//            Map<String, Object> map = new HashMap<>();
//            Object[] comment = (Object[]) comments.toArray()[i];
//            map.put("id", comment[0]);
//            map.put("content", comment[1]);
//            map.put("dateTime", comment[2]);
//            map.put("user_id", comment[3]);
//            map.put("userName", comment[4]);
//            questionDTO.getCommentList().add(map);
//        }

        return ResponseEntity.ok(list);
    }
}
