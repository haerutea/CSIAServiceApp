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

/**
 * This activity serves as the base activity for all other activities to extend.  This sets up
 * the side navigation bar.
 */
public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawer;

    /**
     * method called when activities are first opened, sets up navigation bar and adds listener
     * to navigationView.
     * @param savedInstanceState data saved from onSaveInstanceState, not used
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.base_activity);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        updateMenu();
    }

    /**
     * update nav bar menu's string according to account type
     */
    //https://stackoverflow.com/a/34283820
    public void updateMenu()
    {
        Menu menu = navigationView.getMenu();
        String account = UserSharedPreferences.getInstance(this).getStringInfo(Constants.ACCOUNT_TYPE_KEY);
        Log.d("baseActivity", account);
        if (account.equals(Constants.ACCOUNT_CUSTOMER))
        {
            menu.findItem(R.id.menu_requests_list).setTitle(R.string.submitted_requests);
        }
        else if (account.equals(Constants.ACCOUNT_PROVIDER))
        {
            menu.findItem(R.id.menu_requests_list).setTitle(R.string.all_requests);
        }
        navigationView.invalidate();
    }

    /**
     * When the back button of the phone is pressed, close the nav drawer if it was open
     */
    @Override
    public void onBackPressed()
    {
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    /**
     * starts activity intent according to which menu item was pressed
     * @param item item that was selected by user
     * @return always return true
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        //get the item id that was clicked on
        int id = item.getItemId();
        Intent intent = null;
        if (id == R.id.menu_home) //if the user clicked on home
        {
            intent = new Intent(getApplicationContext(), ProfileActivity.class);
        }
        else if (id == R.id.menu_requests_list) //if the user clicked my/all submitted requests
        {
            intent = new Intent(getApplicationContext(), SubmittedRequestsListActivity.class);
        }
        else if (id == R.id.menu_ongoing_requests_list) //if the user clicked on ongoing requests
        {
            intent = new Intent(getApplicationContext(), OngoingRequestsListActivity.class);
        }
        else if (id == R.id.menu_completed_requests_list) //if the user clicked on completed requests
        {
            intent = new Intent(getApplicationContext(), CompletedRequestsListActivity.class);
        }
        else if(id == R.id.menu_my_reviews_list) //if the user clicked on all my reviews
        {
            intent = new Intent(getApplicationContext(), MyReviewsActivity.class);
        }
        else if (id == R.id.menu_settings) //if the user clicked on settings
        {
            intent = new Intent(getApplicationContext(), SettingsActivity.class);
        }
        //start activity with the intent modified with the if statements
        startActivity(intent);
        //close the navigation drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
