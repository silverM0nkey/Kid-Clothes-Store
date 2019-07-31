package com.happybaby.kcs.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.happybaby.kcs.R;
import com.happybaby.kcs.activities.interfaces.LoginView;
import com.happybaby.kcs.presenters.LoginPresenter;


public class LoginActivity extends BaseActivity implements LoginView {

    LoginPresenter loginPresenter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         loginPresenter = new LoginPresenter(this);

        setupToolbar();
        setTitle(getResources().getString(R.string.login_title));

        TextView loginJoinText = findViewById(R.id.login_join_text);
        loginJoinText.setText(String.format("%s %s %s",
                getResources().getString(R.string.login_join_text_1),
                getResources().getString(R.string.app_name),
                getResources().getString(R.string.login_join_text_2)));

        Button join = findViewById(R.id.join_button);
        Button login = findViewById(R.id.login_button);
        EditText email =findViewById(R.id.email);
        EditText password = findViewById(R.id.password);

        Context context = this;

        join.setOnClickListener(View -> {
                Toast.makeText(context, context.getResources().getString(R.string.join_message), Toast.LENGTH_SHORT).show();
        });

        login.setOnClickListener(View -> {
               loginPresenter.login(email.getText().toString(), password.getText().toString());
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loginPresenter.unbindView();
    }

    public void loginFinished(){
        finish();
    }

    public void loginFail() {
        Toast.makeText(this, getResources().getString(R.string.login_faild), Toast.LENGTH_SHORT);
    }
}


