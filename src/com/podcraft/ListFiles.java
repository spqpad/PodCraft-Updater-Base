package com.podcraft;

import edu.princeton.cs.algs4.Stopwatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Szabolcs Pásztor on 9/27/2015.
 */
public class ListFiles {

    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        File currentDir = new File("."); // current directory
        displayDirectoryContents(currentDir);
        System.out.println(stopwatch.elapsedTime());
    }

    public static void displayDirectoryContents(File dir) {
        File[] files = dir.listFiles();
        try {
            FileInputStream fis = null;
            for (File file : files) {
                if (file.isDirectory()) {
                    //System.out.println("d " + file.getPath());
                    displayDirectoryContents(file);
                } else {
                    fis = new FileInputStream(file);
                    String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
                    fis.close();
                    //System.out.println("f:" + file.getPath() + " MD5:" + md5);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
