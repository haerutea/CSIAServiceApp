package ibcs.cs_ia_serviceapp.activities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

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
import ibcs.cs_ia_serviceapp.utils.OngoingRequestsAdapter;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;

public class OngoingRequestsListActivity extends BaseActivity
{
    //UI
    private LinearLayoutManager linearLayoutManager;

    private OngoingRequestsAdapter adapter;
    private ArrayList<Request> requestsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.ongoing_requests_list_activity, (ViewGroup) findViewById(R.id.contents));

        requestsList = new ArrayList<>();
        final TaskCompletionSource<String> getAllRequestsTask = new TaskCompletionSource<>();
        final String uid = UserSharedPreferences.getInstance(this).getStringInfo(Constants.UID_KEY);
        final ArrayList<String> userRidList = new ArrayList<>();
        final TaskCompletionSource<String> getUserRidTask = new TaskCompletionSource<>();
        Constants.USER_REFERENCE.child(uid).child(Constants.REQUESTS_ONGOING_PATH).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot rid : dataSnapshot.getChildren())
                {
                    userRidList.add(rid.getKey());
                }
                getUserRidTask.setResult(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                getUserRidTask.setException(databaseError.toException());
            }
        });
        getUserRidTask.getTask().addOnCompleteListener(new OnCompleteListener<String>()
        {
            @Override
            public void onComplete(@NonNull Task<String> task)
            {
                Constants.REQUEST_REFERENCE.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot tempRid : dataSnapshot.getChildren())
                        {
                            if (userRidList.contains(tempRid.getKey()))
                            {
                                requestsList.add(tempRid.getValue(Request.class));
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
            }
        });

        getAllRequestsTask.getTask().addOnCompleteListener(new OnCompleteListener<String>()
        {
            @Override
            public void onComplete(@NonNull Task<String> task)
            {
                System.out.println(requestsList.size());
                linearLayoutManager = new LinearLayoutManager(OngoingRequestsListActivity.this);
                adapter = new OngoingRequestsAdapter(requestsList);
                RecyclerView requests = findViewById(R.id.ongoing_requests_recycler);
                requests.setLayoutManager(linearLayoutManager);
                requests.setAdapter(adapter);
            }
        });
    }
}
