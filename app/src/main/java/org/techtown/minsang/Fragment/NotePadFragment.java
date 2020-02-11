package org.techtown.minsang.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.techtown.minsang.Login;
import org.techtown.minsang.NotePadData;
import org.techtown.minsang.NotePad_RecyclerAdapter;
import org.techtown.minsang.NotePad_Result;
import org.techtown.minsang.R;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class NotePadFragment extends Fragment {
    private ArrayList<NotePadData> arrayList;
    private NotePad_RecyclerAdapter recyclerAdapter;
    private NotePadData notePadData;
    public static final int REQUEST_CODE_NOTEPAD = 101;
    int pst;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_note_pad, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.notepad_RecyclerView);
        recyclerView.setHasFixedSize(true); // 크기일정하게맞추는거양
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();
        loadData();
        recyclerAdapter = new NotePad_RecyclerAdapter(arrayList, getActivity());
        recyclerView.setAdapter(recyclerAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                notePadData = arrayList.get(position);
                pst = position;
                Intent intent = new Intent(getActivity().getBaseContext(), NotePad_Result.class);
                intent.putExtra("title", notePadData.getTitle());
                intent.putExtra("subtitle", notePadData.getContents());
                startActivityForResult(intent, REQUEST_CODE_NOTEPAD);
            }

            @Override
            public void onLongClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("메모장 삭제").setMessage("정말로 삭제하시겠습니까?");

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        arrayList.remove(position);
                        recyclerAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity().getApplicationContext(), "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
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

        Button bt_notepad_add = (Button) v.findViewById(R.id.bt_notepad_add);
        bt_notepad_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTitle = "성공하자";
                String strContents = "민상아";
                notePadData = new NotePadData(strTitle, strContents);
                arrayList.add(0, notePadData); //첫 줄에 삽입
//                arrayList.add(notePadData); //마지막 줄에 삽입
                pst = 0;
                Intent intent = new Intent(getActivity().getBaseContext(), NotePad_Result.class);
                startActivityForResult(intent, REQUEST_CODE_NOTEPAD);
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getTitle().equals("성공하자") && arrayList.get(i).getContents().equals("민상아")) {
                arrayList.remove(i);
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String title = data.getExtras().getString("backtitle");
            String contents = data.getExtras().getString("backcontents");
            notePadData = new NotePadData(title, contents);
            arrayList.set(pst, notePadData);
            recyclerAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private NotePadFragment.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final NotePadFragment.ClickListener clickListener) {
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

    @Override
    public void onStop() {
        super.onStop();
        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("notepad", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("notepadlist"+ Login.loginpos, json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("notepad", 0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("notepadlist"+Login.loginpos, null);
        Type type = new TypeToken<ArrayList<NotePadData>>() {
        }.getType();
        arrayList = gson.fromJson(json, type);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
    }
}