//package kr.ac.kumoh.Saessak_Server.domain;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class PlantDict1 {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false, insertable = false, updatable = false)
//    private Long id;
//
//    private String plantEngName;  //식물영 명
//    private String plantName;  //유통명(식물명)
//    private String manageLevel;  //관리 수준
//    private String winterMinTemp;  //겨울 최저 온도
//    private String humidity;  //습도
//    private String fertilizer;  //비료 정보
//    private String soilInfo;  //토양 정보
//    private String waterCycleSpring;  //물주기 봄
//    private String waterCycleSummer;  //물주기 여름
//    private String waterCycleAutumn;  //물주기 가을
//    private String waterCycleWinter;  //물주기 겨울
//    private String speclManageInfo;  //특별 관리 정보
//    private String prpgtMth;  //번식방법
//    private String lighthDemand;  //광요구도
//    private String pest;  //병충해
//    private String imgUrl;  //이미지
//
//}
