package com.juuppi.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;


public class TimerDialog extends AppCompatDialogFragment {

    private EditText PomoTime, BreakTime;
    private DialogListener listener;
    private int PomoInt, BreakInt;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_timer_dialog, null);

        builder.setView(view)
                .setTitle("Otsikko")

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i)
                    {

                    }
                })

                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i)
                    {
                        if (PomoTime.getText().toString().isEmpty()) {
                            PomoTime.setText("0");
                        }
                        if (BreakTime.getText().toString().isEmpty()) {
                            BreakTime.setText("0");
                        }
                        String Pomotime = PomoTime.getText().toString();
                        String Breaktime = BreakTime.getText().toString();

                        PomoInt = Integer.parseInt(Pomotime);
                        BreakInt = Integer.parseInt(Breaktime);

                        listener.SetTimes(PomoInt, BreakInt);

                    }
                });
        PomoTime = view.findViewById(R.id.PomoT);
        BreakTime = view.findViewById(R.id.BreakT);
        return builder.create();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + "must implement ExampleDialogListener");
        }
    }
    public interface DialogListener
    {
        void SetTimes(int PomoInt, int BreakInt);
    }
}
