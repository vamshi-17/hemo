package edu.communication.hemo.patient;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import edu.communication.hemo.CommonData;
import edu.communication.hemo.R;
import edu.communication.hemo.adapter.MessageAdapter;
import edu.communication.hemo.admin.model.DoctorDetails;
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.custom.Utils;
import edu.communication.hemo.patient.model.ChatDetails;
import edu.communication.hemo.patient.model.PatientDetails;
import java.io.File;
import java.util.ArrayList;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class SendPatientMessageActivity extends AppCompatActivity {
    private static final int REQUEST_CHOOSE_IMAGE = 3;
    ImageButton addImage;
    private Dialog awesomeProgressDialog;
    ArrayList<ChatDetails> chatDetailsArrayList;
    FirebaseDatabase database;
    DoctorDetails doctorDetails;
    private Uri imgFilePath;
    MessageAdapter messageAdapter;
    EditText messageEt;
    DatabaseReference myRef;
    PatientDetails patientDetails;
    private Dialog progressDialog;
    RecyclerView recyclerView;
    ImageButton send;
    StorageReference storageRefer;
    String strAddImage;
    private boolean isDoctorScreen = true;
    private String TAG = SendPatientMessageActivity.class.getSimpleName() + ": --> ";


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        this.patientDetails = (PatientDetails) getIntent().getExtras().getParcelable("patientDetails");
        this.doctorDetails = (DoctorDetails) getIntent().getExtras().getParcelable("doctorDetails");
        this.isDoctorScreen = getIntent().getExtras().getBoolean("isDoctor");
        this.storageRefer = FirebaseStorage.getInstance().getReference(CommonData.IMAGE_UPLOADS);
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        this.recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.send = (ImageButton) findViewById(R.id.send_message_btn);
        this.addImage = (ImageButton) findViewById(R.id.add_image_btn);
        this.messageEt = (EditText) findViewById(R.id.msg_et);
        this.myRef = fireBaseInitialSetup();
        readMessages();
        this.send.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.patient.SendPatientMessageActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (SendPatientMessageActivity.this.validateFields()) {
                    String message_content = SendPatientMessageActivity.this.messageEt.getText().toString();
                    String encodedMessage = Utils.EncodeString(message_content);
                    ChatDetails chatDetails = new ChatDetails();
                    if (SendPatientMessageActivity.this.isDoctorScreen) {
                        chatDetails.setSenderMail(SendPatientMessageActivity.this.doctorDetails.getmEmail());
                        chatDetails.setSender(SendPatientMessageActivity.this.doctorDetails.getmName());
                        chatDetails.setReceiver(SendPatientMessageActivity.this.patientDetails.getName());
                        chatDetails.setReceiverMail(SendPatientMessageActivity.this.patientDetails.getmEmail());
                    } else {
                        chatDetails.setSender(SendPatientMessageActivity.this.patientDetails.getName());
                        chatDetails.setSenderMail(SendPatientMessageActivity.this.patientDetails.getmEmail());
                        chatDetails.setReceiver(SendPatientMessageActivity.this.doctorDetails.getmName());
                        chatDetails.setReceiverMail(SendPatientMessageActivity.this.doctorDetails.getmEmail());
                    }
                    chatDetails.setMessage(encodedMessage);
                    chatDetails.setMessageType("text");
                    chatDetails.setImageURL("");
                    SendPatientMessageActivity.this.sendMessage(chatDetails);
                    SendPatientMessageActivity.this.messageEt.setText("");
                }
            }
        });
        this.addImage.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.patient.SendPatientMessageActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EasyImage.openChooserWithGallery(SendPatientMessageActivity.this, "Choose Picture", 3);
            }
        });
    }

    private DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance();
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }


    public void sendMessage(ChatDetails chatDetails) {
        try {
            this.myRef.child(CommonData.CHATS).push().setValue(chatDetails);
            readMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean validateFields() {
        if (this.messageEt.getText().toString().trim().length() == 0) {
            setFocus(this.messageEt, getResources().getString(R.string.enter_message));
            return false;
        }
        return true;
    }

    private void setFocus(EditText editText, String string) {
        editText.requestFocus();
        editText.setError(Html.fromHtml("<font color='red'>" + string + "</font>"));
    }

    private void readMessages() {
        this.chatDetailsArrayList = new ArrayList<>();
        this.myRef.child(CommonData.CHATS).addValueEventListener(new ValueEventListener() { // from class: edu.communication.hemo.patient.SendPatientMessageActivity.3
            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(DataSnapshot dataSnapshot) {
                ChatDetails chatDetails;
                SendPatientMessageActivity.this.chatDetailsArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1 != null && (chatDetails = (ChatDetails) dataSnapshot1.getValue(ChatDetails.class)) != null && ((chatDetails.getReceiverMail().equals(SendPatientMessageActivity.this.patientDetails.getmEmail()) && chatDetails.getSenderMail().equals(SendPatientMessageActivity.this.doctorDetails.getmEmail())) || (chatDetails.getReceiverMail().equals(SendPatientMessageActivity.this.doctorDetails.getmEmail()) && chatDetails.getSenderMail().equals(SendPatientMessageActivity.this.patientDetails.getmEmail())))) {
                        SendPatientMessageActivity.this.chatDetailsArrayList.add(chatDetails);
                        SendPatientMessageActivity sendPatientMessageActivity = SendPatientMessageActivity.this;
                        sendPatientMessageActivity.messageAdapter = new MessageAdapter(sendPatientMessageActivity, sendPatientMessageActivity.chatDetailsArrayList, SendPatientMessageActivity.this.patientDetails, SendPatientMessageActivity.this.isDoctorScreen);
                        SendPatientMessageActivity.this.recyclerView.setAdapter(SendPatientMessageActivity.this.messageAdapter);
                    }
                }
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() { // from class: edu.communication.hemo.patient.SendPatientMessageActivity.4
            @Override // pl.aprilapps.easyphotopicker.EasyImage.Callbacks
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                Intent intent = CropImage.activity(Uri.fromFile(imageFile)).getIntent(SendPatientMessageActivity.this);
                SendPatientMessageActivity.this.startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            }

            @Override // pl.aprilapps.easyphotopicker.DefaultCallback, pl.aprilapps.easyphotopicker.EasyImage.Callbacks
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                Utils.showToast(SendPatientMessageActivity.this, e.getMessage());
            }

            @Override // pl.aprilapps.easyphotopicker.DefaultCallback, pl.aprilapps.easyphotopicker.EasyImage.Callbacks
            public void onCanceled(EasyImage.ImageSource source, int type) {
                super.onCanceled(source, type);
            }
        });
        if (requestCode == 203) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == -1) {
                this.imgFilePath = result.getUri();
                Uri uri = this.imgFilePath;
                if (uri != null) {
                    imageUpload(uri);
                } else {
                    Utils.showToast(this, "Something Went Wrong...!");
                }
            } else if (resultCode == 204) {
                Exception error = result.getError();
                Utils.showToast(this, error.toString());
            }
        }
    }

    private void imageUpload(Uri imgFilePath) {
        if (imgFilePath != null) {
            StorageReference storageReference = this.storageRefer.child(imgFilePath.getLastPathSegment());
            this.progressDialog = new AwesomeProgressDialog(this).setMessage(R.string.uploading_image).show();
            storageReference.putFile(imgFilePath).addOnSuccessListener((OnSuccessListener) new OnSuccessListener<UploadTask.TaskSnapshot>() { // from class: edu.communication.hemo.patient.SendPatientMessageActivity.7
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() { // from class: edu.communication.hemo.patient.SendPatientMessageActivity.7.1
                        @Override
                        public void onSuccess(Uri downloadUrl) {
                            SendPatientMessageActivity.this.strAddImage = downloadUrl.toString();
                            String str = SendPatientMessageActivity.this.TAG;
                            Log.d(str, "onSuccess: " + SendPatientMessageActivity.this.strAddImage);
                            SendPatientMessageActivity.this.uploadImageChat(SendPatientMessageActivity.this.strAddImage);
                            Log.d(SendPatientMessageActivity.this.TAG, "onSuccess: ");
                            SendPatientMessageActivity.this.progressDialog.dismiss();
                            Utils.showToast(SendPatientMessageActivity.this, "Uploaded Successfully");
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() { // from class: edu.communication.hemo.patient.SendPatientMessageActivity.6
                @Override // com.google.android.gms.tasks.OnFailureListener
                public void onFailure(Exception e) {
                    String str = SendPatientMessageActivity.this.TAG;
                    Log.d(str, "onFailure: " + e.getMessage());
                    SendPatientMessageActivity.this.progressDialog.dismiss();
                }
            }).addOnProgressListener((OnProgressListener) new OnProgressListener<UploadTask.TaskSnapshot>() { // from class: edu.communication.hemo.patient.SendPatientMessageActivity.5
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double pr = (taskSnapshot.getBytesTransferred() * 100.0d) / taskSnapshot.getTotalByteCount();
                    String str = SendPatientMessageActivity.this.TAG;
                    Log.d(str, "onProgress: Status" + pr);
                }
            });
        }
    }


    public void uploadImageChat(String strAddImage) {
        ChatDetails chatDetails = new ChatDetails();
        if (this.isDoctorScreen) {
            chatDetails.setSenderMail(this.doctorDetails.getmEmail());
            chatDetails.setSender(this.doctorDetails.getmName());
            chatDetails.setReceiver(this.patientDetails.getName());
            chatDetails.setReceiverMail(this.patientDetails.getmEmail());
        } else {
            chatDetails.setSender(this.patientDetails.getName());
            chatDetails.setSenderMail(this.patientDetails.getmEmail());
            chatDetails.setReceiver(this.doctorDetails.getmName());
            chatDetails.setReceiverMail(this.doctorDetails.getmEmail());
        }
        chatDetails.setMessage("");
        chatDetails.setMessageType("image");
        chatDetails.setImageURL(strAddImage);
        sendMessage(chatDetails);
    }
}
