package pl.tkjm.tasklist;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        SQLiteOpenHelper openHelper = new OpenHelper(this);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        TaskDao taskDao = new TaskDao(db);

        List<Task> taskList = taskDao.getAll();

        MyCustomAdapter adapter = new MyCustomAdapter(taskList, this, taskDao);
        ListView lView = findViewById(R.id.taskListView);
        lView.setAdapter(adapter);
    }
}
