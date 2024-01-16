package com.green.greengram4.common;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Data
@Component
public class MyFileUtils {

    @Value("${file.dir}")
    private final String uploadPrefixPath;

    public MyFileUtils(@Value("${file.dir}") String uploadPrefixPath) {
        this.uploadPrefixPath = uploadPrefixPath;
    }

    // 폴더 만들기
    public String makeFolders(String path) {
        File folder = new File(uploadPrefixPath, path);
        folder.mkdirs();
        return folder.getAbsolutePath();
        // getAbsolutePath: 절대주소
        // 절대주소는 주소 전체를 말하는것(D:programfiles/xxxxx/yyyy.exe)
    }

    // 랜덤 파일명 만들기
    public String getRandomFiling() {
        return UUID.randomUUID().toString();
    }

    // 확장자 얻어오기
    public String getExt(String fileNm) {
        File filing = new File(uploadPrefixPath, fileNm);
        String extension = filing.getName();
        String realextention = extension.substring(extension.lastIndexOf("."));

        return realextention;
    }

    // 확장자 포함해서 랜덤 파일명 만들기
    public String getRandomFiling2(String nameis) {
        /* File xfile = new File(UUID.randomUUID().toString(), nameis);
        String extension = xfile.getName();
        String realextension = extension.substring(extension.lastIndexOf("."));
        return realextension; */

        return getRandomFiling() + getExt(nameis);
    }

    // 확장자 사용해서 랜덤 파일명 만들기(multipartfile)
    public String getRandomFiling3(MultipartFile mult) {
        String fileNm = mult.getOriginalFilename();
        return getRandomFiling2(fileNm);
    }

    // 메모리에 있는 내용을 파일로 옮기는 메소드
    public String transferTo(MultipartFile mf, String target) {
        String fileNa = getRandomFiling3(mf);
        String folderPath = makeFolders(target);
        File saveFile = new File(folderPath, fileNa);
        try {
            mf.transferTo(saveFile); // 램에 올라간 파일을 saveFile 쪽으로 옮김.
            return fileNa;
            // return saveFile.getAbsoultePath(D:\home\download\diaryPics\35\...\.png);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delFiles(String folderpath) { // 폴더 아래에 폴더 및 파일 삭제하되 보내는 폴더는 삭제하지 않음
        File folder = new File(uploadPrefixPath, folderpath);
        if(folder.exists()) {
            File[] files = folder.listFiles(); //파일 배열

            for(File file : files) {
                if(file.isDirectory()) {
                    String absolutepath = file.getAbsolutePath();
                    String targetFolder = absolutepath.replace(uploadPrefixPath, "");
                    delFiles(targetFolder);
                }
                file.delete();
            }
        }
    }
}
