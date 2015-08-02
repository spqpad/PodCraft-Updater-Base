package com.podcraft;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;

public class Main {

    private static class Version {

        String name;
        Long code;

        public Version(String name, Long code) {
            this.name = name;
            this.code = code;
        }

        @Override
        public String toString() {
            return "version(" + name +", "+code+")";
        }
    }

    private static Version current;
    private static Version newest;
    private static String newestJson;

    public static void main(String args[])
    {
        try {
            StringBuilder jsonBuilder = new StringBuilder();
            Files.lines(new File("current.json").toPath())
                    .forEach( jsonBuilder::append );
            current = decodeJson(jsonBuilder.toString());
//            System.out.println("current = " + current);
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Syncing newest.json...");
        newestJson = getHTML(API.NEWEST_URI);
        newest = decodeJson(newestJson);
//        System.out.println("newest = " + newest);

        if (newest.code > current.code) {
            System.out.println("Time to update to " + newest.name + ", current is " + current.name);
            update();
            updateCurrentJsonFile();
            //launchLauncher();
        } else {
            System.out.println("Launcher is up-to-date with version " + current.name);
            System.exit(0);
        }

    }


    private static void update() {
        try {
            System.out.println("Downloading new version...");
            URL website = new URL(API.JAR_URI);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream("PodCraft.jar");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void updateCurrentJsonFile() {
        System.out.println("Updating current.json...");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("current.json", "UTF-8");
            writer.println(newestJson);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private static Version decodeJson(String json) {
        Version version = null;
        JSONParser parser = new JSONParser();
        try{
            JSONObject obj = (JSONObject) parser.parse(json);
            String versionName = (String) obj.get("version_name");
            Long versionCode = (Long) obj.get("version_code");
            version = new Version(versionName, versionCode);
        }catch(ParseException e){
            System.out.println(e);
        }
        return version;
    }


    public static String getHTML(String urlToRead) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        StringBuilder result = new StringBuilder();
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private static void launchLauncher() {
        Process proc;
        String command = ".\\launch-no-update.bat";
        try  {
            System.out.println("Starting up new launcher");
            proc = Runtime.getRuntime().exec(command);
//            System.exit(0);
            InputStream in = proc.getInputStream();
            InputStream err = proc.getErrorStream();
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(in));
            String s;
            while((s = inputStream.readLine()) != null)
            {
                System.out.println(s);
            }
            BufferedReader errStream = new BufferedReader(new InputStreamReader(err));
            String s2;
            while((s2 = errStream.readLine()) != null)
            {
                System.err.println(s2);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
