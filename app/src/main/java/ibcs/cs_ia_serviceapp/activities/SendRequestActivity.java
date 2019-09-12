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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.object_classes.Request;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.DialogUtils;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;

public class SendRequestActivity extends AppCompatActivity implements View.OnClickListener {

    //UI
    private Spinner sLanguageType;
    private Spinner sServiceType;
    private Spinner sPriorityType;
    private Spinner sLocationType;
    private EditText descriptionField;
    private Button bChoose;
    private Button bSubmit;
    private ImageView imageView;

    //Firebase
    private StorageReference storageRef;

    //fields
    private String uid;
    private String selectedLang;
    private String selectedService;
    private String selectedPriority;
    private String selectedLoc;
    private final int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_request_activity);
        selectedLang = "";
        selectedService = "";
        selectedPriority = "";
        selectedLoc = "";

        descriptionField = findViewById(R.id.request_description);
        bChoose = findViewById(R.id.choose);
        bChoose.setOnClickListener(this);
        bSubmit = findViewById(R.id.upload_request);
        bSubmit.setOnClickListener(this);
        imageView = findViewById(R.id.img_view);
        setupSpinners();

        uid = UserSharedPreferences.getInstance(SendRequestActivity.this).getStringInfo(Constants.UID_KEY);
        uid = "hiii";
        storageRef = FirebaseStorage.getInstance().getReference().child(uid);
    }

    private void setupSpinners()
    {
        sLanguageType = findViewById(R.id.language_type_spinner);
        //since items are from an array
        ArrayAdapter<String> langArrAdapter = new ArrayAdapter<String>(SendRequestActivity.this,
                android.R.layout.simple_spinner_item, Constants.LANGUAGES);
        langArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sLanguageType.setAdapter(langArrAdapter);
        //triggered whenever user selects something different
        sLanguageType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedLang = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        sServiceType = findViewById(R.id.service_type_spinner);
        //since items are from an array
        ArrayAdapter<String> servArrAdapter = new ArrayAdapter<String>(SendRequestActivity.this,
                android.R.layout.simple_spinner_item, Constants.SERVICES);
        servArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sServiceType.setAdapter(servArrAdapter);
        //triggered whenever user selects something different
        sServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedService = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        sPriorityType = findViewById(R.id.priority_type_spinner);
        //since items are from an array
        ArrayAdapter<String> priorArrAdapter = new ArrayAdapter<String>(SendRequestActivity.this,
                android.R.layout.simple_spinner_item, Constants.PRIORITY);
        priorArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sPriorityType.setAdapter(priorArrAdapter);
        //triggered whenever user selects something different
        sPriorityType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedPriority = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        sLocationType = findViewById(R.id.location_type_spinner);
        //since items are from an array
        ArrayAdapter<String> locArrAdapter = new ArrayAdapter<String>(SendRequestActivity.this,
                android.R.layout.simple_spinner_item, Constants.LOCATIONS);
        locArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sLocationType.setAdapter(locArrAdapter);
        //triggered whenever user selects something different
        sLocationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedLoc = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
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

    private void submitRequest()
    {
        if(filePath != null)
        {
            final ProgressDialog popup = DialogUtils.showProgressDialog(this, "Uploading...");
            String reqDescription = descriptionField.getText().toString();
            String filename = uploadImage();
            Request newReq =
                    new Request(selectedLang, selectedService, selectedPriority, selectedLoc, reqDescription, filename);
            uploadRequest(newReq);
            popup.dismiss();
        }
    }

    //https://code.tutsplus.com/tutorials/image-upload-to-firebase-in-android-application--cms-29934
    private String uploadImage()
    {
        String timestamp = "";

        //TODO: ADD THIS TIMESTAMP TO REQUEST OBJECT SO IT'LL BE EASIER TO RETRIEVE
        timestamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + ".jpg";
        storageRef.child(timestamp).putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                //Toast.makeText(SendRequestActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
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
        imageView.setImageBitmap(null);
        return timestamp;
    }

    private void uploadRequest(Request inRequest)
    {
        Constants.REQUEST_INSTANCE.child(uid).setValue(inRequest);
        Toast.makeText(SendRequestActivity.this, "Uploaded request", Toast.LENGTH_SHORT).show();
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
            submitRequest();
        }
    }
}
