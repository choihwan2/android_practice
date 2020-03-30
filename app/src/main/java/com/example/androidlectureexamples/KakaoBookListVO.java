package com.example.androidlectureexamples;

import java.util.ArrayList;

public class KakaoBookListVO {
    private ArrayList<KakaoBookVO> documents = new ArrayList<>();

    public ArrayList<KakaoBookVO> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<KakaoBookVO> documents) {
        this.documents = documents;
    }
}
