package pl.tkjm.tasklist;

import java.io.Serializable;

public class Task implements Serializable {
    private long id;
    private String title;
    private String description;
    private String duration;
    private String date;

    public Task(String title, String description, String date, String duration) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return title;
    }
}
