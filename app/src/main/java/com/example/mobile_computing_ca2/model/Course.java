package com.example.mobile_computing_ca2.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Course {
    
    public static Course getCourse(JSONObject jsonObject) throws JSONException {
        int id = jsonObject.getInt("id");
        String title = jsonObject.getString("title");
        String code = jsonObject.getString("code");
        String description = jsonObject.getString("description");
        int points = jsonObject.getInt("points");
        int level = jsonObject.getInt("level");
        Course course = new Course(id, title, code, description, points, level);

        return course;
    }

    public static List<Course> getCourses(JSONArray jsonArray) throws JSONException {
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i != jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Course course = Course.getCourse(jsonObject);
            courses.add(course);
        }
        return courses;
    }
    
    private int id;
    private String title;
    private String code;
    private String description;
    private int points;
    private int level;

    public Course(int id, String title, String code, String description, int points, int level) {
        this.id = id;
        this.title = title;
        this.code = code;
        this.description = description;
        this.points = points;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;

        if (obj != null && obj instanceof Course) {
            Course that = ((Course) obj);
            if (this.id == that.id) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return title;
    }
}
