package com.example.android.miwok;

public class Word {
    //we are declaring variable or field as private because 1st, we don't want change variable directly
    //2ndly, we don't want user to assign wrong type of variable. That we will be using method
    private  String mivok_word;
    private String english_word;
    private int imageResourceId = IMGAGE_PRESENCE;
    private int audioResourceId;
    private final static int IMGAGE_PRESENCE= -1; //choosing -1 because this is out of range of the possibel value of the resource ID.
    //variable as static means, the value of the variable is common through all the object
    //variable as final means, it is constant know, and value can't be change.
    public Word(String english, String mivok,int Id, int audioId){
        english_word = english;
        mivok_word = mivok;
        imageResourceId = Id;
        audioResourceId = audioId;
    }

    public Word(String english, String mivok, int audioId){
        english_word = english;
        mivok_word = mivok;
        audioResourceId = audioId;
    }

    public String getEnglish_word() {
        return english_word;
    }

    public String getMivok_word() {
        return mivok_word;
    }

    public int getImageResourceId() {
        return imageResourceId; }

    public boolean getImgagePresence() {
        return imageResourceId != IMGAGE_PRESENCE;
    }

    public int getAudioResourceId() {
        return audioResourceId;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
