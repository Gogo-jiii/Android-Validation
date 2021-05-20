package com.example.validation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity implements TextWatcher,
        ValidationManager.ErrorSetter {

    Button btnSubmit;
    TextInputLayout tilEmpty, tilEmail, tilNumberofDigits, tilPassword, tilRePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSubmit = findViewById(R.id.btnSubmit);
        tilEmpty = findViewById(R.id.tilEmpty);
        tilEmail = findViewById(R.id.tilEmail);
        tilNumberofDigits = findViewById(R.id.tilNumberofDigits);
        tilPassword = findViewById(R.id.tilPassword);
        tilRePassword = findViewById(R.id.tilRePassword);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ValidationManager.getInstance().doValidation(MainActivity.this, tilEmpty).checkEmpty();
                ValidationManager.getInstance().doValidation(MainActivity.this,
                        tilNumberofDigits).checkEmpty()
                        .checkPhoneNumber();
                ValidationManager.getInstance().doValidation(MainActivity.this, tilEmail).checkEmpty()
                        .checkEmail();
                ValidationManager.getInstance().doValidation(MainActivity.this, tilPassword).checkEmpty();
                ValidationManager.getInstance().doValidation(MainActivity.this, tilRePassword).checkEmpty()
                        .matchPassword(tilPassword, tilRePassword);

                if (ValidationManager.getInstance().isAllValid()) {
                    Toast.makeText(MainActivity.this, "All valid.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setTextWatcher();
    }

    private void setTextWatcher() {
        tilEmpty.getEditText().addTextChangedListener(this);
        tilEmail.getEditText().addTextChangedListener(this);
        tilNumberofDigits.getEditText().addTextChangedListener(this);
        tilPassword.getEditText().addTextChangedListener(this);
        tilRePassword.getEditText().addTextChangedListener(this);
    }

    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override public void afterTextChanged(Editable editable) {
        if (editable.hashCode() == tilEmpty.getEditText().getText().hashCode()) {
            tilEmpty.setErrorEnabled(false);
        } else if (editable.hashCode() == tilNumberofDigits.getEditText().getText().hashCode()) {
            tilNumberofDigits.setErrorEnabled(false);
        } else if (editable.hashCode() == tilEmail.getEditText().getText().hashCode()) {
            tilEmail.setErrorEnabled(false);
        } else if (editable.hashCode() == tilPassword.getEditText().getText().hashCode()) {
            tilPassword.setErrorEnabled(false);
        } else if (editable.hashCode() == tilRePassword.getEditText().getText().hashCode()) {
            tilRePassword.setErrorEnabled(false);
        }
    }

    @Override public void setError(TextInputLayout textInputLayout, String errorMsg) {
        textInputLayout.setError(errorMsg);
    }
}