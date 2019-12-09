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
import ibcs.cs_ia_serviceapp.utils.RequestAdapter;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;

public class RequestListActivity extends BaseActivity
{
    //UI
    private LinearLayoutManager linearLayoutManager;

    private RequestAdapter adapter;
    private ArrayList<Request> requestsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.request_list_activity, (ViewGroup) findViewById(R.id.contents));

        requestsList = new ArrayList<>();
        final TaskCompletionSource<String> getAllRequestsTask = new TaskCompletionSource<>();
        String account = UserSharedPreferences.getInstance(this).getStringInfo(Constants.ACCOUNT_TYPE_KEY);
        String uid = UserSharedPreferences.getInstance(this).getStringInfo(Constants.UID_KEY);
        if(account.equals(Constants.ACCOUNT_CUSTOMER))
        {
            Constants.REQUEST_REFERENCE.child(uid).addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    populateRequests(dataSnapshot);
                    getAllRequestsTask.setResult(null);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    getAllRequestsTask.setException(databaseError.toException());
                }
            });
        }
        else if(account.equals(Constants.ACCOUNT_PROVIDER))
        {

            Constants.REQUEST_REFERENCE.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    for(DataSnapshot uids : dataSnapshot.getChildren())
                    {
                        //TODO: FIGURE OUT HOW TO SORT ACCEPTED FROM NOT ACCEPTED https://stackoverflow.com/questions/45891437/how-to-query-firebase-database-and-not-show-values-based-of-a-boolean-value
                        Query query = Constants.REQUEST_REFERENCE.child(uids.getKey()).orderByChild("accepted").equalTo("true");
                        populateRequests(uids);
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

        getAllRequestsTask.getTask().addOnCompleteListener(new OnCompleteListener<String>()
        {
            @Override
            public void onComplete(@NonNull Task<String> task)
            {
                System.out.println(requestsList.size());
                linearLayoutManager = new LinearLayoutManager(RequestListActivity.this);
                adapter = new RequestAdapter(requestsList);
                RecyclerView requests = findViewById(R.id.all_requests_recycler);
                requests.setLayoutManager(linearLayoutManager);
                requests.setAdapter(adapter);
            }
        });
    }

    private void populateRequests(DataSnapshot parent)
    {
        for(DataSnapshot data : parent.getChildren())
        {
            Request tempRequest = data.getValue(Request.class);
            requestsList.add(tempRequest);
        }
    }
}
