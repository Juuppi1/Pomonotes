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

import java.util.List;


public class Notes extends AppCompatActivity {

    private RelativeLayout parentLayout;
    private int cardCount = 0;
    private String Title;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        parentLayout = findViewById(R.id.ParentView);
        }

public void Testi(View v){
        createCardView();
}

    private void createCardView() {
        Context context = Notes.this;

        CardView cardView = new CardView(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        int margin = 50;
        int marginTop = (cardCount == 0) ? margin : 2 * margin + cardCount * (2 * margin + cardView.getMeasuredHeight());
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

        EditText editText = new EditText(context);
        LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        int padding = 8;
        int editTextMargin = 8;
        editParams.setMargins(0, 0, 0, editTextMargin);
        editText.setLayoutParams(editParams);

        editText.setSingleLine();
        editText.setHint("Enter text");
        editText.setPadding(padding, padding, padding, padding);
        editText.setBackgroundResource(android.R.color.transparent);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                Title = editText.getText().toString();
            }
        });

        linearLayout.addView(editText);

        TextView textView = new TextView(context);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        int textViewMargin = 3;
        textParams.setMargins(0, textViewMargin, 0, 0);
        textView.setLayoutParams(textParams);

        textView.setText("Result: ");
        textView.setPadding(padding, padding, padding, padding);
        textView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                editText.setHint("klikattu");
                preferences = getSharedPreferences(Title, MODE_PRIVATE);
                editor = preferences.edit();
            }
        });

        linearLayout.addView(textView);

        cardView.addView(linearLayout);
        parentLayout.addView(cardView);

        cardCount++;
    }
}