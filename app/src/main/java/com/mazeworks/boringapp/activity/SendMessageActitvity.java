package com.mazeworks.boringapp.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mazeworks.boringapp.R;
import com.mazeworks.boringapp.entities.MessageNavigationClassParser;
import com.mazeworks.boringapp.entities.Post;
import com.mazeworks.boringapp.entities.User;
import com.mazeworks.boringapp.util.FirebaseDataReferences;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SendMessageActitvity extends AppCompatActivity {

    @BindView(R.id.user_profile)
    CircleImageView profileImage;

    @BindView(R.id.name)
    AppCompatTextView name;

    @BindView(R.id.message_text)
    AppCompatEditText messageText;

    @BindView(R.id.activity_send_message)
    View mView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message_actitvity);


        ButterKnife.bind(this);

        if (MessageNavigationClassParser.recpient != null) {
            User rec = MessageNavigationClassParser.recpient;
            Glide.with(this).load(rec.getProfileUrl()).into(profileImage);
            name.setText(rec.getName());
        }


        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MessageNavigationClassParser.recpient != null) {
                    User rec = MessageNavigationClassParser.recpient;
                    String msgT = messageText.getText().toString();
                    if (msgT.length() > 2) {
                        Post msg = new Post();
                        msg.setMessage(msgT);
                        msg.setAuthorId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        msg.setTimeStamp(new Date().getTime());
                        msg.setRecipientId(rec.getId());
                        FirebaseDatabase.getInstance().getReference().child("messages").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("outbox").push().setValue(msg);
                        FirebaseDatabase.getInstance().getReference().child("messages").child(rec.getId()).child("inbox").push().setValue(msg).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                messageText.setText("");
                                Snackbar.make(mView, "Message Sent!!", Snackbar.LENGTH_SHORT).show();
                                fab.setEnabled(true);
                            }
                        });
                        fab.setEnabled(false);
                    }
                    else {
                        Snackbar.make(mView, "Please Enter a message", Snackbar.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    void close(View view) {
        finish();
    }

}
