package org.techtown.minsang;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Profile_Album_Activity extends AppCompatActivity {
    private ArrayList<AlbumData> arrayList;
    private Album_RecyclerAdapter recyclerAdapter;
    private AlbumData albumData;
    private AlbumSharedData albumSharedData;
    private final int GET_GALLERY_IMAGE = 201;
    private String nowDate;
    private ArrayList<AlbumSharedData> getArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__album_);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.album_Recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();
        loadData();
        for (int i = 0; i < getArrayList.size(); i++) {
            byte[] decodedString = Base64.decode(getArrayList.get(i).getAlbumimage(), Base64.DEFAULT);
            Bitmap image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            albumData = new AlbumData(getArrayList.get(i).getAlbumdate(), image);
            arrayList.add(albumData);
        }

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat nowYear = new SimpleDateFormat("yyyy", Locale.KOREAN);
        SimpleDateFormat nowMonth = new SimpleDateFormat("MM", Locale.KOREAN);
        SimpleDateFormat nowDay = new SimpleDateFormat("dd", Locale.KOREAN);
        nowDate = Integer.parseInt(nowYear.format(date)) + "-" + Integer.parseInt(nowMonth.format(date)) + "-" + Integer.parseInt(nowDay.format(date));

        recyclerAdapter = new Album_RecyclerAdapter(arrayList, this);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                int pos = position;
                Intent intent = new Intent(getBaseContext(), Profile_Album_Result_Activity.class);
                intent.putExtra("pos", pos);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile_Album_Activity.this);
                builder.setTitle("사진 삭제").setMessage("정말로 삭제하시겠습니까?");

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        arrayList.remove(position);
                        getArrayList.remove(position);
                        saveData();
                        recyclerAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }));

        Button bt_album_add = (Button) findViewById(R.id.bt_album_add);
        bt_album_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                albumData = new AlbumData(nowDate, img);
                arrayList.add(albumData);
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            albumData.getAlbumimage().compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
            albumSharedData = new AlbumSharedData(encodedImage, nowDate);
            getArrayList.add(albumSharedData);
            saveData();
            recyclerAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영
        } else {
            Toast.makeText(getApplicationContext(), "취소했습니다.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("album", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(getArrayList);
        editor.putString("ab"+Login.loginpos, json);
        editor.apply();
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


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Profile_Album_Activity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Profile_Album_Activity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }
}


