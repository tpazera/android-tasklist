package pl.tkjm.tasklist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteOpenHelper openHelper = new OpenHelper(this);
        SQLiteDatabase db = openHelper.getWritableDatabase();

        TaskDao taskDao = new TaskDao(db);
        List<Task> taskList = taskDao.getAll();
        for(Task t : taskList) {
            Log.i("db", t.getTitle());
        }

        setActivity(R.id.button, AddActivity.class);
        setActivity(R.id.button1, ShowActivity.class);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.MODIFY_AUDIO_SETTINGS},
                1);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                1);

    }

    private void setActivity(int buttonId, final Class activity) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this, activity);
                startActivity(intent);
            }
        });
    }
}
