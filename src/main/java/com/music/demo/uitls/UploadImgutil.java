package com.music.demo.uitls;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class UploadImgutil {
    /**
     * 上传文件
     *
     * @param multipartFile
     * @return 文件存储路径
     */
    public static String upload(MultipartFile multipartFile) {
        // 文件存储位置，文件的目录要存在才行，可以先创建文件目录，然后进行存储
        //F:\JetBrains\Java\WebMusic_project\src\main\resources\publicImg
        String filePath = "F:/JetBrains/Java/WebMusic_project/src/main/resources/static/publicImg/" + multipartFile.getOriginalFilename();
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 文件存储
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
}
