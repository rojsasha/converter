package com.example.alex.laboratoryone.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.alex.laboratoryone.R;

public class Dialog extends DialogFragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_currency, container, false);
        Button btnDialog = view.findViewById(R.id.btnDialog);
        btnDialog.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}
