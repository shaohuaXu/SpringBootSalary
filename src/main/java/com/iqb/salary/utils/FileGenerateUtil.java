package com.iqb.salary.utils;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * create by liujun 17/8/25下午6:03
 */
public class FileGenerateUtil {


    public static File writeup(List<String> contexts, String fileName, String fileUrl, boolean b) throws IOException {
        File file = new File(fileUrl, fileName);
        if (!file.isDirectory()) {
            FileOutputStream fileOutputStream = new FileOutputStream(file, b);
            ByteArrayInputStream byteArrayInputStream = null;
            for (String context : contexts) {
                //内存输入字节流
                byteArrayInputStream = new ByteArrayInputStream(context.getBytes());
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = byteArrayInputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                }
                fileOutputStream.write("\n".getBytes());
            }
            byteArrayInputStream.close();
            fileOutputStream.close();
        }
        return file;
    }

    public static File writeup2(List<String> contexts, String fileName, String fileUrl, boolean b) throws IOException {
        File file = new File(fileUrl, fileName);
        try {
            OutputStream out = new FileOutputStream(file);
            BufferedWriter rd = new BufferedWriter(new OutputStreamWriter(out, "GBK"));
            for (String context : contexts) {
                rd.write(context);
                rd.write("\n");
            }
            rd.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

}
