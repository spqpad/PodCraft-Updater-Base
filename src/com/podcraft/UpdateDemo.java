package com.podcraft;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Szabolcs Pásztor on 9/27/2015.
 */
public class UpdateDemo {

    public static void main(String[] args) {

        Set<UniqueFile> localFiles = ContentsSet.generate(new File("."));

        print(localFiles);
        System.out.println();


        Set<UniqueFile> serverFiles = ContentsSet.readFromJson("{\n" +
                " \n" +
                "  \"files\":[\n" +
                "    {\"qualifiedName\":\".\\\\a.txt\", \"md5\":\"56b5c76820a22861686d38a95e51c4ce\"}, \n" +
                "    {\"qualifiedName\":\".\\\\b.txt\", \"md5\":\"d41d8cd98f00b204e9800998ecf8427e\"}, \n" +
                "    {\"qualifiedName\":\".\\\\e.txt\", \"md5\":\"3723ba491b0807da613b9e87c2d21f82\"},\n" +
                "    {\"qualifiedName\":\".\\\\f1\\\\a.txt\", \"md5\":\"c4c8bd3f5a0d686f2690aadfe5d04cf8\"},\n" +
                "    {\"qualifiedName\":\".\\\\f1\\\\g1\\\\a.txt\", \"md5\":\"6831353a7e34c825b891d95fc8810a0c\"},\n" +
                "    {\"qualifiedName\":\".\\\\f1\\\\g2\\\\xx.txt\", \"md5\":\"981e8cc039b33d06dc4916178f50c50d\"},\n" +
                "    {\"qualifiedName\":\".\\\\f2\\\\h1\\\\a.txt\", \"md5\":\"d41d8cd98f00b204e9800998ecf8427e\"}\n" +
                "  ]\n" +
                "  \n" +
                "}");
        print(serverFiles);
        System.out.println();


        Set<UniqueFile> changedFiles = new HashSet<>(serverFiles);
        changedFiles.removeAll(localFiles);
        System.out.println("Changed:");
        print(changedFiles);
        System.out.println();


        Set<UniqueFile> filesToRemove = new HashSet<>(localFiles);
        filesToRemove.removeAll(serverFiles);
        System.out.println("To remove:");
        print(filesToRemove);

//        FileSystems.getDefault().getPath()

        filesToRemove.stream().forEach(
                (x) -> {
                    Path path = FileSystems.getDefault().getPath(x.getQualifiedName());
                    try {
                        Files.delete(path);
                        System.out.println("removed " + x.getQualifiedName());
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                }
        );


    }

    private static <T> void print (Set<T> set) {
        for(T t : set) {
            System.out.println(t);
        }
    }

}
