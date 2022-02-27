package com.project.expensestracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserMainScreen extends AppCompatActivity {

    TextView userNameTV;
    ImageView logoutButton;
    MaterialCardView addDataCard;
    EditText dataDesc, dataAmt;
    Button addDataButton;
    LinearLayout addDataLayout;

    private String desc, amt;

    private FirebaseFirestore db;

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

                desc = dataDesc.getText().toString();
                amt = dataAmt.getText().toString();

                if (TextUtils.isEmpty(desc) && TextUtils.isEmpty(amt)) {
                    Toast.makeText(UserMainScreen.this, "Enter data properly!", Toast.LENGTH_SHORT).show();
                } else {
                    addDataLayout.setVisibility(View.INVISIBLE);
                    addDataCard.setVisibility(View.VISIBLE);

                    addDataToFireStore(desc, amt);
                }
            }
        });

        db = FirebaseFirestore.getInstance();
    }

    private void addDataToFireStore(String desc, String amt) {
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        //CollectionReference dbData = db.collection("User");

        ModelClass model = new ModelClass(desc, amt);

        FirebaseFirestore.getInstance().collection("User").document(signInAccount.getEmail()).collection("Expenses").document(desc).set(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(UserMainScreen.this, "Your data has been added", Toast.LENGTH_SHORT).show();
            }
        });

        /*dbData.add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(UserMainScreen.this, "Your data has been added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserMainScreen.this, "Failed to add data", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}