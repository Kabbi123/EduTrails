package com.example.edutrails.ui.discover;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.edutrails.R;


public class TutorialDialog extends AppCompatDialogFragment {
    @NonNull
    String title, desc;
    public TutorialDialog(String title, String desc){
        this.title = title;
        this.desc = desc;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.TutorialDialogTheme);
        builder.setTitle(title)
                            .setMessage(desc)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

        return builder.create();
    }
}
