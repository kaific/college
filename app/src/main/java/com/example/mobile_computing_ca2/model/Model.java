package com.example.mobile_computing_ca2.model;

import android.app.Application;

import com.example.mobile_computing_ca2.model.api.API;
import com.example.mobile_computing_ca2.model.api.APIListener;
import com.example.mobile_computing_ca2.model.api.AbstractAPIListener;
import com.example.mobile_computing_ca2.model.api.WebAPI;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private static Model sInstance = null;

    private final API mApi;
    private User mUser;
    private List<Enrolment> mEnrolments;
    private List<Course> mCourses;
    private List<Student> mStudents;

    public static Model getInstance(Application application) {
        if (sInstance == null) {
            sInstance = new Model(application);
        }
        return sInstance;
    }

    private final Application mApplication;

    private Model(Application application) {
        mApplication = application;
        mApi = new WebAPI(mApplication, this);
        mEnrolments = new ArrayList<>();
        mCourses = new ArrayList<>();
        mStudents = new ArrayList<>();
    }

    public Application getApplication() {
        return mApplication;
    }

    public void login(String email, String password, APIListener listener) {
        mApi.login(email, password, listener);
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        this.mUser = user;
    }

    public List<Enrolment> getEnrolments() {
        return mEnrolments;
    }

    public Enrolment findEnrolmentById(int enrolmentId) {
        Enrolment enrolment = null;

        for (Enrolment v : mEnrolments) {
            if (v.getId() == enrolmentId) {
                enrolment = v;
                break;
            }
        }
        return enrolment;
    }

    public void storeEnrolment(Enrolment enrolment) {
        mEnrolments.add(enrolment);
    }

    public void loadEnrolments(APIListener listener) {
        mApi.loadEnrolments(listener);
    }

    public List<Course> getCourses() {
        return mCourses;
    }

    public Course findCourseById(int courseId) {
        Course course = null;

        for (Course c : mCourses) {
            if (c.getId() == courseId) {
                course = c;
                break;
            }
        }
        return course;
    }

    public void addCourses(List<Course> courses) {
        mCourses.clear();
        mCourses.addAll(courses);
    }

    public void loadCourses(AbstractAPIListener listener) {
        mApi.loadCourses(listener);
    }

    public List<Student> getStudents() {
        return mStudents;
    }

    public Student findStudentById(int studentId) {
        Student student = null;

        for (Student s : mStudents) {
            if (s.getId() == studentId) {
                student = s;
                break;
            }
        }
        return student;
    }

    public void addStudents(List<Student> students) {
        mStudents.clear();
        mStudents.addAll(students);
    }

    public void loadStudents(AbstractAPIListener listener) {
        mApi.loadStudents(listener);
    }

    public void deleteEnrolment(Enrolment deletedEnrolment) {
        mEnrolments.remove(deletedEnrolment);
    }

    public void deleteEnrolment(Enrolment enrolment, APIListener apiListener) {
        mApi.deleteEnrolment(enrolment, apiListener);
    }

    public void storeEnrolment(Enrolment enrolment, APIListener listener) {
        mApi.storeEnrolment(enrolment, listener);
    }

    public void updateEnrolment(Enrolment enrolment, APIListener listener) {
        mApi.updateEnrolment(enrolment, listener);
    }
}

