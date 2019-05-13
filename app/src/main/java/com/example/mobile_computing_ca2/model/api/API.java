package com.example.mobile_computing_ca2.model.api;

import com.example.mobile_computing_ca2.model.Enrolment;

public interface API {
    void login(String email, String password, APIListener listener);

    void loadEnrolments(APIListener listener);
    void storeEnrolment(Enrolment enrolment, APIListener listener);
    void updateEnrolment(Enrolment enrolment, APIListener listener);
    void deleteEnrolment(Enrolment enrolment, APIListener listener);

    void loadCourses(APIListener listener);
    void loadStudents(APIListener listener);

}
