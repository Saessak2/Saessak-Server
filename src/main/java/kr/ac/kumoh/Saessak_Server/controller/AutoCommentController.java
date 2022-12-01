package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.domain.AutoComment;
import kr.ac.kumoh.Saessak_Server.domain.dto.AutoCommentDTO;
import kr.ac.kumoh.Saessak_Server.service.AutoCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AutoCommentController {

    private final AutoCommentService autoCommentService;

    //자동댓글 조회
    @GetMapping("auto/readComment/{id}")
    public @ResponseBody ResponseEntity<List<Map<String, Object>>> readComment(@PathVariable("id") Long id) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Object[]> comments = autoCommentService.readAutoCommentList(id);

        if(comments.toArray().length == 0) {
        }
        else {
            Map<String, Object> map = new HashMap<>();
            Object[] comment = (Object[]) comments.toArray()[0];
            map.put("link", comment[0]);
            map.put("title", comment[1]);
            map.put("similarity", comment[2]);
            map.put("answer", comment[3]);

            list.add(map);
        }

        return ResponseEntity.ok(list);
    }
}
