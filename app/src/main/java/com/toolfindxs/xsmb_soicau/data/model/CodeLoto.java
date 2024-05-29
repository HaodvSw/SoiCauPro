package com.toolfindxs.xsmb_soicau.data.model;

import com.google.gson.annotations.SerializedName;

public class CodeLoto {
    @SerializedName("first")
    private String first;

    @SerializedName("last")
    private String last;

    public CodeLoto(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return "CodeLoto{" +
                "start='" + first + '\'' +
                ", end='" + last + '\'' +
                '}';
    }
}
