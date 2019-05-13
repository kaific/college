package com.example.mobile_computing_ca2.model.api;

import android.app.Application;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.mobile_computing_ca2.model.Model;
import com.example.mobile_computing_ca2.model.User;
import com.example.mobile_computing_ca2.model.Course;
import com.example.mobile_computing_ca2.model.Enrolment;
import com.example.mobile_computing_ca2.model.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebAPI implements API {

    //private static final String BASE_URL = "http://10.0.2.2/college-api/public";
    private static final String BASE_URL = "http://10.0.2.2:8000/";

    private final Application mApplication;
    private RequestQueue mRequestQueue;
    private Model mModel;

    public WebAPI(Application application, Model model) {
        mApplication = application;
        mRequestQueue = Volley.newRequestQueue(application);
        mModel = model;
    }

    public void login(String email, String password, final APIListener listener) {
        String url = BASE_URL + "/api/login";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);

            Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        User user = User.getUser(response);
                        if (listener != null) {
                            listener.onLogin(user);
                        }
                    }
                    catch (JSONException e) {
                        Toast.makeText(mApplication, "JSON exception", Toast.LENGTH_LONG).show();
                    }
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mApplication, "Error response", Toast.LENGTH_LONG).show();
                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, successListener, errorListener);
            mRequestQueue.add(request);
        }
        catch (JSONException e) {
            Toast.makeText(mApplication, "JSON exception", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void loadEnrolments(final APIListener listener) {
        String url = BASE_URL + "/api/enrolments";

        Response.Listener<JSONArray> successListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    List<Enrolment> enrolments = Enrolment.getEnrolments(response);
                    if (listener != null) {
                        listener.onEnrolmentsLoaded(enrolments);
                    }
                }
                catch (JSONException e) {
                    Toast.makeText(mApplication, "JSON exception", Toast.LENGTH_LONG).show();
                    System.out.println(response);
                    System.out.println("Error ------");
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mApplication, "Volley error", Toast.LENGTH_LONG).show();
                System.out.println("Error ------");
                System.out.println(error.getMessage());
                System.out.println("Error ------");
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, successListener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " + mModel.getUser().getToken());
                return headers;
            }
        };
        mRequestQueue.add(request);
    }

    @Override
    public void storeEnrolment(Enrolment enrolment, final APIListener listener) {
        final User user = mModel.getUser();
        if (user == null) {
            listener.onEnrolmentStored(null);
        }
        else {
            try {
                String url = BASE_URL + "/api/enrolments";

                JSONObject requestBody = new JSONObject();
                requestBody.put("date", enrolment.getDate());
                requestBody.put("time", enrolment.getTime());
                requestBody.put("status", enrolment.getStatus());
                requestBody.put("course_id", enrolment.getCourseId());
                requestBody.put("student_id", enrolment.getStudentId());

                Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            Enrolment storedEnrolment = Enrolment.getEnrolment(jsonObject);
                            listener.onEnrolmentStored(storedEnrolment);
                        }
                        catch(JSONException ex) {
                            listener.onEnrolmentStored(null);
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onEnrolmentStored(null);
                    }
                };

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST, url, requestBody, responseListener, errorListener){

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "Bearer " + user.getToken());
                        return headers;
                    }
                };

                mRequestQueue.add(request);
            }
            catch (JSONException ex) {
                listener.onLogin(null);
            }
        }
    }

    @Override
    public void updateEnrolment(Enrolment enrolment, final APIListener listener) {
        final User user = mModel.getUser();
        if (user == null) {
            listener.onEnrolmentUpdated(null);
        }
        else {
            try {
                String url = BASE_URL + "/api/enrolments/" + enrolment.getId();

                JSONObject requestBody = new JSONObject();
                requestBody.put("date", enrolment.getDate());
                requestBody.put("time", enrolment.getTime());
                requestBody.put("status", enrolment.getStatus());
                requestBody.put("course_id", enrolment.getCourseId());
                requestBody.put("student_id", enrolment.getStudentId());

                Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            Enrolment updatedEnrolment = Enrolment.getEnrolment(jsonObject);
                            listener.onEnrolmentUpdated(updatedEnrolment);
                        }
                        catch(JSONException ex) {
                            listener.onEnrolmentUpdated(null);
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onEnrolmentUpdated(null);
                    }
                };

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.PUT, url, requestBody, responseListener, errorListener){

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "Bearer " + user.getToken());
                        return headers;
                    }
                };

                mRequestQueue.add(request);
            }
            catch (JSONException ex) {
                listener.onLogin(null);
            }
        }
    }

    @Override
    public void deleteEnrolment(final Enrolment enrolment, final APIListener listener) {
        final User user = mModel.getUser();
        if (user == null) {
            listener.onEnrolmentDeleted(null);
        }
        else {
            String url = BASE_URL + "/api/enrolments/" + enrolment.getId();

            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    listener.onEnrolmentDeleted(enrolment);
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onEnrolmentDeleted(null);
                }
            };

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.DELETE, url, null, responseListener, errorListener){

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + user.getToken());
                    return headers;
                }
            };

            mRequestQueue.add(request);
        }
    }

    @Override
    public void loadCourses(final APIListener listener) {
        final User user = mModel.getUser();
        if (user == null) {
            listener.onCoursesLoaded(null);
        }
        else {
            String url = BASE_URL + "/api/courses";

            Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    try {
                        List<Course> courses = Course.getCourses(jsonArray);
                        listener.onCoursesLoaded(courses);
                    } catch (JSONException ex) {
                        listener.onCoursesLoaded(null);
                    }
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onCoursesLoaded(null);
                }
            };

            JsonArrayRequest request = new JsonArrayRequest(
                    Request.Method.GET, url, null, responseListener, errorListener) {

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + user.getToken());
                    return headers;
                }
            };

            mRequestQueue.add(request);
        }
    }

    @Override
    public void loadStudents(final APIListener listener) {
        final User user = mModel.getUser();
        if (user == null) {
            listener.onStudentsLoaded(null);
        }
        else {
            String url = BASE_URL + "/api/students";

            Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    try {
                        List<Student> students = Student.getStudents(jsonArray);
                        listener.onStudentsLoaded(students);
                    } catch (JSONException ex) {
                        listener.onStudentsLoaded(null);
                    }
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onStudentsLoaded(null);
                }
            };

            JsonArrayRequest request = new JsonArrayRequest(
                    Request.Method.GET, url, null, responseListener, errorListener) {

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + user.getToken());
                    return headers;
                }
            };

            mRequestQueue.add(request);
        }
    }
}
