package com.example.zul.localdatabase.Model;

public class DataModel {

    private int id;
    private String englishWord;
    private String englishTranslate;

    public DataModel() {
    }

    public DataModel(int id, String englishWord, String englishTranslate) {
        this.id = id;
        this.englishWord = englishWord;
        this.englishTranslate = englishTranslate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getEnglishTranslate() {
        return englishTranslate;
    }

    public void setEnglishTranslate(String englishTranslate) {
        this.englishTranslate = englishTranslate;
    }

}
