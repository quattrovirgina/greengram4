package com.green.greengram4.common;

import com.green.greengram4.common.MyFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@Import({MyFileUtils.class})
@TestPropertySource(properties = {
        "file.dir=C:/Users/Administrator",
})

@Component
public class MyFileUtilsTest {

    @Autowired
    private MyFileUtils myFileUtils;

    @Test
    public void makeFolderTest() {
        String path = "/bumblebee";
        File preFolder = new File(myFileUtils.getUploadPrefixPath(), path);
        assertEquals(false, preFolder.exists());

        String newPath = myFileUtils.makeFolders(path);
        File newFolder = new File(newPath);
        assertEquals(preFolder.getAbsolutePath(), newFolder.getAbsolutePath());
        assertEquals(preFolder.getAbsolutePath(), newFolder.getAbsolutePath());
        assertEquals(true, newFolder.exists());
    }

    @Test
    public void getRandomFileNmTest() {
        String fileNm = myFileUtils.getRandomFiling();
        System.out.println("fileNm : " + fileNm);
        assertNotNull(fileNm);
        assertNotEquals("", fileNm);
    }

    @Test
    public void getExtTest() {
        String fileNm = "abc.efg.eee.jpg";

        String ext = myFileUtils.getExt(fileNm);
        assertEquals(".jpg", ext);

        String fileNm2 = "jjj-asdfasdf.png";
        String ext2 = myFileUtils.getExt(fileNm2);
        assertEquals(".png", ext2);
    }

    @Test
    public void getRandomFileNm2() {
        String fileNm1 = "내래 시라소니야.jpeg";
        String fileNmReverse = myFileUtils.getRandomFiling2(fileNm1);
        System.out.println("result: " + fileNmReverse);
    }

    @Test
    public void getRandomFileNm3() {
        String fileNm2 = "남조선 아새끼들.jpeg";
    }

    @Test
    public void transferToTest() throws Exception {
        String filename = "delete delfeedpics.png";
        String FilePath = "C:/Users/Administrator/Desktop/요약한거/" + filename;
        FileInputStream fis = new FileInputStream(FilePath);
        MultipartFile mf = new MockMultipartFile("pic", filename, "png", fis);

        String saveFileNm = myFileUtils.transferTo(mf, "/feed/10");
        log.info("myFileUtils = {}", saveFileNm);

    }

}