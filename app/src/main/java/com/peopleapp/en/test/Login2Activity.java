package com.peopleapp.en.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

public class Login2Activity extends AppCompatActivity {

    private CallbackManager callbackManager;

    private boolean isLogin = false;

    private AppCompatButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Bill", "onSuccess");

                        isLogin = true;
                        loginButton.setText("退出");
                    }

                    @Override
                    public void onCancel() {
                        isLogin = false;
                        Log.d("Bill", "onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        isLogin = false;
                        Log.d("Bill", "onError:" + exception.getMessage());
                    }
                });


        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    isLogin = false;
                    loginButton.setText("登录");
                    LoginManager.getInstance().logOut();
                } else {
                    LoginManager.getInstance().logInWithReadPermissions(Login2Activity.this, Arrays.asList("public_profile"));
                }
            }
        });


        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        Log.d("Bill", "isLoggedIn:" + isLoggedIn);

        if (isLoggedIn) {
            isLogin = true;
            loginButton.setText("退出");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


}
