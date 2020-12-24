package com.google.notebook;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class detailnote extends AppCompatActivity {
    Intent data;
    modelNote note;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailnote);

        Toolbar toolbar = findViewById(R.id.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data = getIntent();
        note = (modelNote) data.getSerializableExtra("note");


        TextView content = findViewById(R.id.noteDetailsContent);
        TextView title = findViewById(R.id.noteDetailsTitle);
        TextView date = findViewById(R.id.noteDetailsDate);
        content.setMovementMethod(new ScrollingMovementMethod());

        content.setText(note.getDesc());
        title.setText(note.getTitle());
        date.setText(note.getDate());
        content.setBackgroundColor(getResources().getColor(data.getIntExtra("code",0),null));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Intent i = new Intent(v.getContext(), edite.class);
                                       i.putExtra("note",note);
                                       startActivity(i);
                                   }

        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}