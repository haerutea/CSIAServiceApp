package ibcs.cs_ia_serviceapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import ibcs.cs_ia_serviceapp.R;

public class ViewRequestsActivity extends AppCompatActivity {

    //UI
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_requests_activity);

        imageView = findViewById(R.id.viewall_img_view)
    }
}
