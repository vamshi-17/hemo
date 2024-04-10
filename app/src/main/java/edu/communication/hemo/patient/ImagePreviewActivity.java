package edu.communication.hemo.patient;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import edu.communication.hemo.R;

public class ImagePreviewActivity extends AppCompatActivity {
    String imageURL;
    ImageView ivPrescription;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        this.ivPrescription = (ImageView) findViewById(R.id.iv_prescription);
        this.imageURL = getIntent().getExtras().getString("imageURL");
        Glide.with((FragmentActivity) this).load(this.imageURL).into(this.ivPrescription);
    }
}
