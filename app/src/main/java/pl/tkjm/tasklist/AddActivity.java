package pl.tkjm.tasklist;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final Calendar myCalendar = Calendar.getInstance();
        final EditText dateText = findViewById(R.id.editTextDate);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(dateText, myCalendar);
            }

        };

        dateText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        SQLiteOpenHelper openHelper = new OpenHelper(this);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        final TaskDao taskDao = new TaskDao(db);

        Button addButton = findViewById(R.id.addingButton);
        final EditText title = findViewById(R.id.editTextTitle);
        final EditText description = findViewById(R.id.editTextDesc);
        final EditText duration = findViewById(R.id.editTextDuration);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task(title.getText().toString(), description.getText().toString(), dateText.getText().toString(), duration.getText().toString());
                taskDao.save(task);

                title.getText().clear();
                description.getText().clear();
                duration.getText().clear();
                dateText.getText().clear();

                Toast.makeText(v.getContext(), "Task created", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void updateLabel(EditText edittext, Calendar myCalendar) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }
}
