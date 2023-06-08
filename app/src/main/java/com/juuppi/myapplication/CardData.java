package com.juuppi.myapplication;

import androidx.cardview.widget.CardView;

import com.google.gson.annotations.SerializedName;

public class CardData {

    @SerializedName("CardView")
    private CardView card;
    @SerializedName("Title")
    private String title;
    @SerializedName("Context")
    private String context;

    public CardData (String Title, String Context){
        this.title = Title;
        this.context = Context;
    }
}
