package com.juuppi.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class Notes extends AppCompatActivity {

    private RelativeLayout parentLayout;
    public TextView text;
    public EditText editText;
    public int cardCount = 0;
    private ArrayList<NoteInfo> arrayList;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        parentLayout = findViewById(R.id.ParentView);
        arrayList = new ArrayList<>();
        }
        public void Testi(View v){
        arrayList.add(new NoteInfo("New Note", " "));
        loadData();
        }

    private void loadData() {
        Context context = Notes.this;
        int margin = 50;
        int marginTop = margin;

        parentLayout.removeAllViews();

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

            LinearLayout linearLayout = new LinearLayout(context);
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            linearLayout.setLayoutParams(linearParams);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            // EditText inside card view
            editText = new EditText(context);
            LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            int padding = 8;
            int editTextMargin = 8;
            editParams.setMargins(0, 0, 0, editTextMargin);
            editText.setLayoutParams(editParams);

            editText.setSingleLine();
            editText.setHint(arrayList.get(index).getTitle()); // Change title
            editText.setPadding(padding, padding, padding, padding);
            editText.setBackgroundResource(android.R.color.transparent);

            linearLayout.addView(editText);

            // TextView inside card
            text = new TextView(context);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            int textViewMargin = 3;
            textParams.setMargins(0, textViewMargin, 0, 0);
            text.setLayoutParams(textParams);

            text.setText(arrayList.get(index).getContext()); // Change context
            text.setPadding(padding, padding, padding, padding);

            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {}
            });

            linearLayout.addView(text);

            cardView.addView(linearLayout);
            parentLayout.addView(cardView);

            marginTop += cardView.getMeasuredHeight() + 3 * margin; // Update marginTop for the next card
        }
    }
}