package ibcs.cs_ia_serviceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.DialogUtils;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;

public class SendRequestActivity extends AppCompatActivity implements View.OnClickListener {

    //UI
    private Button bChoose;
    private Button bSubmit;
    private ImageView imageView;

    //Firebase
    private StorageReference storageRef;

    //fields
    private String uid;
    private final int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_request_activity);
        bChoose = findViewById(R.id.choose);
        bChoose.setOnClickListener(this);
        bSubmit = findViewById(R.id.upload_request);
        bSubmit.setOnClickListener(this);
        imageView = findViewById(R.id.img_view);

        uid = UserSharedPreferences.getInstance(SendRequestActivity.this).getStringInfo(Constants.UID_KEY);
        storageRef = FirebaseStorage.getInstance().getReference().child(uid);
    }

    //https://code.tutsplus.com/tutorials/image-upload-to-firebase-in-android-application--cms-29934
    private void chooseImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //https://code.tutsplus.com/tutorials/image-upload-to-firebase-in-android-application--cms-29934
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    //https://code.tutsplus.com/tutorials/image-upload-to-firebase-in-android-application--cms-29934
    private void uploadImage()
    {
        if(filePath != null)
        {
            final ProgressDialog popup = DialogUtils.showProgressDialog(this, "Uploading...");
            //TODO: ADD THIS TIMESTAMP TO REQUEST OBJECT SO IT'LL BE EASIER TO RETRIEVE
            String timestamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
            storageRef.child(timestamp + ".jpg").putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    Toast.makeText(SendRequestActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(SendRequestActivity.this, "Failed. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            popup.dismiss();
            imageView.setImageBitmap(null);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(bChoose.getId() == i)
        {
            chooseImage();
        }
        if(bSubmit.getId() == i)
        {
            uploadImage();
        }
    }
}
