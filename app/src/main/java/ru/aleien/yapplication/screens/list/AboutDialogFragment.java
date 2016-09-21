package ru.aleien.yapplication.screens.list;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import ru.aleien.yapplication.R;

public class AboutDialogFragment extends DialogFragment {

    public static AboutDialogFragment newInstance(String title, String message) {
        AboutDialogFragment frag = new AboutDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        String message = getArguments().getString("message");

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.alert_dialog_ok,
                        (dialog, whichButton) -> dismiss()
                )
                .create();
    }
}