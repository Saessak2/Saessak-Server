package kr.ac.kumoh.Saessak_Server.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Image {

    private String fileName;
    private String fileOriName;
    private String fileUrl;

    public Image(String fileName, String fileOriName, String fileUrl) {
        this.fileName = fileName;
        this.fileOriName = fileOriName;
        this.fileUrl = fileUrl;
    }

}
