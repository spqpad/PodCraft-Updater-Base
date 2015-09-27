package com.podcraft;

/**
 * Created by Szabolcs Pásztor on 9/27/2015.
 */
public class UniqueFile {

    private String qualifiedName;
    private String md5;

    public UniqueFile(String qualifiedName, String md5) {
        this.qualifiedName = qualifiedName;
        this.md5 = md5;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UniqueFile) {
            UniqueFile other = (UniqueFile) obj;
            return (this.qualifiedName.equals(other.qualifiedName) && this.md5.equals(other.md5));
        } else return false;
    }
}
