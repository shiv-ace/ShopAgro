package com.codeshiv.shopagro.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codeshiv.shopagro.R;
import com.codeshiv.shopagro.social.FacebookAuthManager;
import com.codeshiv.shopagro.social.GoogleAuthManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Collections;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.hilt.android.AndroidEntryPoint;


import static android.text.TextUtils.isEmpty;
import static com.codeshiv.shopagro.utils.PreferenceKeys.IS_LOGGED_IN;

@AndroidEntryPoint
public class SignInActivity extends AppCompatActivity {

    private static final int GOOGLE_RC_SIGN_IN = 999;
    private static final String EMAIL = "email";

    @BindView(R.id.sign_in_button)
    protected SignInButton signInButton;
    @BindView(R.id.login_button)
    protected LoginButton loginButton;
    @BindView(R.id.user_login_button)
    protected Button userLoginButton;
    @BindView(R.id.password_field)
    protected EditText password;
    @BindView(R.id.username_field)
    protected EditText username;

    @Inject
    protected GoogleAuthManager googleAuthManager;
    @Inject
    protected FacebookAuthManager facebookAuthManager;

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        unbinder = ButterKnife.bind(this);

        googleAuthManager.initializeGoogleSignInClient(this);
        setupFacebookLoginButton();
    }

    private void setupFacebookLoginButton() {
        loginButton.setReadPermissions(Collections.singletonList(EMAIL));
        loginButton.registerCallback(facebookAuthManager.getCallbackManager(), new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("ShopAgro", "Facebook Login =" + loginResult.getAccessToken());
                updateLoggedInState(true);
                navigateToMainActivity();
            }

            @Override
            public void onCancel() {
                Log.d("ShopAgro", "Facebook Login Cancelled");
                updateLoggedInState(false);
                navigateToMainActivity();
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("ShopAgro", "Facebook Login Exception", exception.fillInStackTrace());
                updateLoggedInState(false);
                navigateToMainActivity();
            }
        });
    }

    @OnClick(R.id.user_login_button)
    public void loginUser() {
        if (isEmpty(username.getText()) || isEmpty(password.getText())) {
            Toast.makeText(this, "Username or password cannot be empty", Toast.LENGTH_LONG).show();
        }

        if (password.getText().toString().equalsIgnoreCase("123456") &&
                username.getText().toString().equalsIgnoreCase("ShopAgroUser")) {
            navigateToMainActivity();
        } else {
            Toast.makeText(this, "Please enter correct credentials", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.sign_in_button)
    public void googleSignIn() {
        invokeGoogleSignIn();
    }

    @OnClick(R.id.login_button)
    public void facebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Collections.singletonList("public_profile"));
    }

    private void invokeGoogleSignIn() {
        final Intent signInIntent = googleAuthManager.getGoogleSignInClient().getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebookAuthManager.getCallbackManager().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_RC_SIGN_IN) {
            final Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignIn(task);
        }
    }

    private void handleGoogleSignIn(Task<GoogleSignInAccount> completedTask) {
        try {
            final GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d("ShopAgro", "Google Login Successful = " + account.getDisplayName());
            updateLoggedInState(true);
            navigateToMainActivity();
        } catch (ApiException e) {
            Log.e("ShopAgro", "Google Login Failed with exception = " + e.fillInStackTrace());
            updateLoggedInState(false);
            navigateToMainActivity();
        }
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    private void updateLoggedInState(boolean isLoggedIn) {
        final SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_name), MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(IS_LOGGED_IN, isLoggedIn).apply();
    }

    private void navigateToMainActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
