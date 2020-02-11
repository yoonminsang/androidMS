package org.techtown.minsang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NotePad_Result extends AppCompatActivity {
    Button bt_notepad_save;
    EditText ed_notepad_result_title;
    EditText ed_notepad_result_contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_pad__result);

        ed_notepad_result_title = (EditText) findViewById(R.id.ed_notepad_result_title);
        ed_notepad_result_contents = (EditText) findViewById(R.id.ed_notepad_result_contents);

        ed_notepad_result_title.setText(getIntent().getStringExtra("title"));
        ed_notepad_result_contents.setText(getIntent().getStringExtra("contents"));

        bt_notepad_save = (Button) findViewById(R.id.bt_notepad_save);
        bt_notepad_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("backtitle", ed_notepad_result_title.getText().toString());
                intent.putExtra("backcontents", ed_notepad_result_contents.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                Toast.makeText(getApplicationContext(), "저장되었습니다. ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

