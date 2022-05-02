package ru.mirea.ivanov.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class AlertDialogFragment extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Здравствуй МИРЭА!")
                .setMessage("Успех близок?")
                .setIcon(R.mipmap.ic_launcher_round)
                .setPositiveButton("Иду дальше", (dialog, id) -> {
                    ((MainActivity) getActivity()).onOkDialogClicked();
                    dialog.cancel();
                })
                .setNeutralButton("На паузе",
                        (dialog, id) -> {
                            ((MainActivity) getActivity()).onNeutralDialogClicked();
                            dialog.cancel();
                        })
                .setNegativeButton("Нет",
                        (dialog, id) -> {
                            ((MainActivity) getActivity()).onCancelDialogClicked();
                            dialog.cancel();
                        });
        return builder.create();
    }
}
