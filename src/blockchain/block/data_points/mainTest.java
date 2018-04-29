package blockchain.block.data_points;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class mainTest {
    public static void main(String[] args) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        System.out.println(timeStamp);

        byte[] bytes = timeStamp.getBytes(StandardCharsets.UTF_8);

        String str = new String(bytes);

        System.out.println(str);
/*
        trans1.addData("hej trans1");
        smart1.addData("hej smart1");
        trans1.addData("hoowee");

        System.out.println(trans1.getString());
        System.out.println(smart1.getString());*/
    }
}
