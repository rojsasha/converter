package com.example.alex.laboratoryone.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.alex.laboratoryone.ConvertMath;
import com.example.alex.laboratoryone.R;
import com.example.alex.laboratoryone.internet.RetorfitGet;
import com.example.alex.laboratoryone.internet.StartAplication;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyFragment extends Fragment implements TextWatcher, View.OnFocusChangeListener {
    private Spinner spinnerOne;
    private Spinner spinnerTwo;
    private EditText edOne, edTwo;
    private TextView textView;
    private ConvertMath convertMath = new ConvertMath();
    private ProgressBar progressBar;
    private ArrayList<String> currencyValues;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.currency_fragment, container, false);
        spinnerOne = view.findViewById(R.id.spinnerOne);
        spinnerTwo = view.findViewById(R.id.spinnerTwo);
        edOne = view.findViewById(R.id.edFirst);
        edTwo = view.findViewById(R.id.edSecond);
        textView = view.findViewById(R.id.textInternet);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        sendRequest();

        edOne.setOnFocusChangeListener(this);
        edTwo.setOnFocusChangeListener(this);

        spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int i = spinnerTwo.getSelectedItemPosition();

                if (currencyValues == null) return;

                textView.setText(convertMath.exchange(currencyValues.get(spinnerTwo.getSelectedItemPosition())
                        , currencyValues.get(spinnerOne.getSelectedItemPosition())));
                if (edOne.getText().toString().isEmpty()) return;
                edTwo.setText(convertMath.calcCurrency(currencyValues.get(i), currencyValues.get(position), edOne.getText().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int i = spinnerOne.getSelectedItemPosition();

                if (currencyValues == null) return;
                textView.setText(convertMath.exchange(currencyValues.get(spinnerTwo.getSelectedItemPosition())
                        , currencyValues.get(spinnerOne.getSelectedItemPosition())));

                if (edOne.getText().toString().isEmpty()) return;
                edTwo.setText(convertMath.calcCurrency(currencyValues.get(position), currencyValues.get(i), edOne.getText().toString()));
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

            edTwo.setText(convertMath.calcCurrency(currencyValues.get(spinnerTwo.getSelectedItemPosition()),
                    currencyValues.get(spinnerOne.getSelectedItemPosition()), edOne.getText().toString()));
        }
        if (edTwo.isFocused()) {
            edTwo.addTextChangedListener(this);
            if (edTwo.getText().toString().isEmpty()) {
                edOne.setText("");
                return;
            }
            edOne.setText(convertMath.calcCurrency(currencyValues.get(spinnerOne.getSelectedItemPosition()),
                    currencyValues.get(spinnerTwo.getSelectedItemPosition()), edTwo.getText().toString()));
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

    private void sendRequest() {
        RetorfitGet retorfitGet = StartAplication.get(getContext()).ListCurrency();
        Call listCall = retorfitGet.getSpinerList();
        listCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, @NonNull Response response) {
                JsonObject object = (JsonObject) response.body();
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject jsonObject = object.getAsJsonObject("rates");
                    currencyValues = new ArrayList<>();
                    Object[] stringsList = jsonObject.keySet().toArray();

                    for (int i = 0; i < stringsList.length; i++) {
                        currencyValues.add(String.valueOf(jsonObject.get(String.valueOf(stringsList[i]))));
                    }

                    ArrayAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, stringsList);
                    spinnerOne.setAdapter(adapter);
                    spinnerTwo.setAdapter(adapter);

                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                call.cancel();
            }
        });
    }
}
