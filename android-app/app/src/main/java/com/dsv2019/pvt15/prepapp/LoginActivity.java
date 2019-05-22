package com.dsv2019.pvt15.prepapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends Activity
{
    private LoginButton mLoginButton;
    private CallbackManager mCallbackManager;
    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mCallbackManager = CallbackManager.Factory.create();
        mLoginButton = findViewById(R.id.login_button);

        mLoginButton.setReadPermissions(Arrays.asList(EMAIL));

        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                AccessToken accessToken = loginResult.getAccessToken();
                GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback()
                {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse)
                    {
                        Display_data(jsonObject);

                    }
                });
                Bundle bundle = new Bundle();
                bundle.putString("fields", "email, id");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();

            }

            @Override
            public void onCancel()
            {

            }

            @Override
            public void onError(FacebookException error)
            {

            }
        });
    }

    private void Display_data(JSONObject object)
    {
        TextView textView = findViewById(R.id.email_login_text);
        textView.setText(object.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
