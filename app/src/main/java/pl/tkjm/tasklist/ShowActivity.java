package pl.tkjm.tasklist;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                taskDao.delete(taskList.get(i));
                setListItems();
                itemsAdapter.clear();
                itemsAdapter.addAll(taskList);

                return true;
            }
        });

    }

    private void setListItems() {
        taskList = taskDao.getAll();
    }

}
