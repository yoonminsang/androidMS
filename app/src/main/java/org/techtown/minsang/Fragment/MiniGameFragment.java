package org.techtown.minsang.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import org.techtown.minsang.MiniGame_Dudage_Activity;
import org.techtown.minsang.R;

public class MiniGameFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mini_game, container, false);
        Button bt_game_dudage = (Button) v.findViewById(R.id.bt_game_dudage);
        bt_game_dudage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MiniGame_Dudage_Activity.class);
                startActivity(intent);
            }
        });
        return v;
    }
}
