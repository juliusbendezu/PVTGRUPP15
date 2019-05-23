package com.dsv2019.pvt15.prepapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;

public class LoginActivity extends Activity
{
    private LoginButton mLoginButton;
    private CallbackManager mCallbackManager;
    private static final String EMAIL = "email";
    private TextView idTv;
    private TextView emailTv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mCallbackManager = CallbackManager.Factory.create();
        mLoginButton = findViewById(R.id.login_button);


        mLoginButton.setReadPermissions(Arrays.asList(EMAIL));
        checkLoginStatus();

        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {

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

    AccessTokenTracker tokenTracker = new AccessTokenTracker()
    {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken)
        {

            if (currentAccessToken == null)
            {
                idTv.setText(null);
                emailTv.setText(null);
                Toast.makeText(LoginActivity.this, "User logged out", Toast.LENGTH_SHORT).show();
            }
            else
            {
                loadUserProfile(currentAccessToken);
            }

        }
    };

    private void loadUserProfile(AccessToken newAccessToken)
    {
        GraphRequest graphRequest = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback()
        {
            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse)
            {
                DisplayData(jsonObject);

            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("fields", "email, id");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }

    private void DisplayData(JSONObject object)
    {
        try
        {
            String stringID = object.getString("id");
            String email = object.getString("email");

            idTv = findViewById(R.id.id_login_text);
            idTv.setText(stringID);

            emailTv = findViewById(R.id.email_login_text);
            emailTv.setText(email);

        } catch (JSONException e)
        {
            e.printStackTrace();
        }


    }

    private void checkLoginStatus()
    {
        if (AccessToken.getCurrentAccessToken() != null)
        {
            loadUserProfile(AccessToken.getCurrentAccessToken());

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
