package com.example.mobile_computing_ca2.model.api;

import com.example.mobile_computing_ca2.model.Course;
import com.example.mobile_computing_ca2.model.Student;
import com.example.mobile_computing_ca2.model.Enrolment;
import com.example.mobile_computing_ca2.model.User;

import java.util.List;

public abstract class AbstractAPIListener implements APIListener {
    @Override
    public void onLogin(User user) { }

    @Override
    public void onEnrolmentsLoaded(List<Enrolment> enrolments) { }

    @Override
    public void onCoursesLoaded(List<Course> courses) { }

    @Override
    public void onStudentsLoaded(List<Student> students) { }

    @Override
    public void onEnrolmentStored(Enrolment storedEnrolment) { }

    @Override
    public void onEnrolmentUpdated(Enrolment updatedEnrolment) { }

    @Override
    public void onEnrolmentDeleted(Enrolment deletedEnrolment) { }
}
