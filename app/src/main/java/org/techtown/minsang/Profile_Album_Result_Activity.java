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

public class Profile_Album_Result_Activity extends AppCompatActivity {

    private ArrayList<AlbumData> arrayList;
    private ArrayList<AlbumSharedData> getArrayList;
    AlbumData albumData;
    TextView tx_album_result_date;
    ImageView im_album_result;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__album__result_);

        arrayList = new ArrayList<>();
        loadData();
        for (int i = 0; i < getArrayList.size(); i++) {
            byte[] decodedString = Base64.decode(getArrayList.get(i).getAlbumimage(), Base64.DEFAULT);
            Bitmap image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            albumData = new AlbumData(getArrayList.get(i).getAlbumdate(), image);
            arrayList.add(albumData);
        }

        tx_album_result_date = (TextView) findViewById(R.id.tx_album_result_date);
        im_album_result = (ImageView)findViewById(R.id.im_album_result);

        pos= getIntent().getIntExtra("pos",0);

        tx_album_result_date.setText(arrayList.get(pos).getAlbumdate());
        im_album_result.setImageBitmap(arrayList.get(pos).getAlbumimage());

        Button bt_album_result_previous = (Button) findViewById(R.id.bt_album_result_previous);
        bt_album_result_previous.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(pos>=1){
                    pos--;
                    tx_album_result_date.setText(arrayList.get(pos).getAlbumdate());
                    im_album_result.setImageBitmap(arrayList.get(pos).getAlbumimage());
                }
            }
        });

        Button bt_album_result_next = (Button) findViewById(R.id.bt_album_result_next);
        bt_album_result_next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(pos+1<arrayList.size()){
                    pos++;
                    tx_album_result_date.setText(arrayList.get(pos).getAlbumdate());
                    im_album_result.setImageBitmap(arrayList.get(pos).getAlbumimage());
                }
            }
        });
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("album", 0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("ab"+Login.loginpos, null);
        Type type = new TypeToken<ArrayList<AlbumSharedData>>() {
        }.getType();
        getArrayList = gson.fromJson(json, type);
        if (getArrayList == null) {
            getArrayList = new ArrayList<>();
        }
    }
}
