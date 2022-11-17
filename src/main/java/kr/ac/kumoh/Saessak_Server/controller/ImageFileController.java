package kr.ac.kumoh.Saessak_Server.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

@RestController
@RequestMapping("/image")
public class ImageFileController {

    private final String FILE_PATH = "C:/Users/mare1/workspace/Saessak-Server/src/main/resources/images/";

    @PostMapping()
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file){


        Date date = new Date();
        StringBuilder sb = new StringBuilder();
        String pathh;
        if (file.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        else {
            sb.append(date.getTime()).append(file.getOriginalFilename());
            pathh =FILE_PATH + sb;
            File path = new File(pathh);
            try {
                file.transferTo(path);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok(pathh);
    }

    @GetMapping(value = "/{image-name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("image-name") String imgName) throws IOException {
        InputStream imgStream = new FileInputStream(FILE_PATH + imgName);
        byte[] imgByteArr = IOUtils.toByteArray(imgStream);
        imgStream.close();
        return new ResponseEntity<>(imgByteArr, HttpStatus.OK);
    }

}
