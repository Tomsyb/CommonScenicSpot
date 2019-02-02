package com.android.daqsoft.androidbasics;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.daqsoft.androidbasics.base.IApplication;
import com.android.daqsoft.androidbasics.ui.fragment.index.IndexFragment;
import com.android.daqsoft.androidbasics.ui.fragment.index.IndexScenicTravelsFragment;
import com.android.daqsoft.androidbasics.utils.BarUtils;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.utils.StatusBarCompat;
import com.android.daqsoft.androidbasics.utils.img.GlideUtils;
import com.android.daqsoft.androidbasics.view.RoundImageView;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class Main2Activity extends SupportActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        BarUtils.setNavBarVisibility(this,false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        if (findFragment(IndexFragment.class) == null) {
            loadRootFragment(R.id.fl_container, IndexFragment.newInstance());
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        RoundImageView img = (RoundImageView) headerView.findViewById(R.id.head_imageView);
        TextView account = (TextView) headerView.findViewById(R.id.account);
        TextView bottom = (TextView)headerView.findViewById(R.id.tv_bottom);
        TextView tel = (TextView)headerView.findViewById(R.id.tel);
        GlideUtils.GlideImg(this, IApplication.SP.getString("imghead"),img);
        bottom.setText(IApplication.SP.getString("bottomtag"));
        account.setText(IApplication.SP.getString("account"));
        tel.setText(IApplication.SP.getString("tel"));
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void openDraw(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(Gravity.LEFT);
        }
    }
    @Override
    public void onBackPressedSupport() {
        // 对于4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressedSupport();
        }

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultNoAnimator();
    }

    /**
     * start other BrotherFragment
     * 就是和这个Fragment同级
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            loadRootFragment(R.id.fl_container, IndexScenicTravelsFragment.newInstance("3120IEGA4400"));
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
