package org.techtown.minsang.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import org.techtown.minsang.ExerciseRoutine_5x5_Activity;
import org.techtown.minsang.R;

public class ExerciseRoutineFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_exercise_routine, container, false);

        Button bt_5x5 = (Button) v.findViewById(R.id.bt_5x5);
        bt_5x5.setOnClickListener(this);
        Button bt_10x3 = (Button) v.findViewById(R.id.bt_10x3);
        bt_10x3.setOnClickListener(this);
        Button  bt_GVT = (Button) v.findViewById(R.id.bt_GVT);
        bt_GVT.setOnClickListener(this);
        Button  bt_8x8 = (Button) v.findViewById(R.id.bt_8x8);
        bt_8x8.setOnClickListener(this);
        Button  bt_3x10 = (Button) v.findViewById(R.id.bt_3x10);
        bt_3x10.setOnClickListener(this);
        Button   bt_5x10 = (Button) v.findViewById(R.id.bt_5x10);
        bt_5x10.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_5x5:
                Intent intent=new Intent(getActivity().getApplicationContext(), ExerciseRoutine_5x5_Activity.class);
                startActivity(intent);
        }
    }
}
