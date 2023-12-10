package com.juuppi.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Notes extends AppCompatActivity {

    private RelativeLayout parentLayout, FileLayout, WriteLayout;
    public TextView TitleText, ContextText;
    public EditText TitleChange, ContextChange;
    private ArrayList<NoteInfo> arrayList; //
    private int ClickedIndex; //get right info
    public int index; //erased = all cards info is the same
    SharedPreferences preferences; //where info is saved
    SharedPreferences.Editor editor; //used to edit preferences info
    private BottomNavigationView bottomNavigationView; // Used for navigation between views

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationHandler navigationHandler = new BottomNavigationHandler(this, bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(navigationHandler); // Give ability to change views

        FileLayout = findViewById(R.id.FileLayout);
        WriteLayout = findViewById(R.id.WriteLayout);
        parentLayout = findViewById(R.id.ParentView);

        TitleChange = findViewById(R.id.EditTitle);
        ContextChange = findViewById(R.id.EditContext);

        arrayList = new ArrayList<>();

        loadData();
    }

    public void ChangeArrayInfo(View v) {

        if (ClickedIndex >= 0 && ClickedIndex < arrayList.size()){
            arrayList.get(ClickedIndex).setTitle(TitleChange.getText().toString());
            arrayList.get(ClickedIndex).setContext(ContextChange.getText().toString());
            saveData();
        }

    }

    public void NewCard(View v) {
        arrayList.add(new NoteInfo("New Note", " "));
        saveData();
        loadData();
    }

    public void Back(View v) {
        FileLayout.setVisibility(View.VISIBLE);
        WriteLayout.setVisibility(View.GONE);

        loadData();
    }

    private void saveData() {
        preferences = getApplicationContext().getSharedPreferences("DATA", MODE_PRIVATE);
        editor = preferences.edit();

        Gson gson = new Gson();

        String json = gson.toJson(arrayList);
        editor.putString("notes_data", json);
        editor.apply();
    }

    private void loadData() {
        Context context = Notes.this;
        int margin = 50;
        int marginTop = margin;

        parentLayout.removeAllViews(); //remove old cards

        preferences = getApplicationContext().getSharedPreferences("DATA", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString("notes_data", null);
        Type type = new TypeToken<ArrayList<NoteInfo>>() {
        }.getType();

        arrayList = gson.fromJson(json, type);

        if (arrayList == null) {
            arrayList = new ArrayList<>();
        } else {
            //create new cards
            for (index = 0; index < arrayList.size(); index++) {
                // Create card view
                CardView cardView = new CardView(context);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );

                params.setMargins(margin, marginTop, margin, margin);

                cardView.setLayoutParams(params);
                cardView.setCardElevation(3);
                cardView.setRadius(3);
                cardView.setCardBackgroundColor(null);

                // card is clickable
                cardView.setOnClickListener(view ->{
                        ClickedIndex = parentLayout.indexOfChild(cardView);

                            WriteLayout.setVisibility(View.VISIBLE);
                            FileLayout.setVisibility(View.GONE);

                            TitleChange.setText(arrayList.get(ClickedIndex).getTitle());
                            ContextChange.setText(arrayList.get(ClickedIndex).getContext());
                });

                LinearLayout linearLayout = new LinearLayout(context);
                LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                linearLayout.setLayoutParams(linearParams);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                // Card Title
                TitleText = new TextView(context);
                RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                int padding = 8;
                int textViewMargin1 = 8;
                titleParams.setMargins(0, 0, 3, textViewMargin1);
                TitleText.setLayoutParams(titleParams);

                TitleText.setSingleLine();
                TitleText.setHint(arrayList.get(index).getTitle()); // Change title
                TitleText.setPadding(padding, padding, padding, padding);
                TitleText.setBackgroundResource(android.R.color.transparent);

                // Card Context
                ContextText = new TextView(context);
                RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                int textViewMargin2 = 3;
                textParams.addRule(RelativeLayout.BELOW, TitleText.getId());
                textParams.setMargins(0, textViewMargin2, 0, 0);
                ContextText.setLayoutParams(textParams);

                ContextText.setText(arrayList.get(index).getContext()); // Change context
                ContextText.setPadding(padding, padding, padding, padding);

                // Button to Erase card
                ImageButton button = new ImageButton(context);
                RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(100, 100);
                buttonParams.addRule(RelativeLayout.ALIGN_PARENT_END);
                buttonParams.addRule(RelativeLayout.ALIGN_BOTTOM, ContextText.getId());
                button.setLayoutParams(buttonParams);

                button.setImageResource(R.drawable.ic_delete_forever);

                //Button erases the card & saves changes
                button.setOnClickListener(view ->{
                        int position = parentLayout.indexOfChild((View) view.getParent().getParent());
                        if (position >= 0 && position < arrayList.size()) {
                            arrayList.remove(position);
                            saveData();
                            loadData();
                    }
                });
                linearLayout.addView(TitleText);
                linearLayout.addView(ContextText);

                RelativeLayout relativeLayout = new RelativeLayout(context);
                RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                relativeLayout.setLayoutParams(relativeParams);
                relativeLayout.addView(linearLayout);
                relativeLayout.addView(button);

                cardView.addView(relativeLayout);
                parentLayout.addView(cardView);

                marginTop += cardView.getMeasuredHeight() + 3 * margin; // Update marginTop for the next card
            }
        }
    }
}