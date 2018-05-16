package com.tipchou.sunshineboxiii.support;

import android.util.Log;

import com.tipchou.sunshineboxiii.ui.course.CourseBean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 由邵励治于2017/12/18创造.
 */

public class IOUtils {
    public static String fileToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        // 数组长度
        byte[] buffer = new byte[1024];
        // 初始长度
        int len;
        // 循环
        while ((len = inputStream.read(buffer)) != -1) {
            arrayOutputStream.write(buffer, 0, len);
        }
        return arrayOutputStream.toString();
    }

    public static String rebuildMarkdown(File materialFolder, CourseBean bean) {
        String markdownReplace = "[$1](" + materialFolder.getAbsolutePath() + "/$2)";
        final String markdown = bean.getContent().replaceAll("\\[(\\S+)]\\((\\S+)\\)", markdownReplace);
        Log.e("FUCK:Markdown content", markdown);
        return markdown;
    }
}
