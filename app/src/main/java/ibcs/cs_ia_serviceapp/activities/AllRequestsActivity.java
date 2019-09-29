package ibcs.cs_ia_serviceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.object_classes.Request;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.RequestAdapter;

public class AllRequestsActivity extends AppCompatActivity
{
    //UI
    private LinearLayoutManager linearLayoutManager;

    private RequestAdapter adapter;
    private ArrayList<Request> allRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_requests_activity);

        allRequests = new ArrayList<>();
        final TaskCompletionSource<String> getAllRequestsTask = new TaskCompletionSource<>();
        Constants.REQUEST_REFERENCE.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot uids : dataSnapshot.getChildren())
                {
                    for(DataSnapshot data : uids.getChildren())
                    {
                        Request tempRequest = data.getValue(Request.class);
                        allRequests.add(tempRequest);
                    }
                }
                getAllRequestsTask.setResult(null);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                getAllRequestsTask.setException(databaseError.toException());
            }
        });

        getAllRequestsTask.getTask().addOnCompleteListener(new OnCompleteListener<String>()
        {
            @Override
            public void onComplete(@NonNull Task<String> task)
            {
                System.out.println(allRequests.size());
                adapter = new RequestAdapter(allRequests);
                RecyclerView chat = findViewById(R.id.all_requests_recycler);
                chat.setAdapter(adapter);
            }
        });

    }
}
