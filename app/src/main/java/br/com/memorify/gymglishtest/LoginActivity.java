package br.com.memorify.gymglishtest;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Login screen via username and password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final List<Pair<String, String>> CREDENTIALS;
    static {
        ArrayList<Pair<String, String>> credentials = new ArrayList<>();
        credentials.add(new Pair<>("gymglish" , "Gymglish123"));
        credentials.add(new Pair<>("kevin"    , "Kevin123"));
        credentials.add(new Pair<>("admin"    , "admin123"));
        CREDENTIALS = Collections.unmodifiableList(credentials);
    }

    private EditText mUsernameView;
    private EditText mPasswordView;
    private TextInputLayout mUsernameLayoutView;
    private TextInputLayout mPasswordLayoutView;
    private View mProgressView;
    private Button mLoginButton;

    public static void startLogin(Activity activity) {
        activity.startActivity(new Intent(activity, LoginActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
        setupViews();
    }

    private void bindViews() {
        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mUsernameLayoutView = (TextInputLayout) findViewById(R.id.username_input_layout);
        mPasswordLayoutView = (TextInputLayout) findViewById(R.id.password_input_layout);
        mProgressView = findViewById(R.id.login_progress);
        mLoginButton = (Button) findViewById(R.id.email_sign_in_button);
    }

    private void setupViews() {
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL || id == EditorInfo.IME_ACTION_DONE) {
                    tryLogin();
                    return true;
                }
                return false;
            }
        });

        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tryLogin();
            }
        });
    }

    private void tryLogin() {
        enableLogin(false);

        String email = mUsernameView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();

        boolean shouldCancel = false;
        View focusView = null;

        // Validate password
        if (TextUtils.isEmpty(password)) {
            mPasswordLayoutView.setErrorEnabled(true);
            mPasswordLayoutView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            shouldCancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordLayoutView.setErrorEnabled(true);
            mPasswordLayoutView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            shouldCancel = true;
        } else {
            mPasswordLayoutView.setError(null);
        }

        // Validate email
        if (TextUtils.isEmpty(email)) {
            mUsernameLayoutView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            shouldCancel = true;
        } else if (!isEmailValid(email)) {
            mUsernameLayoutView.setError(getString(R.string.error_invalid_username));
            focusView = mUsernameView;
            shouldCancel = true;
        } else {
            mUsernameLayoutView.setError(null);
        }

        if (shouldCancel) {
            focusView.requestFocus();
            enableLogin(true);
        } else {
            showProgress(true);
            new UserLoginTask(email, password).execute();
        }
    }

    private boolean isEmailValid(String email) {
        return email.length() >= 3;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8 && textHasUppercase(password) && textHasNumber(password);
    }

    private boolean textHasUppercase(String text) {
        return !text.toLowerCase().equals(text);
    }

    private boolean textHasNumber(String text) {
        return text.matches(".*\\d.*");
    }

    private void enableLogin(final boolean isEnabled) {
        mLoginButton.setEnabled(isEnabled);
        mUsernameView.setEnabled(isEnabled);
        mPasswordView.setEnabled(isEnabled);
    }

    private void showProgress(final boolean shouldShow) {
        mProgressView.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }

    /**
     * Represents an asynchronous login task used to authenticate the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;
        private boolean mUserExist = false;

        UserLoginTask(String email, String password) {
            mUsername = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                return false;
            }

            for (Pair<String, String> credential : CREDENTIALS) {
                if (credential.first.toUpperCase().equals(mUsername.toUpperCase())) {
                    mUserExist = true;
                    return (credential.second.equals(mPassword));
                }
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            showProgress(false);

            if (success) {
                WebsiteListActivity.startWebsiteList(LoginActivity.this);
            } else {
                if (mUserExist) {
                    mPasswordLayoutView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                } else {
                    mUsernameLayoutView.setError(getString(R.string.error_incorrect_username));
                    mUsernameView.requestFocus();
                }
                enableLogin(true);
            }
        }

        @Override
        protected void onCancelled() {
            showProgress(false);
            enableLogin(true);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}

