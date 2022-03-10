package com.project.expensestracker;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class UserMainScreen extends AppCompatActivity {

    TextView userNameTV;
    ImageView logoutButton;
    MaterialCardView addDataCard;
    EditText dataDesc, dataAmt;
    private GoogleSignInClient mGoogleSignInClient;

    Button addDataButton;
    LinearLayout addDataLayout;

    private String desc;
    long date;
    private int amt;

    private FirebaseFirestore db;

    RecyclerView recyclerView;
    ArrayList<ModelClass> list;
    DataAdapter dataAdapter;

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
        if (signInAccount != null) {
            userNameTV.setText("Welcome, " + signInAccount.getDisplayName());
            GoogleSignInOptions gso = new GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog customDialog = new CustomDialog(UserMainScreen.this);
                customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                customDialog.show();
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                desc = dataDesc.getText().toString();
                try {
                    amt = Integer.parseInt(dataAmt.getText().toString());


                    if (TextUtils.isEmpty(desc) && TextUtils.isEmpty(String.valueOf(amt))) {
                        Toast.makeText(UserMainScreen.this, "Enter data properly!", Toast.LENGTH_SHORT).show();
                    } else {
                        addDataLayout.setVisibility(View.INVISIBLE);
                        addDataCard.setVisibility(View.VISIBLE);

                        date = System.currentTimeMillis();

                        addDataToFireStore(desc, amt, date);

                        dataDesc.setText("");
                        dataAmt.setText("");
                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(UserMainScreen.this, "Enter valid amount!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerView = findViewById(R.id.dataListRecyclerView);
        eventChangeListener();
    }

    private void eventChangeListener() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<ModelClass>();
        db = FirebaseFirestore.getInstance();
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        db.collection("User").document(signInAccount.getEmail()).collection("Expenses").orderBy("dAndT", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {

                            list.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                ModelClass modelClassObj = snapshot.toObject(ModelClass.class);
                                list.add(modelClassObj);
                            }
                            dataAdapter.notifyDataSetChanged();
                        }
                    }
                });
        dataAdapter = new DataAdapter(UserMainScreen.this, list);
        dataAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(dataAdapter);
    }

    private void addDataToFireStore(String desc, long amt, long date) {
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);

        ModelClass model = new ModelClass(desc, amt, date);

        db.collection("User").document(signInAccount.getEmail()).collection("Expenses").document(desc).set(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(UserMainScreen.this, "Your data has been added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserMainScreen.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }
}