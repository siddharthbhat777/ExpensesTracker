package com.project.expensestracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

public class UserMainScreen extends AppCompatActivity {

    TextView userNameTV;
    ImageView logoutButton;
    MaterialCardView addDataCard;
    EditText dataDesc, dataAmt;
    Button addDataButton;
    LinearLayout addDataLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_screen);

        getWindow().setStatusBarColor(ContextCompat.getColor(UserMainScreen.this, R.color.violet));

        userNameTV = findViewById(R.id.userNameTextView);
        logoutButton = findViewById(R.id.logoutButton);
        addDataCard = findViewById(R.id.addDataCardView);
        dataDesc = findViewById(R.id.dataDescription);
        dataAmt = findViewById(R.id.dataAmount);
        addDataButton = findViewById(R.id.addButton);
        addDataLayout = findViewById(R.id.addDataLayout);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            userNameTV.setText("Welcome, " + signInAccount.getDisplayName());
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        addDataCard.setVisibility(View.VISIBLE);
        addDataLayout.setVisibility(View.INVISIBLE);

        addDataCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDataCard.setVisibility(View.INVISIBLE);
                addDataLayout.setVisibility(View.VISIBLE);
            }
        });

        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDataLayout.setVisibility(View.INVISIBLE);
                addDataCard.setVisibility(View.VISIBLE);
            }
        });
    }
}