package app.fuelfinder;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends Activity implements View.OnClickListener{

    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private EditText inputEmail, inputPassword;
    private Button  btnLogin;
    Button btn_createAccount ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_createAccount=(Button)findViewById(R.id.btn_create_account) ;
        btn_createAccount.setOnClickListener(this);


        inputEmail = (EditText) findViewById(R.id.text_email);
        inputPassword = (EditText) findViewById(R.id.text_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnLogin = (Button) findViewById(R.id.submit_login);




        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        //If user is already logged in
//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(MainActivity.this, MapsActivity.class));
//            finish();
//        }


        //Button for login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                //Checking if eamil field is not empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Checking if password field is not empty

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Checking if password's length

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Showing progress bar
                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    Toast.makeText(getApplicationContext(),"Error"+task.getException().toString(),Toast.LENGTH_SHORT).show(); 

                                } else {
                                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                                    intent.putExtra("email",email) ;
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });


    }

    @Override
    public void onClick(View view) {
        if(view.getId()==btn_createAccount.getId()){
            //Starting SignUP activity
            startActivity(new Intent(MainActivity.this, SignUpActivity.class));

        }
    }
}
