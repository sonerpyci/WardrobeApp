package tr.edu.yildiz.payci.soner.wardrobe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import tr.edu.yildiz.payci.soner.wardrobe.adapters.ViewPagerAdapter;
import tr.edu.yildiz.payci.soner.wardrobe.fragments.CabinetFragment;
import tr.edu.yildiz.payci.soner.wardrobe.fragments.ClothingFragment;
import tr.edu.yildiz.payci.soner.wardrobe.fragments.EventFragment;
import tr.edu.yildiz.payci.soner.wardrobe.fragments.WardrobeFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private Context mContext;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);

        ArrayList<Fragment> fragList = new ArrayList<>();
        fragList.add(new EventFragment());
        fragList.add(new CabinetFragment());
        fragList.add(new WardrobeFragment());
        fragList.add(new ClothingFragment());
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(fragList, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        bnv.setOnNavigationItemSelectedListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.event:
                viewPager.setCurrentItem(0);
                break;
            case R.id.cabinet:
                viewPager.setCurrentItem(1);
                break;
            case R.id.wardrobe:
                viewPager.setCurrentItem(2);
                break;
            case R.id.clothing:
                viewPager.setCurrentItem(3);
                break;
        }
        return true;
    }
}