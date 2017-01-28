package com.test.reejh.gradletest;

/**
 * Created by 15-AB032TX on 28-01-2017.
 */

import io.realm.RealmList;
import io.realm.RealmObject;
public class Word extends RealmObject{
    private String word;
    private RealmList<stringRealm> synonyms;
    private RealmList<stringRealm> antonyms;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public RealmList<stringRealm> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(RealmList<stringRealm> synonyms) {
        this.synonyms = synonyms;
    }

    public RealmList<stringRealm> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(RealmList<stringRealm> antonyms) {
        this.antonyms = antonyms;
    }
}
