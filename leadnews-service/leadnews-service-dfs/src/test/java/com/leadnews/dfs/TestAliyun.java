package com.leadnews.dfs;

import com.leadnews.common.util.ali.AliGreenScanner;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.dfs
 */
@SpringBootTest
public class TestAliyun {

    @Autowired
    private AliGreenScanner aliGreenScanner;

    @Test
    public void testText() throws Exception {
        Map<String, String> stringStringMap = aliGreenScanner.greenTextScan("便宜大甩卖，冰毒2元一斤，买一斤送一斤大麻");
        System.out.println(stringStringMap);
    }

    @Test
    public void testImage() throws Exception {
        List<byte[]> list = new ArrayList<>();
        byte[] bytes = IOUtils.toByteArray(new FileInputStream("E:\\leadnews_courses\\头条项目\\day06\\讲义\\assets\\1584967491149.png"));
        list.add(bytes);
        Map<String, String> stringStringMap = aliGreenScanner.imageScan(list);
        System.out.println("resultMap==============" + stringStringMap);
    }
}
