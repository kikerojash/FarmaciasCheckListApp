package farmacias.peruanas.com.farmaciaschecklistapp.main;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import farmacias.peruanas.com.farmaciaschecklistapp.R;
import farmacias.peruanas.com.farmaciaschecklistapp.main.adapter.MyFragmentAdapter;
import farmacias.peruanas.com.farmaciaschecklistapp.main.preguntas.PreguntasFragment;
import farmacias.peruanas.com.farmaciaschecklistapp.main.respuestas.RespuestasFragment;
import farmacias.peruanas.com.farmaciaschecklistapp.utils.AppConstants;
import farmacias.peruanas.com.farmaciaschecklistapp.utils.SecurePreferences;
import timber.log.Timber;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabMenuOptions)
    TabLayout tabLayout;
    private SecurePreferences securePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        securePreferences = new SecurePreferences(MainActivity.this, AppConstants.KEY_PREFERENCES_CHECKLIST, true);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        initViewPager();
    }

    private void initViewPager() {
        final MyFragmentAdapter fragmentAdapter = new MyFragmentAdapter(this.getSupportFragmentManager());
        fragmentAdapter.addFragment(PreguntasFragment.newInstance(), "Preguntas");
        fragmentAdapter.addFragment(RespuestasFragment.newInstance(), "Respuestas");
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        initDataLogin();
    }

    private void initDataLogin() {
        HashMap<String, String> userDetails = securePreferences.getUserDetails();
        String keyEmpresa = userDetails.get(SecurePreferences.KEY_EMPRESA);
        Timber.d("keyEmpresa : " + keyEmpresa);
        switch (keyEmpresa) {
            case "INKA":
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color_inka)));
                tabLayout.setBackground(new ColorDrawable(getResources().getColor(R.color.color_inka)));
                fab.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.color_inka2)));
                break;
            case "MIFA":
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color_mifa)));
                tabLayout.setBackground(new ColorDrawable(getResources().getColor(R.color.color_mifa)));
                fab.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.colorPrimaryDark)));
                break;
            case "FARMA":
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                tabLayout.setBackground(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                fab.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.colorPrimaryDark)));
                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            desloguearUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void desloguearUser() {
        ActivityCompat.finishAffinity(MainActivity.this);
        securePreferences.logoutUser();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAffinity(MainActivity.this);
    }*/
}
