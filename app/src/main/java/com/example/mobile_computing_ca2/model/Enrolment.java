package com.example.mobile_computing_ca2.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Enrolment implements Comparable<Enrolment> {

    public static final String REGISTERED = "registered";
    public static final String ATTENDING = "attending";
    public static final String DEFERRED = "deferred";
    public static final String WITHDRAWN = "withdrawn";

    public static Enrolment getEnrolment(JSONObject jsonObject) throws JSONException {
        int id = jsonObject.getInt("id");
        String date = jsonObject.getString("date");
        String time = jsonObject.getString("time");
        int courseId = jsonObject.getInt("course_id");
        int studentId = jsonObject.getInt("student_id");
        String status = jsonObject.getString("status");
        Enrolment enrolment = new Enrolment(id, date, time, courseId, studentId, status);

        return enrolment;
    }

    public static List<Enrolment> getEnrolments(JSONArray jsonArray) throws JSONException {
        List<Enrolment> enrolments = new ArrayList<>();
        for (int i = 0; i != jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Enrolment enrolment = Enrolment.getEnrolment(jsonObject);
            Course course = Course.getCourse(jsonObject.getJSONObject("course"));
            Student student = Student.getStudent(jsonObject.getJSONObject("student"));

            enrolment.setCourse(course);
            enrolment.setStudent(student);

            enrolments.add(enrolment);
        }

        return enrolments;
    }

    private int id;
    private String date;
    private String time;
    private int courseId;
    private int studentId;
    private String status;
    private Course course;
    private Student student;

    public Enrolment(int id, String date, String time, int courseId, int studentId, String status) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.courseId  = courseId;
        this.studentId = studentId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;

        if (obj != null && obj instanceof Enrolment) {
            Enrolment that = (Enrolment) obj;
            if (this.id == that.id) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return date + " @ " + time + ": " + student.getName() + " > " + course.getTitle();
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("date", date);
        jsonObject.put("time", time);
        jsonObject.put("status", status);
        jsonObject.put("course_id", courseId);
        jsonObject.put("student_id", studentId);
        return jsonObject;
    }

    @Override
    public int compareTo(Enrolment that) {
        return this.date.compareTo(that.date);
    }
}
