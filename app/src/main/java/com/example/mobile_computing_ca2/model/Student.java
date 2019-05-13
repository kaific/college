package com.example.mobile_computing_ca2.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Student {

    public static Student getStudent(JSONObject jsonObject) throws JSONException {
        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        String address = jsonObject.getString("address");
        String email = jsonObject.getString("email");
        String phone = jsonObject.getString("phone");
        Student student = new Student(id, name, address, email, phone);

        return student;
    }

    public static List<Student> getStudents(JSONArray jsonArray) throws JSONException {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i != jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Student student = Student.getStudent(jsonObject);
            students.add(student);
        }
        return students;
    }

    private int id;
    private String name;
    private String address;
    private String email;
    private String phone;

    public Student(int id, String name, String address, String email, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;

        if (obj != null && obj instanceof Student) {
            Student that = (Student) obj;
            if (this.id == that.id) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
