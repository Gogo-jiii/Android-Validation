package com.example.validation;

import android.content.Context;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class ValidationManager {

    private static ValidationManager instance = null;
    private TextInputLayout textInputLayout;
    private EditText editText;
    private ErrorSetter errorSetter;
    private int phoneNumberDigits = 10;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private boolean isEmpty = true;
    private final String ERR_MSG_CHECK_EMPTY = "Field can not be empty.";
    private final String ERR_MSG_CHECK_PHONE_NUMBER = "Please enter 10 digit phone number.";
    private final String ERR_MSG_CHECK_EMAIL = "Invalid email.";
    private final String ERR_MSG_MATCH_PASSWORD = "Password does not match.";
    private boolean isAllValid = false;
    private boolean isEmptyValid = false;
    private boolean isEmailValid = false;
    private boolean isPhoneNumberValid = false;
    private boolean isPasswordValid = false;

    private ValidationManager() {
    }

    public static ValidationManager getInstance() {
        if (instance == null) {
            instance = new ValidationManager();
        }
        return instance;
    }

    interface ErrorSetter {
        void setError(TextInputLayout textInputLayout, String errorMsg);
    }

    ValidationManager doValidation(Context context, TextInputLayout textInputLayout) {
        errorSetter = (ErrorSetter) context;
        this.textInputLayout = textInputLayout;
        this.editText = textInputLayout.getEditText();
        return instance;
    }

    ValidationManager checkEmpty() {
        if (editText.getText().toString().isEmpty()) {
            errorSetter.setError(textInputLayout, ERR_MSG_CHECK_EMPTY);
            isEmpty = true;
            isEmptyValid = false;
        } else {
            isEmpty = false;
            isEmptyValid = true;
        }
        return instance;
    }

    ValidationManager checkPhoneNumber() {
        if (!isEmpty && editText.getText().toString().length() != phoneNumberDigits) {
            errorSetter.setError(textInputLayout, ERR_MSG_CHECK_PHONE_NUMBER);
            isPhoneNumberValid = false;
        } else {
            isPhoneNumberValid = true;
        }
        return instance;
    }

    ValidationManager checkEmail() {
        if (!isEmpty && !editText.getText().toString().matches(emailPattern)) {
            errorSetter.setError(textInputLayout, ERR_MSG_CHECK_EMAIL);
            isEmailValid = false;
        } else {
            isEmailValid = true;
        }
        return instance;
    }

    ValidationManager matchPassword(TextInputLayout firstPasswordLayout,
                                    TextInputLayout secondPasswordLayout) {
        if (!isEmpty && !firstPasswordLayout.getEditText().getText().toString().
                equals(secondPasswordLayout.getEditText().getText().toString())) {
            errorSetter.setError(textInputLayout, ERR_MSG_MATCH_PASSWORD);
            isPasswordValid = false;
        } else {
            isPasswordValid = true;
        }
        return instance;
    }

    boolean isAllValid() {
        if (isEmptyValid && isEmailValid && isPhoneNumberValid && isPasswordValid) {
            isAllValid = true;
        } else {
            isAllValid = false;
        }
        return isAllValid;
    }
}
