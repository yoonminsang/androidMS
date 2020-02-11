package org.techtown.minsang;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.techtown.minsang.Fragment.CalendarFragment;
import org.techtown.minsang.Fragment.ExerciseRoutineFragment;
import org.techtown.minsang.Fragment.MiniGameFragment;
import org.techtown.minsang.Fragment.NotePadFragment;
import org.techtown.minsang.Fragment.ProfileFragment;

public class FragmentAdapter_main extends FragmentPagerAdapter {

    private int mPageCount;
//    public FragmentAdapter_main(@NonNull FragmentManager fm, int behavior, int mPageCount) {
//        super(fm, behavior);
//        this.mPageCount = mPageCount;
//    }
    public FragmentAdapter_main(FragmentManager fm, int pageCount){
        super(fm);
        this.mPageCount = pageCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                CalendarFragment calendarFragment = new CalendarFragment();
                return calendarFragment;
            case 1:
                NotePadFragment notePadFragment = new NotePadFragment();
                return notePadFragment;
            case 2:
                ExerciseRoutineFragment exerciseRoutineFragment = new ExerciseRoutineFragment();
                return exerciseRoutineFragment;
            case 3:
                MiniGameFragment miniGameFragment = new MiniGameFragment();
                return miniGameFragment;
            case 4:
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mPageCount;
    }
}
