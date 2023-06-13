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

    private RelativeLayout parentLayout, FileLayout, WriteLayout;
    public TextView TitleText, ContextText;
    public EditText TitleChange, ContextChange;
    private ArrayList<NoteInfo> arrayList;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        FileLayout = findViewById(R.id.FileLayout);
        WriteLayout = findViewById(R.id.WriteLayout);
        parentLayout = findViewById(R.id.ParentView);

        TitleChange = findViewById(R.id.EditTitle);
        ContextChange = findViewById(R.id.EditContext);

        arrayList = new ArrayList<>();
        }

        public void ChangeArrayInfo(View v){
        arrayList.add(new NoteInfo("New Note", ""));
        arrayList.get(index).setTitle(TitleChange.getText().toString());
        arrayList.get(index).setContext(ContextChange.getText().toString());
        }

        public void NewCard(View v){
        arrayList.add(new NoteInfo("New Note", ""));
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

            //Korttiin nappi
            cardView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    arrayList.add(new NoteInfo("New Note", " "));

                    WriteLayout.setVisibility(View.VISIBLE);
                    FileLayout.setVisibility(View.GONE);

                    TitleChange.setText(arrayList.get(index).getTitle());
                    ContextChange.setText(arrayList.get(index).getContext());
                }
            });

            LinearLayout linearLayout = new LinearLayout(context);
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            linearLayout.setLayoutParams(linearParams);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            //Otsikko Teksti
            TitleText = new TextView(context);
            LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            int padding = 8;
            int textViewMargin1 = 8;
            Params.setMargins(0, 0, 0, textViewMargin1);
            TitleText.setLayoutParams(Params);

            TitleText.setSingleLine();
            TitleText.setHint(arrayList.get(index).getTitle()); // Change title
            TitleText.setPadding(padding, padding, padding, padding);
            TitleText.setBackgroundResource(android.R.color.transparent);

            linearLayout.addView(TitleText);

            // Sisältö Teksti
            ContextText = new TextView(context);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            int textViewMargin2 = 3;
            textParams.setMargins(0, textViewMargin2, 0, 0);
            ContextText.setLayoutParams(textParams);

            ContextText.setText(arrayList.get(index).getContext()); // Change context
            ContextText.setPadding(padding, padding, padding, padding);

            linearLayout.addView(ContextText);

            cardView.addView(linearLayout);
            parentLayout.addView(cardView);

            marginTop += cardView.getMeasuredHeight() + 3 * margin; // Update marginTop for the next card
        }
    }
}