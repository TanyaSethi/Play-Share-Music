package androidapp.musicshare;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends AppCompatActivity {
    public TabLayout mTabLayout;
    public static Bundle myBundle = new Bundle();
    private int[] mTabsIcons = {
            R.drawable.home,
            R.drawable.profile1,
            R.drawable.song};
    private DatabaseReference dbreference1;
    private String userId;
    private String username;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        
        dbreference1 = FirebaseDatabase.getInstance().getReference();
        Intent in = getIntent();
        
        userId = in.getStringExtra("UserId");
        username = in.getStringExtra("Username");
        
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        if (viewPager != null)
            viewPager.setAdapter(pagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(viewPager);

            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = mTabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(pagerAdapter.getTabView(i));
            }

            mTabLayout.getTabAt(0).getCustomView().setSelected(true);
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public final int PAGE_COUNT = 3;

        private final String[] mTabsTitle = {"Home", "Groups", "Player"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View view = LayoutInflater.from(Home.this).inflate(R.layout.custom_tab, null);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            icon.setImageResource(mTabsIcons[position]);
            return view;
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    return FirstFragment.newInstance(1);
                case 1:
                    return SecondFragment.newInstance(2);
                case 2:
                    return FourthFragment.newInstance(3);
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabsTitle[position];
        }
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    /*public void passData(String data) {
        ThirdFragment thirdFragment = new ThirdFragment ();
        Bundle args = new Bundle();
        args.putString(ThirdFragment.DATA_RECEIVE, data);
       thirdFragment .setArguments(args);
        getFragmentManager().beginTransaction()
                .replace(R.id.container,ThirdFragment )
                .commit();
    }*/
}
