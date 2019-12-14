package pl.tkjm.tasklist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private EditText duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiity_add);

        final Calendar myCalendar = Calendar.getInstance();
        final EditText dateTimeText = findViewById(R.id.editTextDate);
        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);

                updateLabel(dateTimeText, myCalendar);
            }
        };

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                new TimePickerDialog(AddActivity.this, time, myCalendar.get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE), true).show();
            }
        };

        dateTimeText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        SQLiteOpenHelper openHelper = new OpenHelper(this);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        final TaskDao taskDao = new TaskDao(db);

        Button addButton = findViewById(R.id.addingButton);
        title = findViewById(R.id.editTextTitle);
        description = findViewById(R.id.editTextDesc);
        duration = findViewById(R.id.editTextDuration);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task(title.getText().toString(), description.getText().toString(), dateTimeText.getText().toString(), duration.getText().toString());
                taskDao.save(task);

                title.getText().clear();
                description.getText().clear();
                duration.getText().clear();
                dateTimeText.getText().clear();

                Toast.makeText(v.getContext(), R.string.task_created, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("title", title.getText().toString());
        editor.putString("description", description.getText().toString());
        editor.putString("duration", duration.getText().toString());
        editor.apply();
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        title.setText(sharedPref.getString("title", ""));
        description.setText(sharedPref.getString("description", ""));
        duration.setText(sharedPref.getString("duration", ""));
    }

    private void updateLabel(EditText edittext, Calendar myCalendar) {
        String myFormat = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }
}
