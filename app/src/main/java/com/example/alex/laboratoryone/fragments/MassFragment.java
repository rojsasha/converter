package com.example.alex.laboratoryone.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alex.laboratoryone.ConvertMath;
import com.example.alex.laboratoryone.R;
import com.example.alex.laboratoryone.data.SQLiteHelper;
import com.example.alex.laboratoryone.data.SpinerModel;
import com.example.alex.laboratoryone.internet.StartAplication;

public class MassFragment extends Fragment implements TextWatcher, View.OnFocusChangeListener {
    private Spinner spinnerOne;
    private Spinner spinnerTwo;
    private EditText edOne, edTwo;
    private ConvertMath convertMath = new ConvertMath();


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mass_fragment, container, false);
        spinnerOne = view.findViewById(R.id.spinnerOne);
        spinnerTwo = view.findViewById(R.id.spinnerTwo);
        edOne = view.findViewById(R.id.edFirst);
        edTwo = view.findViewById(R.id.edSecond);


        edTwo.addTextChangedListener(this);
        String[] item = getResources().getStringArray(R.array.mass);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, item);

        spinnerOne.setAdapter(adapter);
        spinnerTwo.setAdapter(adapter);
        showMass();
        edOne.setOnFocusChangeListener(this);
        edTwo.setOnFocusChangeListener(this);

        spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int i = spinnerTwo.getSelectedItemPosition();
                if (edOne.getText().toString().isEmpty()) return;

                edTwo.setText(convertMath.calcMass(position, i, edOne.getText().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int i = spinnerOne.getSelectedItemPosition();
                if (edTwo.getText().toString().isEmpty()) return;
                edTwo.setText(convertMath.calcMass(i, position, edOne.getText().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.e("beforetext", "beforetext");
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.e("ontext", "ontext");
    }

    @Override
    public void afterTextChanged(Editable editable) {

        if (editable == null) return;

        edOne.removeTextChangedListener(this);
        edTwo.removeTextChangedListener(this);
        if (edOne.isFocused()) {
            edOne.addTextChangedListener(this);
            if (edOne.getText().toString().isEmpty()) {
                edTwo.setText("");
                return;
            }

            Log.e("sddf", "safdg");
            edTwo.setText(convertMath.calcMass(spinnerOne.getSelectedItemPosition(),
                    spinnerTwo.getSelectedItemPosition(), edOne.getText().toString()));

        }
        if (edTwo.isFocused()) {

            edTwo.addTextChangedListener(this);

            if (edTwo.getText().toString().isEmpty()) {
                edOne.setText("");
                return;
            }

            edOne.setText(convertMath.calcMass(spinnerTwo.getSelectedItemPosition(),
                    spinnerOne.getSelectedItemPosition(), edTwo.getText().toString()));

        }

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.edFirst:
                edOne.addTextChangedListener(this);
                edTwo.removeTextChangedListener(this);
                break;
            case R.id.edSecond:
                edTwo.addTextChangedListener(this);
                edOne.removeTextChangedListener(this);
        }
    }

    private void showMass() {
        SQLiteHelper sqlite = StartAplication.get(getContext()).sqliteGet();
        SpinerModel sqliteModel = sqlite.getData(2);

        if (sqliteModel == null) {
            Toast.makeText(getContext(), "Первый запуск", Toast.LENGTH_SHORT).show();

        } else {
            edOne.setText(sqliteModel.getEdOne());
            spinnerOne.setSelection(sqliteModel.getSpinerOne());
            edTwo.setText(sqliteModel.getEdTwo());
            spinnerTwo.setSelection(sqliteModel.getSpinerTwo());
        }
    }

    @Override
    public void onStop() {
        super.onStop();

Runnable runnable = new Runnable() {
    @Override
    public void run() {
        Log.e("stopped", "stopped");
        SQLiteHelper sqlite = StartAplication.get(getContext()).sqliteGet();
        SpinerModel model = new SpinerModel();
        model.setEdOne(edOne.getText().toString());
        model.setSpinerOne(spinnerOne.getSelectedItemPosition());
        model.setEdTwo(edTwo.getText().toString());
        model.setSpinerTwo(spinnerTwo.getSelectedItemPosition());
        sqlite.saveData(model, 2);
    }
};
       Thread thread = new Thread(runnable);
       thread.start();
    }
}