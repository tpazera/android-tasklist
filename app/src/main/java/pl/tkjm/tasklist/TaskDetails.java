package pl.tkjm.tasklist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TaskDetails extends Fragment {

    public TaskDetails() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_task_details, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        String description = getArguments().getString("description");
        String date = getArguments().getString("date");
        String duration = getArguments().getString("duration");

        TextView listItemText = getView().findViewById(R.id.details);
        listItemText.setText("Description: " + description + "\n" + "Date: " + date + "\n" + "Duration: " + duration);
    }

}
