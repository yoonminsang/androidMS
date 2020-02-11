package org.techtown.minsang;

import android.app.Activity;
import android.widget.Toast;

public class BackPressedForFinish {

    private long backKeyPressedTime = 0;   // '뒤로' 버튼을 클릭했을 때의 시간
    private long timeInterval = 2000;      // 첫번째 버튼 클릭과 두번째 버튼 클릭 사이의 종료를 위한 시간차를 정의
    private Toast toast;                   // 종료 안내 문구 Toast
    private Activity activity;             // 종료할 액티비티의 Activity 객체

    public BackPressedForFinish(Activity act) {
        this.activity = act;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + timeInterval) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(activity, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.cancel();
            activity.finish();
        }
    }
}