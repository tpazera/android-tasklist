package pl.tkjm.tasklist;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ShowActivity extends AppCompatActivity {

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    TaskDao taskDao;
    List<Task> taskList;
    ArrayAdapter<Task> itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        openHelper = new OpenHelper(this);
        db = openHelper.getWritableDatabase();
        taskDao = new TaskDao(db);

        setListItems();

        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        final ListView listView = findViewById(R.id.taskListView);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            taskDao.delete(taskList.get(i));
            setListItems();
            itemsAdapter.clear();
            itemsAdapter.addAll(taskList);

            return true;
        });

    }

    private void setListItems() {
        taskList = taskDao.getAll();
    }

}
