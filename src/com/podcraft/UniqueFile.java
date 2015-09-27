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

    @Override
    public int hashCode() {

        int result = qualifiedName.hashCode();
        result = 31 * result + md5.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UniqueFile("+this.qualifiedName+", "+this.md5+") ";
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public String getMd5() {
        return md5;
    }
}
