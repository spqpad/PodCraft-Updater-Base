package com.podcraft;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Szabolcs Pásztor on 9/27/2015.
 */
public class UpdateDemo {

    public static void main(String[] args) {

        Set<UniqueFile> localFiles = ListFiles.listContents(new File("."));
//        Set<UniqueFile> localFiles = new HashSet<>(100);
//        localFiles.add(new UniqueFile(".\\a.txt", "d41d8cd98f00b204e9800998ecf8427e"));
//        localFiles.add(new UniqueFile(".\\b.txt", "d41d8cd98f00b204e9800998ecf8427e"));


        print(localFiles);
        System.out.println();

        Set<UniqueFile> serverFiles = new HashSet<>(100);
        serverFiles.add(new UniqueFile(".\\a.txt", "56b5c76820a22861686d38a95e51c4ce"));
        serverFiles.add(new UniqueFile(".\\b.txt", "d41d8cd98f00b204e9800998ecf8427e"));
        serverFiles.add(new UniqueFile(".\\e.txt", "3723ba491b0807da613b9e87c2d21f82"));
        serverFiles.add(new UniqueFile(".\\f1\\a.txt", "c4c8bd3f5a0d686f2690aadfe5d04cf8"));
        serverFiles.add(new UniqueFile(".\\f1\\g1\\a.txt", "6831353a7e34c825b891d95fc8810a0c"));
        serverFiles.add(new UniqueFile(".\\f1\\g2\\xx.txt", "981e8cc039b33d06dc4916178f50c50d"));
        serverFiles.add(new UniqueFile(".\\f2\\h1\\a.txt", "d41d8cd98f00b204e9800998ecf8427e"));

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

    }

    private static <T> void print (Set<T> set) {
        for(T t : set) {
            System.out.println(t);
        }
    }

}
