package com.example.reminders;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogCustom extends AppCompatDialogFragment {
    private EditText reminderText;
    private CheckBox impor;
    private DialogListener listener;
    private int dialogStatus;
    private String dialogText;
    private int dialogCheckBox;
    public DialogCustom(int status, String text, int checkBox){
        dialogStatus = status;
        dialogText = text;
        dialogCheckBox = checkBox;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog, null);
        reminderText = view.findViewById(R.id.reminder_text);
        impor = view.findViewById(R.id.important);
        if(dialogStatus == 1) {
            view.setBackgroundResource(R.color.Orange);
            builder.setView(view)
                    .setTitle("New Reminder")
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setPositiveButton("COMMIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String reminderDescription = reminderText.getText().toString();
                            Boolean important = impor.isChecked();
                            listener.passNewReminder(reminderDescription, important);
                            Log.w("My", reminderDescription);
                        }
                    });
        }
        else
        {
            reminderText.setText(dialogText);
            if(dialogCheckBox == 1)
                impor.setChecked(true);

            view.setBackgroundResource(R.color.Blue);
            builder.setView(view)
                    .setTitle("Edit Reminder")
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    })
                    .setPositiveButton("COMMIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String reminderDescription = reminderText.getText().toString();
                            Boolean important = impor.isChecked();
                            listener.editReminder(reminderDescription, important);
                            Log.w("My", reminderDescription);
                        }
                    });
        }


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogListener");
        }

    }

    public interface DialogListener{
        void passNewReminder(String reminderDescription, Boolean important);
        void editReminder(String reminderDescription, Boolean important);
    }


}
