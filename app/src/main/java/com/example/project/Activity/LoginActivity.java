package com.example.project.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class LoginActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        setContentView(R.layout.activity_login);
        bottomNavigation();
    }

    private void updateUI(GoogleSignInAccount acct) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 151);
            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 151) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            TextView name = findViewById(R.id.nome);
            TextView surname = findViewById(R.id.cognome);
            TextView email = findViewById(R.id.email);
            CardView fp = findViewById(R.id.card);
            Button sout = findViewById(R.id.button2);
            ImageView fotoprofilo = findViewById(R.id.fotoprofilo);
            SignInButton signInButton = findViewById(R.id.sign_in_button);

            sout.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.INVISIBLE);
            name.setVisibility(View.VISIBLE);
            surname.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);
            fp.setVisibility(View.VISIBLE);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                surname.setText("  Surname :" + " " + personFamilyName);
                name.setText("  Name :" + " " + personGivenName);
                email.setText("Email :" + " " + personEmail);
                Glide.with(this)
                        .load(personPhoto)
                        .into(fotoprofilo);
            }
                // Signed in successfully, show authenticated UI.
            } catch(ApiException e){
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
                //updateUI(null);
            }
    }

    public void signOut(View view) {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        TextView name = findViewById(R.id.nome);
                        TextView surname = findViewById(R.id.cognome);
                        TextView email = findViewById(R.id.email);
                        CardView fp = findViewById(R.id.card);
                        Button sout = findViewById(R.id.button2);
                        ImageView fotoprofilo = findViewById(R.id.fotoprofilo);
                        SignInButton signInButton = findViewById(R.id.sign_in_button);

                        sout.setVisibility(View.INVISIBLE);
                        signInButton.setVisibility(View.VISIBLE);
                        name.setVisibility(View.INVISIBLE);
                        surname.setVisibility(View.INVISIBLE);
                        email.setVisibility(View.INVISIBLE);
                        fp.setVisibility(View.INVISIBLE);
                        revokeAccess();

                    }
                });

    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.card_btn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CartListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

    }
}
