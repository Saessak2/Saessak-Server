package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.domain.Diary;
import kr.ac.kumoh.Saessak_Server.domain.Image;
import kr.ac.kumoh.Saessak_Server.domain.dto.DiaryDTO;
import kr.ac.kumoh.Saessak_Server.service.DiaryService;
import kr.ac.kumoh.Saessak_Server.service.MyPlantService;
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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;
    private final MyPlantService myPlantService;

    //일기 등록
    @PostMapping("diaries/createDiary")
    public @ResponseBody ResponseEntity createDiary(@RequestBody DiaryDTO diaryDTO) {
        Diary diary = new Diary();
        diary.setDate(diaryDTO.getDate());
        diary.setTime(diaryDTO.getTime());
        diary.setContent(diaryDTO.getContent());
        diary.setWeather(diaryDTO.getWeather());
        diary.setCond(diaryDTO.getCond());
        diary.setActivity1(diaryDTO.getActivity1());
        diary.setActivity2(diaryDTO.getActivity2());
        diary.setActivity3(diaryDTO.getActivity3());
        diary.setUser_id(diaryDTO.getUser_id());
        diary.setMyplant_id(diaryDTO.getMyplant_id());

        diaryService.create(diary);

        Diary diary1 = diaryService.findOne(diary.getId());
        DiaryDTO diaryDTO1 = new DiaryDTO();

        diaryDTO1.setId(diary1.getId());
        diaryDTO1.setDate(diary1.getDate());
        diaryDTO1.setTime(diary1.getTime());
        diaryDTO1.setContent(diary1.getContent());
        diaryDTO1.setWeather(diary1.getWeather());
        diaryDTO1.setCond(diary1.getCond());
        diaryDTO1.setContent(diary1.getContent());
        diaryDTO1.setWeather(diary1.getWeather());
        diaryDTO1.setActivity1(diary1.getActivity1());
        diaryDTO1.setActivity2(diary1.getActivity2());
        diaryDTO1.setActivity3(diary1.getActivity3());
        diaryDTO1.setUser_id(diary1.getUser_id());
        diaryDTO1.setMyplant_id(diary1.getMyplant_id());

        List<DiaryDTO> list = new ArrayList<>();
        list.add(diaryDTO1);

        return ResponseEntity.ok(list);
    }

    //일기 수정
    @PutMapping("diaries/updateDiary/{id}")
    public void updateDiary(@RequestBody DiaryDTO diaryDTO, @PathVariable("id") Long id) {
        Diary diary = diaryService.findOne(id);

        diary.setContent(diaryDTO.getContent());
        diary.setWeather(diaryDTO.getWeather());
        diary.setCond(diaryDTO.getCond());
        diary.setActivity1(diaryDTO.getActivity1());
        diary.setActivity2(diaryDTO.getActivity2());
        diary.setActivity3(diaryDTO.getActivity3());

        diaryService.update(diary);
    }

    //일기 삭제
    @DeleteMapping("diaries/deleteDiary/{id}")
    public void deleteDiary(@PathVariable("id") Long id) {
        diaryService.delete(id);
    }

    //일기 전체 조회 //유저 id 받기
    @GetMapping("diaries/readDiaryList/{id}")
    public @ResponseBody ResponseEntity<List<Diary>> readDiaryList(@PathVariable("id") Long id) {
        List<Diary> diaryList = diaryService.readDiaryList(id);

        return ResponseEntity.ok(diaryList);
    }

    //일기 상세 조회 //일기 id 받기
    @GetMapping("diaries/readDiaryDetail/{id}")
    public @ResponseBody ResponseEntity<Diary> readDiaryDetail(@PathVariable("id") Long id) {
        Diary diary = diaryService.readDiaryDetail(id);

        return ResponseEntity.ok(diary);
    }

    //식물별 일기 조회
    @GetMapping("diaries/readDiaryByPlant/{id}")
    public @ResponseBody ResponseEntity<List<Diary>> readDiaryByPlant(@PathVariable("id") Long id) {
        List<Diary> diaryList = diaryService.readDiaryByPlant(id);

        return ResponseEntity.ok(diaryList);
    }

    //식물별 일기 조회 (최신 3개) //내 식물 id 받기
    @GetMapping("diaries/readDiaryByRecent/{id}")
    public @ResponseBody ResponseEntity<List<Diary>> readDiaryByRecent(@PathVariable("id") Long id) {
        List<Diary> diaryList = diaryService.readDiaryByRecent(id);

        return ResponseEntity.ok(diaryList);
    }

    //이미지 등록
    @PostMapping("diaries/createImage")
    public void uploadFile(@RequestPart(value = "img_path") MultipartFile files) throws IOException {
        Diary diary = new Diary();

        //
        String sourceFileName = files.getOriginalFilename();

        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

        FilenameUtils.removeExtension(sourceFileName);

        File destinationFile;
        String destinationFileName;
        String fileUrl = "C:\\Users\\DeepLearning_4\\Desktop";

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

        diary.setId(id);
        diary.setImage(file);
        diary.setImg(true);

        diaryService.updateImage(diary);
    }

    //이미지 조회
    @GetMapping(value = "diaries/readImage/{id}")
    public ResponseEntity<Resource> readImageDiary(@PathVariable("id") Long id) {
        Diary diary = diaryService.findOne(id);
        HttpHeaders header = new HttpHeaders();

        try {
            boolean isExist = true;
            String fileName = diary.getImage().getFileName();
            String path = "C:\\Users\\DeepLearning_4\\Desktop";
            FileSystemResource resource = new FileSystemResource(path+fileName);

            Path filePath = null;
            filePath = Paths.get(path+fileName);
            header.add("Content-Type", Files.probeContentType(filePath));

            return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Resource>((Resource) null, HttpStatus.OK);
        }
    }

}
