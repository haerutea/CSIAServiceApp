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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.object_classes.Request;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.SubmittedRequestsAdapter;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;

public class SubmittedRequestListActivity extends BaseActivity
{
    //UI
    private LinearLayoutManager linearLayoutManager;

    private SubmittedRequestsAdapter adapter;
    private ArrayList<Request> requestsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.submitted_request_list_activity, (ViewGroup) findViewById(R.id.contents));

        requestsList = new ArrayList<>();
        final TaskCompletionSource<String> getAllRequestsTask = new TaskCompletionSource<>();
        String account = UserSharedPreferences.getInstance(this).getStringInfo(Constants.ACCOUNT_TYPE_KEY);
        final String uid = UserSharedPreferences.getInstance(this).getStringInfo(Constants.UID_KEY);
        if(account.equals(Constants.ACCOUNT_CUSTOMER))
        {
            final ArrayList<String> userRidList = new ArrayList<>();
            final TaskCompletionSource<String> getUserRidTask = new TaskCompletionSource<>();
            Constants.USER_REFERENCE.child(uid).child(Constants.REQUESTS_SUBMITTED_PATH).addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    for(DataSnapshot rid : dataSnapshot.getChildren())
                    {
                        userRidList.add(rid.getKey());
                    }
                    getUserRidTask.setResult(null);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
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
                            for(DataSnapshot tempRid : dataSnapshot.getChildren())
                            {
                                if(userRidList.contains(tempRid.getKey()))
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

        }
        else if(account.equals(Constants.ACCOUNT_PROVIDER))
        {
            //https://stackoverflow.com/questions/35552571/how-to-use-firebase-query-equaltovalue-key
            Query onlyUnaccepted = Constants.REQUEST_REFERENCE.orderByChild(Constants.ACCEPTED_KEY).equalTo(false);
            onlyUnaccepted.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if(dataSnapshot.exists())
                    {
                        for(DataSnapshot rid : dataSnapshot.getChildren())
                        {
                            Request tempReq = rid.getValue(Request.class);
                            requestsList.add(tempReq);
                        }
                        getAllRequestsTask.setResult(null);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    getAllRequestsTask.setException(databaseError.toException());
                }
            });
        }

        getAllRequestsTask.getTask().addOnCompleteListener(new OnCompleteListener<String>()
        {
            @Override
            public void onComplete(@NonNull Task<String> task)
            {
                System.out.println(requestsList.size());
                linearLayoutManager = new LinearLayoutManager(SubmittedRequestListActivity.this);
                adapter = new SubmittedRequestsAdapter(requestsList);
                RecyclerView requests = findViewById(R.id.submitted_requests_recycler);
                requests.setLayoutManager(linearLayoutManager);
                requests.setAdapter(adapter);
            }
        });
    }
}
