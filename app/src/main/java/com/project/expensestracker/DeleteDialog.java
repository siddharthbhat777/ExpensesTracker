package com.project.expensestracker;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeleteDialog extends Dialog implements View.OnClickListener {

    public Activity activity;
    private MaterialCardView no, yes;
    GoogleSignInAccount account;
    private String path;

    public DeleteDialog(Activity activity, String path) {
        super(activity);
        this.activity = activity;
        this.path = path;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.delete_dialog);

        account = GoogleSignIn.getLastSignedInAccount(activity);
        yes = (MaterialCardView) findViewById(R.id.yesDeleteDialog);
        no = (MaterialCardView) findViewById(R.id.noDeleteDialog);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yesDeleteDialog:


                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("User").document(account.getEmail()).collection("Expenses")
                        .document(path)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
                        });

                Toast.makeText(activity, "Data deleted", Toast.LENGTH_SHORT).show();


                break;
            case R.id.noDeleteDialog:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

}
