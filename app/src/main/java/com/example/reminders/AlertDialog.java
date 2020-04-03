package com.example.reminders;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


public class AlertDialog extends AppCompatDialogFragment implements DialogCustom.DialogListener{

    private DialogCustom.DialogListener listener;
    private String alertDialogText;
    private Boolean alertDialogIsChecked;
    private int alertIdToBeDeleted;

    public AlertDialog(String text, Boolean isChecked, int idToBeDeleted){
        alertDialogText = text;
        alertDialogIsChecked = isChecked;
        alertIdToBeDeleted = idToBeDeleted;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Modify Reminder")
                .setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.communicateWithMain(alertDialogText, alertDialogIsChecked, false, 0);
                    }
                })
                .setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.communicateWithMain("DELETE", false, true, alertIdToBeDeleted);
                    }
                });
        return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogCustom.DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogListener");
        }

    }

    public void communicateWithMain(String reminderDescription, Boolean important, Boolean deleteFlag, int idToBeDeleted){}


}
