package com.podcraft;

import edu.princeton.cs.algs4.Stopwatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Szabolcs Pásztor on 9/27/2015.
 */
public class DisplayContents {

    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        File currentDir = new File("."); // current directory
        displayFileList(currentDir);
        System.out.println(stopwatch.elapsedTime());
    }

    public static void displayFileList(File dir) {
        File[] files = dir.listFiles();
        try {
            FileInputStream fis = null;
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("dir  " + file.getPath());
                    displayFileList(file);
                } else {
                    fis = new FileInputStream(file);
                    String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
                    fis.close();
                    System.out.println("file " + file.getPath() + " " + md5);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
