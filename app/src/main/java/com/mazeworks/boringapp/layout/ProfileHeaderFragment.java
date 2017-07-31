package com.mazeworks.boringapp.layout;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mazeworks.boringapp.R;
import com.mazeworks.boringapp.util.Events;
import com.mazeworks.boringapp.util.FirebaseDataReferences;
import com.mazeworks.boringapp.util.ImageCompression;
import com.mazeworks.boringapp.util.MessageEvent;
import com.mazeworks.boringapp.util.UserPreferences;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import gun0912.tedbottompicker.TedBottomPicker;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class ProfileHeaderFragment extends Fragment implements TedBottomPicker.OnImageSelectedListener, ImageCompression.ICompressorListener {

    private static final String LOG_TAG = "STORAGE_ERROR";
    private FirebaseUser user;

    private TedBottomPicker tedBottomPicker;


    @BindView(R.id.user_profile)
    CircleImageView mProfileImageView;

    @BindView(R.id.profile_background)
    AppCompatImageView mProfileBackground;

    @BindView(R.id.change_name)
    AppCompatButton mNameBtn;

    @BindView(R.id.progress_indicator)
    ProgressBar mBusyIndicator;

    @BindView(R.id.phone_number)
    AppCompatTextView mPhoneNumber;

    String mPrevName = "";
    private StorageReference storageRef;
    private UserPreferences userUtil;

    public ProfileHeaderFragment() {
    }

    public static ProfileHeaderFragment newInstance() {
        ProfileHeaderFragment fragment = new ProfileHeaderFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference();
        userUtil = UserPreferences.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_header, container, false);
        ButterKnife.bind(this, view);

        tedBottomPicker = new TedBottomPicker.Builder(getContext()).setOnImageSelectedListener(this).create();
        mNameBtn.setText("Enter your name");
        if (userUtil.getUsername() != null) {
            mPrevName = userUtil.getUsername();
            mNameBtn.setText(mPrevName);
        }
        //TODO :Must Clean Up
        else {
            if (user.getDisplayName() != null) {
                userUtil.setUsername(user.getDisplayName());
                mPrevName = userUtil.getUsername();
                mNameBtn.setText(mPrevName);
            }
        }
        if (userUtil.getProfileUrl() != null) {
            Glide.with(ProfileHeaderFragment.this).load(userUtil.getProfileUrl()).asBitmap().into(mProfileImageView);
            Glide.with(ProfileHeaderFragment.this)
                    .load(userUtil.getProfileUrl())
                    .bitmapTransform(new BlurTransformation(getContext(), 25))
                    .into(mProfileBackground);
        }
        //TODO :Must Clean Up
        else {
            if (user.getPhotoUrl() != null) {
                userUtil.setProfileUrl(user.getPhotoUrl().toString());
                Glide.with(ProfileHeaderFragment.this).load(userUtil.getProfileUrl()).asBitmap().into(mProfileImageView);
                Glide.with(ProfileHeaderFragment.this)
                        .load(userUtil.getProfileUrl())
                        .bitmapTransform(new BlurTransformation(getContext(), 25))
                        .into(mProfileBackground);
            }
        }


        return view;
    }

    @OnClick(R.id.change_name)
    void changeDisplayName() {
        if (!mNameBtn.getText().toString().toLowerCase().equals("Enter your name".toLowerCase())) {
            mPrevName = mNameBtn.getText().toString();
        }
        new MaterialDialog.Builder(getContext())
                .title("Enter your name")
                .inputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
                .input("Example :John Doe", mPrevName, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        if (input.length() > 4) {
                            mBusyIndicator.setVisibility(View.VISIBLE);
                            updateUsername(input.toString());
                        } else {
                            Snackbar.make(getView(), "Username is too short", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }).show();

    }

    @OnClick(R.id.profile_image_picker)
    void changeProfileImage() {
        if(!tedBottomPicker.isAdded())
        tedBottomPicker.show(getFragmentManager());
    }

    private void updateUsername(final String name) {
        mNameBtn.setEnabled(false);
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
        user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mBusyIndicator.setVisibility(View.GONE);
                mNameBtn.setEnabled(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Snackbar.make(getView(), "Display name Changed", Snackbar.LENGTH_LONG).show();
                userUtil.setUsername(name);

            }
        });

    }

    private void updateUserProfileUrl(final Uri uri) {
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
        user.updateProfile(profileChangeRequest).addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    FirebaseDataReferences.getUserDatabaseReference().child(user.getUid()).child("profileUrl").setValue(uri.toString());
                    userUtil.setProfileUrl(uri.toString());
                    mBusyIndicator.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onImageSelected(Uri uri) {

        UCrop.of(uri, Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "IMG_CROP" + System.currentTimeMillis() + ".jpg")))
                .withAspectRatio(1, 1)
                .withMaxResultSize(512, 512)
                .start(getActivity());
    }

    private void uploadToStorage(Uri uri) {
        StorageReference riversRef = storageRef.child("images/profiles/" + user.getUid() + "/" + new Date().toString() + ".jpg");

        riversRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        updateUserProfileUrl(downloadUrl);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCompressionCompleted(String filePath) {
        uploadToStorage(Uri.fromFile(new File(filePath)));
    }

    @Override
    public void compresionStarted() {
        mBusyIndicator.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.EVENT == Events.PROFILE_URL_UPDATE) {
            Glide.with(this).load(userUtil.getProfileUrl()).into(mProfileImageView);
            Glide.with(ProfileHeaderFragment.this)
                    .load(userUtil.getProfileUrl())
                    .bitmapTransform(new BlurTransformation(getContext(), 25))
                    .into(mProfileBackground);
        }
        if (event.EVENT == Events.DISPLAY_NAME_UPDATE) {
            mNameBtn.setText(userUtil.getUsername());
        }
        if (event.EVENT == Events.PHONE_CHANGED) {
            mPhoneNumber.setText(userUtil.getPhone());
        }


    }

    public void setCroppedImageUri(Uri uri) {
        ImageCompression imageCompression = new ImageCompression(this);
        imageCompression.execute(Uri.parse(uri.getSchemeSpecificPart()).toString());
    }


}
