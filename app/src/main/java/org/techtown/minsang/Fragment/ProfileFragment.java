package org.techtown.minsang.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import org.techtown.minsang.Profile_Album_Activity;
import org.techtown.minsang.Profile_Competency_Activity;
import org.techtown.minsang.Profile_InbodyAlbum_Activity;
import org.techtown.minsang.Profile_InbodyAlbum_Result_Activity;
import org.techtown.minsang.Profile_Inbody_Activity;
import org.techtown.minsang.Profile_Information_Activity;
import org.techtown.minsang.Profile_Point_Activity;
import org.techtown.minsang.R;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        Button bt_inbody = (Button) v.findViewById(R.id.bt_inbody);
        bt_inbody.setOnClickListener(this);
        Button bt_competency = (Button) v.findViewById(R.id.bt_competency);
        bt_competency.setOnClickListener(this);
        Button  bt_album = (Button) v.findViewById(R.id.bt_album);
        bt_album.setOnClickListener(this);
        Button bt_inbody_album = (Button) v.findViewById(R.id.bt_inbody_album);
        bt_inbody_album.setOnClickListener(this);
        Button  bt_point = (Button) v.findViewById(R.id.bt_point);
        bt_point.setOnClickListener(this);
        Button bt_information = (Button) v.findViewById(R.id.bt_information);
        bt_information.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_inbody:
                Intent intent1=new Intent(getActivity().getApplicationContext(), Profile_Inbody_Activity.class);
                startActivity(intent1);
                break;
            case R.id.bt_competency:
                Intent intent2=new Intent(getActivity().getApplicationContext(), Profile_Competency_Activity.class);
                startActivity(intent2);
                break;
            case R.id.bt_album:
                Intent intent3=new Intent(getActivity().getApplicationContext(), Profile_Album_Activity.class);
                startActivity(intent3);
                break;
            case R.id.bt_inbody_album:
                Intent intent4=new Intent(getActivity().getApplicationContext(), Profile_InbodyAlbum_Activity.class);
                startActivity(intent4);
                break;
            case R.id.bt_point:
                Intent intent5=new Intent(getActivity().getApplicationContext(), Profile_Point_Activity.class);
                startActivity(intent5);
                break;
            case R.id.bt_information:
                Intent intent6=new Intent(getActivity().getApplicationContext(), Profile_Information_Activity.class);
                startActivity(intent6);
                break;
        }
    }
}
