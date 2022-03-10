package com.project.expensestracker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

public class CustomDialog extends Dialog implements View.OnClickListener {

    public Activity activity;
    private MaterialCardView no, yes;
    GoogleSignInClient mGoogleSignInClient;

    public CustomDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_logout);
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
        yes = (MaterialCardView) findViewById(R.id.yesDialog);
        no = (MaterialCardView) findViewById(R.id.noDialog);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yesDialog:
                FirebaseAuth.getInstance().signOut();
                mGoogleSignInClient.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        activity.finish();
                        activity.startActivity(new Intent(activity.getApplicationContext(), MainActivity.class));
                    }
                });
                Toast.makeText(activity, "Signed Out", Toast.LENGTH_SHORT).show();

                break;
            case R.id.noDialog:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

}
