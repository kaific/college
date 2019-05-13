package com.example.mobile_computing_ca2.model.api;

import com.example.mobile_computing_ca2.model.Course;
import com.example.mobile_computing_ca2.model.Student;
import com.example.mobile_computing_ca2.model.Enrolment;
import com.example.mobile_computing_ca2.model.User;

import java.util.List;

public interface APIListener {
    void onLogin(User user);

    void onEnrolmentsLoaded(List<Enrolment> enrolments);
    void onEnrolmentStored(Enrolment storedEnrolment);
    void onEnrolmentUpdated(Enrolment updatedEnrolment);
    void onEnrolmentDeleted(Enrolment deletedEnrolment);

    void onCoursesLoaded(List<Course> courses);
    void onStudentsLoaded(List<Student> students);
}
