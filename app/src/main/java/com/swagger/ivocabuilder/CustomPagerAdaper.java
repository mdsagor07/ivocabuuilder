package com.swagger.ivocabuilder;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class CustomPagerAdaper extends FragmentPagerAdapter {


    public CustomPagerAdaper(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                DayFragment dayFragment = new DayFragment();
                return dayFragment;

            case 1:
                MonthFragment monthFragment = new MonthFragment();
                return monthFragment;

            case 2:
                YearFragment yearFragment = new YearFragment();
                return yearFragment;
             default:
                 return null;
        }
    }


    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Day";
            case 1:
                return "Month";
            case 2:
                return "All Words";
            default:
                return null;
        }
    }
}
