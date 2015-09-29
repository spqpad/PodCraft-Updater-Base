package com.podcraft;

import edu.princeton.cs.algs4.Stopwatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Szabolcs Pásztor on 9/29/2015.
 */
public class GenerateContentsJson {

    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        File currentDir = new File("."); // current directory
        System.out.println(generateJsonFileList(currentDir));
//        System.out.println(stopwatch.elapsedTime());
    }

    public static String generateJsonFileList(File dir) {
        boolean first = true;
        StringBuilder json = new StringBuilder("{\n");
        json.append("\"files\":\n[\n");
        appendContentsList(dir, json, first);
        json.append("]\n");
        json.append("}");
        return String.valueOf(json);
    }

    public static void appendContentsList(File dir, final StringBuilder jsonToAppend, boolean isFirstElement) {
        File[] files = dir.listFiles();
        try {
            FileInputStream fis = null;
            for (File file : files) {
                if (file.isDirectory()) {
                    appendContentsList(file, jsonToAppend, isFirstElement);
                } else {
                    fis = new FileInputStream(file);
                    String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
                    fis.close();
                    // {"qualifiedName":".\\a.txt", "md5":"56b5c76820a22861686d38a95e51c4ce"},
                    if (!isFirstElement) {
                        jsonToAppend.append(",\n");
                    } else isFirstElement = false;
                    jsonToAppend.append(String.format("{\"qualifiedName\":\"%s\", \"md5\":\"%s\"}", file.getPath(), md5));
                    //System.out.println("" + file.getPath() + " " + md5);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
