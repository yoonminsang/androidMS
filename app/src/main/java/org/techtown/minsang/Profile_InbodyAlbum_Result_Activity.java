package org.techtown.minsang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Profile_InbodyAlbum_Result_Activity extends AppCompatActivity {

    private ArrayList<InbodyAlbumData> arrayList;
    private ArrayList<InbodyAlbumSharedData> getArrayList;
    InbodyAlbumData inbodyAlbumData;
    TextView tx_inbodyalbum_result_date;
    ImageView im_inbodyalbum_result;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__inbody_album__result_);
        arrayList = new ArrayList<>();
        loadData();
        for (int i = 0; i < getArrayList.size(); i++) {
            byte[] decodedString = Base64.decode(getArrayList.get(i).getAlbumimage(), Base64.DEFAULT);
            Bitmap image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            inbodyAlbumData = new InbodyAlbumData(getArrayList.get(i).getAlbumdate(), image);
            arrayList.add(inbodyAlbumData);
        }

        tx_inbodyalbum_result_date = (TextView) findViewById(R.id.tx_inbodyalbum_result_date);
        im_inbodyalbum_result = (ImageView)findViewById(R.id.im_inbodyalbum_result);

        pos= getIntent().getIntExtra("pos",0);

        tx_inbodyalbum_result_date.setText(arrayList.get(pos).getAlbumdate());
        im_inbodyalbum_result.setImageBitmap(arrayList.get(pos).getAlbumimage());

        Button bt_inbodyalbum_result_previous = (Button) findViewById(R.id.bt_inbodyalbum_result_previous);
        bt_inbodyalbum_result_previous.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(pos>=1){
                    pos--;
                    tx_inbodyalbum_result_date.setText(arrayList.get(pos).getAlbumdate());
                    im_inbodyalbum_result.setImageBitmap(arrayList.get(pos).getAlbumimage());
                }
            }
        });

        Button bt_inbodyalbum_result_next = (Button) findViewById(R.id.bt_inbodyalbum_result_next);
        bt_inbodyalbum_result_next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(pos+1<arrayList.size()){
                    pos++;
                    tx_inbodyalbum_result_date.setText(arrayList.get(pos).getAlbumdate());
                    im_inbodyalbum_result.setImageBitmap(arrayList.get(pos).getAlbumimage());
                }
            }
        });
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("inbodyalbum", 0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("ab"+Login.loginpos, null);
        Type type = new TypeToken<ArrayList<InbodyAlbumSharedData>>() {
        }.getType();
        getArrayList = gson.fromJson(json, type);
        if (getArrayList == null) {
            getArrayList = new ArrayList<>();
        }
    }
}