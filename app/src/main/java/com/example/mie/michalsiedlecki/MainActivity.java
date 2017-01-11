package com.example.mie.michalsiedlecki;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String LENGHT_ERROR= "Password is to short";
    private static final String LOWER_CASE_LETTER_ERROR = "Use at least one lower case letter";
    private static final String CAPITAL_LETTER_ERROR = "Use at least one capital letter";
    private static final String DIGIT_ERROR = "Use at least one digit";
    private static final String EMAIL_ERROR = "Invalid e-mail";

    private EditText email;
    private EditText password;
    private TextView emailError;
    private TextView passwordError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        emailError = (TextView) findViewById(R.id.textView);
        passwordError = (TextView) findViewById(R.id.textView2);
    }

    public void onStartGameButtonClick(View view) {
        writePasswordError(checkPassword());
        writeEmailError(checkEmail());
        if (!checkErrorMessage())
            return;

        login();
        openHomeActivity();
    }

    public void writeEmailError(String error){
        emailError.setText(error);
    }

    public void writePasswordError(String error){
        passwordError.setText(error);
    }

    public String checkEmail() {
        String emailFromET = email.getText().toString();
        if(!Patterns.EMAIL_ADDRESS.matcher(emailFromET).matches())
             return EMAIL_ERROR;

        return "";
    }

    public String checkPassword(){
        String passwordFromET = password.getText().toString();

        if((passwordFromET.length() < 8))
            return LENGHT_ERROR;

        if(!passwordFromET.matches(".*[a-z]+.*"))
            return LOWER_CASE_LETTER_ERROR;

        if(!passwordFromET.matches(".*[A-Z]+.*"))
            return CAPITAL_LETTER_ERROR;

        if(!passwordFromET.matches(".*[0-9]+.*"))
            return DIGIT_ERROR;

        return ("");

    }

    public boolean checkErrorMessage(){
        return !(passwordError.length() > 0 || emailError.length() > 0);

    }

    public void login(){
        SharedPreferences loginStatus = getSharedPreferences("data", 0);
        SharedPreferences.Editor editorLoginStatus = loginStatus.edit();
        editorLoginStatus.putBoolean("loginStatus", true);
        editorLoginStatus.commit();
    }

    public void openHomeActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
