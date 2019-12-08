package pl.tkjm.tasklist;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        SQLiteOpenHelper openHelper = new OpenHelper(this);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        final TaskDao taskDao = new TaskDao(db);

        ArrayList<String> titleList = new ArrayList<>();

        List<Task> taskList = taskDao.getAll();
        for (Task task : taskList) {
            titleList.add(task.getTitle());
        }


        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titleList);
        final ListView listView = findViewById(R.id.taskListView);
        listView.setAdapter(itemsAdapter);


    }
}
