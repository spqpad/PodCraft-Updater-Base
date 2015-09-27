package com.podcraft;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Szabolcs Pásztor on 9/27/2015.
 */
public class ContentsSet {

    public static Set<UniqueFile> generate(File directory) {
        HashSet<UniqueFile> set = new HashSet<>(500);
        File[] files = directory.listFiles();
        try {
            FileInputStream fis = null;
            assert files != null;
            for (File file : files) {
                if (file.isDirectory()) {
                    set.addAll(generate(file));
                } else {
                    fis = new FileInputStream(file);
                    String name = file.getPath();
                    String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
                    fis.close();
                    //System.out.println("" + name + " " + md5);
                    UniqueFile item = new UniqueFile(name, md5);
                    set.add(item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    public static Set<UniqueFile> readFromJson(String json) {
        HashSet<UniqueFile> set = new HashSet<>(500);
        JSONParser parser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) parser.parse(json);
            JSONArray files = (JSONArray) obj.get("files");
            files.forEach(o -> {
                JSONObject x = (JSONObject) o;
                set.add(new UniqueFile((String) x.get("qualifiedName"), (String) x.get("md5")));
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return set;
    }

}
