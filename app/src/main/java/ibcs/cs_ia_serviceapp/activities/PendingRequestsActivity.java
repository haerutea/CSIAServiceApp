package ibcs.cs_ia_serviceapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import ibcs.cs_ia_serviceapp.R;

public class PendingRequestsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pending_requests_activity);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.pending_requests_activity, (ViewGroup) findViewById(R.id.contents));

    }
}
