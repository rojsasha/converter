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

import com.example.alex.laboratoryone.ConvertMath;
import com.example.alex.laboratoryone.R;
import com.example.alex.laboratoryone.data.SQLiteHelper;
import com.example.alex.laboratoryone.data.SpinerModel;
import com.example.alex.laboratoryone.internet.StartAplication;

public class LenghtFragment extends Fragment implements TextWatcher, View.OnFocusChangeListener {
    private Spinner spinnerOne;
    private Spinner spinnerTwo;
    private EditText edOne, edTwo;
    private ConvertMath convertMath = new ConvertMath();


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lenght_fragment, container, false);
        spinnerOne = view.findViewById(R.id.spinnerOne);
        spinnerTwo = view.findViewById(R.id.spinnerTwo);
        edOne = view.findViewById(R.id.edFirst);
        edTwo = view.findViewById(R.id.edSecond);


        edTwo.addTextChangedListener(this);
        String[] item = getResources().getStringArray(R.array.lenght);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, item);

        spinnerOne.setAdapter(adapter);
        spinnerTwo.setAdapter(adapter);

        showLength();

        edOne.setOnFocusChangeListener(this);
        edTwo.setOnFocusChangeListener(this);

        spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int i = spinnerTwo.getSelectedItemPosition();

                if (edOne.getText().toString().isEmpty()) return;

                edTwo.setText(convertMath.calcLength(position, i, edOne.getText().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int i = spinnerOne.getSelectedItemPosition();
                if (edOne.getText().toString().isEmpty()) return;

                edTwo.setText(convertMath.calcLength(i, position, edOne.getText().toString()));
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
            edTwo.setText(convertMath.calcLength(spinnerOne.getSelectedItemPosition(),
                    spinnerTwo.getSelectedItemPosition(), edOne.getText().toString()));

        }
        if (edTwo.isFocused()) {
            edTwo.addTextChangedListener(this);
            if (edTwo.getText().toString().isEmpty()) {
                edOne.setText("");
                return;
            }
            edOne.setText(convertMath.calcLength(spinnerTwo.getSelectedItemPosition(),
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

    private void showLength() {
        SpinerModel sqliteModel = StartAplication.get(getContext()).sqliteGet().getData(1);

        if (sqliteModel != null) {
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
                SpinerModel sqlModel = new SpinerModel();
                sqlModel.setEdOne(edOne.getText().toString());
                sqlModel.setSpinerOne(spinnerOne.getSelectedItemPosition());
                sqlModel.setEdTwo(edTwo.getText().toString());
                sqlModel.setSpinerTwo(spinnerTwo.getSelectedItemPosition());

                StartAplication.get(getContext()).sqliteGet().saveData(sqlModel,1);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }
}
