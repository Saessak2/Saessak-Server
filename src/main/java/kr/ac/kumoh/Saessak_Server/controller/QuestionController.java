package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.domain.Image;
import kr.ac.kumoh.Saessak_Server.domain.Question;
import kr.ac.kumoh.Saessak_Server.domain.User;
import kr.ac.kumoh.Saessak_Server.dto.QuestionDTO;
import kr.ac.kumoh.Saessak_Server.service.CommentService;
import kr.ac.kumoh.Saessak_Server.service.QuestionService;
import kr.ac.kumoh.Saessak_Server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        question.setCategory(questionDTO.getCategory());
        question.setUser_id(user);

        questionService.create(question);

        Question question2 = questionService.readOne(question.getId());
        QuestionDTO questionDTO2 = new QuestionDTO();

        questionDTO2.setId(question2.getId());
        questionDTO2.setUser_id(question2.getUser_id().getId());
        questionDTO2.setCategory(question2.getCategory());
        questionDTO2.setContent(question2.getContent());
        questionDTO2.setDateTime(question2.getCreate_date());
        questionDTO2.setUserName(question2.getUser_id().getUserName());

        Long temp = Long.valueOf(commentService.CommentCnt(questionDTO2.getId()));
        String count = Long.toString(temp);
        questionDTO2.setCommentCnt(count);

        List<QuestionDTO> list = new ArrayList<>();
        list.add(questionDTO2);

        return ResponseEntity.ok(list);

    }

    //질문 수정
    @PutMapping("questions/updateQuestion/{id}")
    public @ResponseBody ResponseEntity updateQuestion(@RequestBody QuestionDTO questionDTO, @PathVariable("id") Long id) {
        Question question = questionService.findOne(id);

        question.setContent(questionDTO.getContent());
        question.setCategory(questionDTO.getCategory());

        questionService.update(question);

        Question question2 = questionService.readOne(question.getId());
        QuestionDTO questionDTO2 = new QuestionDTO();

        questionDTO2.setId(questionDTO2.getId());
        questionDTO2.setUser_id(question2.getUser_id().getId());
        questionDTO2.setCategory(question2.getCategory());
        questionDTO2.setContent(question2.getContent());
        questionDTO2.setDateTime(question2.getCreate_date());
        questionDTO2.setUserName(question2.getUser_id().getUserName());

        Long temp = Long.valueOf(commentService.CommentCnt(questionDTO2.getId()));
        String count = Long.toString(temp);
        questionDTO2.setCommentCnt(count);

        List<QuestionDTO> list = new ArrayList<>();
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
            map.put("category", question[3]);
            map.put("user_id", question[4]);
            map.put("userName", question[5]);

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

        int temp = commentService.CommentCnt(question.getId());
        String count = Integer.toString(temp);
        questionDTO.setCommentCnt(count);

        List<QuestionDTO> list = new ArrayList<>();
        list.add(questionDTO);

        return ResponseEntity.ok(list);
    }

    //이미지 등록
    @PostMapping("questions/createImage")
    public void uploadFile(@RequestPart(value = "img_path") MultipartFile files) throws IOException {
        Question question = new Question();

        //
        String sourceFileName = files.getOriginalFilename();

        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

        FilenameUtils.removeExtension(sourceFileName);

        File destinationFile;
        String destinationFileName;
        String fileUrl = "/Users/seominjeong/Desktop/3학년 2학기/창융/img/";

        do {
            destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
            destinationFile = new File(fileUrl + destinationFileName);
        } while (destinationFile.exists());

        destinationFile.getParentFile().mkdirs();
        files.transferTo(destinationFile);

        Image file = new Image(destinationFileName, sourceFileName, fileUrl);
        int count = 0;
        String temp = sourceFileName.substring(0, count + 1);
        while(true) {
            count++;
            if(sourceFileName.substring(count, count + 1).equals(".")) {
                break;
            }
            temp += sourceFileName.substring(count, count + 1);
        }
        Long id = Long.valueOf(temp);
        System.out.println(id);

        question.setId(id);
        question.setImage(file);

        questionService.updateImage(question);

    }

    //이미지 조회
    @GetMapping(value = "questions/readImage/{id}")
    public ResponseEntity<Resource> readImageQuestion(@PathVariable("id") Long id) {
        Question question = questionService.readOne(id);
        String fileName = question.getImage().getFileName();

        try {
            String path = "/Users/seominjeong/Desktop/3학년 2학기/창융/img/";
            FileSystemResource resource = new FileSystemResource(path+fileName);

            HttpHeaders header = new HttpHeaders();
            Path filePath = null;
            filePath = Paths.get(path+fileName);
            header.add("Content-Type", Files.probeContentType(filePath));

            return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);

        } catch (Exception e) {

            return null;
        }
    }

    @GetMapping("questions/readPage")
    public @ResponseBody ResponseEntity<List<Map<String, Object>>> readPage(Long cursorId, Integer size) {

        List<Map<String, Object>> list = new ArrayList<>();
        List<Object[]> questionList = questionService.readAll();

        for(int i = 0; i < questionList.toArray().length; i++) {
            Map<String, Object> map = new HashMap<>();
            Object[] question = (Object[]) questionList.toArray()[i];
            map.put("id", question[0]);
            map.put("content", question[1]);
            map.put("dateTime", question[2]);
            map.put("category", question[3]);
            map.put("user_id", question[4]);
            map.put("userName", question[5]);

            Long temp = Long.valueOf(commentService.CommentCnt((Long) question[0]));
            String count = Long.toString(temp);
            map.put("commentCnt", count);

            list.add(map);
        }


        return ResponseEntity.ok(list);
    }

}