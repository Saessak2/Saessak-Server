package kr.ac.kumoh.Saessak_Server.domain;

import java.time.LocalDate;

public class EntityUtility {

    public static LocalDate getLocalDateFromStr(String inDate){
        String reg = " ";
        if(inDate.contains("-"))
            reg = "-";
        else if(inDate.contains("\\."))
            reg = ".";
        else if(inDate.contains("/"))
            reg = "/";

        String[] tmpArr = inDate.split(reg);
        return LocalDate.of(Integer.parseInt(tmpArr[0]),
                Integer.parseInt(tmpArr[1]),
                Integer.parseInt(tmpArr[2]));
    }

}
