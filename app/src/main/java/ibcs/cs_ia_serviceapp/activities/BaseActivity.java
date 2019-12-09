package ibcs.cs_ia_serviceapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;

//https://stackoverflow.com/questions/16144399/sidebar-in-each-activity
public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.base_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        updateMenu();
    }

    //https://stackoverflow.com/a/34283820
    public void updateMenu()
    {
        Menu menu = navigationView.getMenu();
        String account = UserSharedPreferences.getInstance(this).getStringInfo(Constants.ACCOUNT_TYPE_KEY);
        Log.d("baseActivity", account);
        if(account.equals(Constants.ACCOUNT_CUSTOMER))
        {
            menu.findItem(R.id.menu_requests_list).setTitle(R.string.pending_requests);
        }
        else if(account.equals(Constants.ACCOUNT_PROVIDER))
        {
            menu.findItem(R.id.menu_requests_list).setTitle(R.string.all_requests);
            Log.d("baseActivity", getString(R.string.all_requests));
            menu.removeItem(R.id.menu_in_progress_list);
        }
        navigationView.invalidate();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.menu_home)
        {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.menu_requests_list)
        {
            Intent intent = new Intent(getApplicationContext(), RequestListActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.menu_in_progress_list)
        {
            Intent intent = new Intent(getApplicationContext(), PendingRequestsActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
