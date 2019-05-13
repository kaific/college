package com.example.mobile_computing_ca2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobile_computing_ca2.model.Enrolment;
import com.example.mobile_computing_ca2.model.Model;
import com.example.mobile_computing_ca2.model.Course;
import com.example.mobile_computing_ca2.model.Student;
import com.example.mobile_computing_ca2.model.api.AbstractAPIListener;


import java.util.List;

public class EnrolmentFragment extends Fragment {

    public static final String ARG_ENROLMENT_ID = "enrolment_id";

    public static EnrolmentFragment newInstance(int enrolmentId) {
        EnrolmentFragment fragment = new EnrolmentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ENROLMENT_ID, enrolmentId);
        fragment.setArguments(args);
        return fragment;
    }

    private EditText mDateText;
    private EditText mTimeText;
    private Spinner mCoursesSpinner;
    private Spinner mStudentsSpinner;
    private RadioButton mRegisteredButton;
    private RadioButton mAttendingButton;
    private RadioButton mDefferedButton;
    private RadioButton mWithdrawnButton;

    private Model mModel;
    private int mEnrolmentId;
    private Enrolment mEnrolment;
    private boolean mEditMode;

    public EnrolmentFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            Bundle arguments = getArguments();
            mEnrolmentId = arguments.getInt(ARG_ENROLMENT_ID);

            mModel = Model.getInstance(this.getActivity().getApplication());
            if(mEnrolmentId == 0) {
                mEnrolment = new Enrolment(0, "", "", 0, 0, Enrolment.REGISTERED);
            }
            else {
                mEnrolment = mModel.findEnrolmentById(mEnrolmentId);
            }

            setHasOptionsMenu(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_enrolment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mDateText = view.findViewById(R.id.dateText);
        mTimeText = view.findViewById(R.id.timeText);
        mCoursesSpinner = view.findViewById(R.id.coursesSpinner);
        mStudentsSpinner = view.findViewById(R.id.studentsSpinner);
        mRegisteredButton = view.findViewById(R.id.radio_registered);
        mAttendingButton = view.findViewById(R.id.radio_attending);
        mDefferedButton = view.findViewById(R.id.radio_deffered);
        mWithdrawnButton = view.findViewById(R.id.radio_withdrawn);

        populateForm();

        if(mEnrolmentId == 0) {
            setEditMode(true);
        }
        else {
            setEditMode(false);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu (Menu menu) {
        MenuItem edit = menu.findItem(R.id.action_edit);

        if(mEditMode) {
            edit.setIcon(R.drawable.baseline_save_white_48dp);
            edit.setTitle("Save");
        }
        else {
            edit.setIcon(R.drawable.baseline_edit_white_48dp);
            edit.setTitle("Edit");
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_edit: {
                if(mEditMode) {
                    Enrolment enrolment = getFormData();
                    if(mEnrolmentId == 0) {
                        storeEnrolment(enrolment);
                    }
                    else {
                        updateEnrolment(enrolment);
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Edit Selected", Toast.LENGTH_LONG).show();
                }
                setEditMode(!mEditMode);

                getActivity().invalidateOptionsMenu();

                return false;
            }
            case R.id.action_delete: {
                mModel.deleteEnrolment(mEnrolment, new AbstractAPIListener() {
                    @Override
                    public void onEnrolmentDeleted(Enrolment deletedEnrolment) {
                        if(deletedEnrolment == null) {
                            Toast.makeText(getActivity(), "Enrolment not deleted!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            mModel.deleteEnrolment(deletedEnrolment);

                            Toast.makeText(getActivity(), "Enrolment deleted!", Toast.LENGTH_LONG).show();

                            EnrolmentFragment.this.getActivity().finish();
                        }
                    }
                });
                return false;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // Save new enrolment into database through API
    public void storeEnrolment(Enrolment enrolment){
        mModel.storeEnrolment(enrolment, new AbstractAPIListener() {
            @Override
            public void onEnrolmentStored(Enrolment storedEnrolment) {
                if(storedEnrolment == null) {
                    Toast.makeText(getActivity(), "Enrolment not stored!", Toast.LENGTH_LONG).show();
                }
                else {
                    setEnrolment(storedEnrolment);

                    mModel.storeEnrolment(mEnrolment);

                    Toast.makeText(getActivity(), "Enrolment stored!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Update enrolment in database through API
    public void updateEnrolment(Enrolment enrolment) {
        mModel.updateEnrolment(enrolment, new AbstractAPIListener() {
            @Override
            public void onEnrolmentUpdated(Enrolment updatedEnrolment) {
                if(updatedEnrolment == null) {
                    Toast.makeText(getActivity(), "Enrolment not updated!", Toast.LENGTH_LONG).show();
                }
                else {
                    setEnrolment(updatedEnrolment);
                    Toast.makeText(getActivity(), "Enrolment updated!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void populateForm(){
        mDateText.setText(mEnrolment.getDate());
        mTimeText.setText(mEnrolment.getTime());

        populateCoursesSpinner();
        populateStudentsSpinner();

        mRegisteredButton.setChecked(mEnrolment.getStatus().equals(Enrolment.REGISTERED));
        mAttendingButton.setChecked(mEnrolment.getStatus().equals(Enrolment.ATTENDING));
        mDefferedButton.setChecked(mEnrolment.getStatus().equals(Enrolment.DEFERRED));
        mWithdrawnButton.setChecked(mEnrolment.getStatus().equals(Enrolment.WITHDRAWN));
    }

    private void populateCoursesSpinner() {
        List<Course> courses = mModel.getCourses();
        final ArrayAdapter<Course> coursesAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, courses);
        coursesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCoursesSpinner.setAdapter(coursesAdapter);

        if(courses.size() == 0) {
            mModel.loadCourses(new AbstractAPIListener() {
                @Override
                public void onCoursesLoaded(List<Course> courseList) {
                    if(courseList != null && !courseList.isEmpty()) {
                        mModel.addCourses(courseList);
                        coursesAdapter.notifyDataSetChanged();
                        mCoursesSpinner.setSelection(coursesAdapter.getPosition(mEnrolment.getCourse()));
                    }
                }
            });
        }
        else {
            mCoursesSpinner.setSelection(coursesAdapter.getPosition(mEnrolment.getCourse()));
        }
    }

    private void populateStudentsSpinner() {
        List<Student> students = mModel.getStudents();
        final ArrayAdapter<Student> studentsAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, students);
        studentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStudentsSpinner.setAdapter(studentsAdapter);

        if(students.size() == 0) {
            mModel.loadStudents(new AbstractAPIListener() {
                @Override
                public void onStudentsLoaded(List<Student> studentsList) {
                    if(studentsList != null && !studentsList.isEmpty()) {
                        mModel.addStudents(studentsList);
                        studentsAdapter.notifyDataSetChanged();
                        mStudentsSpinner.setSelection(studentsAdapter.getPosition(mEnrolment.getStudent()));
                    }
                }
            });
        }
        else {
            mStudentsSpinner.setSelection(studentsAdapter.getPosition(mEnrolment.getStudent()));
        }
    }

    private void setEditMode(boolean editMode) {
        mEditMode = editMode;
        mDateText.setEnabled(editMode);
        mTimeText.setEnabled(editMode);
        mCoursesSpinner.setEnabled(editMode);
        mStudentsSpinner.setEnabled(editMode);
        mRegisteredButton.setEnabled(editMode);
        mAttendingButton.setEnabled(editMode);
        mDefferedButton.setEnabled(editMode);
        mWithdrawnButton.setEnabled(editMode);
    }

    private Enrolment getFormData() {
        int id = mEnrolment.getId();
        String date = mDateText.getText().toString();
        String time = mTimeText.getText().toString();
        Course course = (Course) mCoursesSpinner.getSelectedItem();
        int courseId = course.getId();
        Student student = (Student) mStudentsSpinner.getSelectedItem();
        int studentId = student.getId();
        String status = getFormStatus();

        Enrolment enrolment = new Enrolment(id, date, time, courseId, studentId, status);
        enrolment.setCourse(course);
        enrolment.setStudent(student);

        return enrolment;
    }

    private String getFormStatus() {
        String status = null;
        if(mRegisteredButton.isChecked()) {
            status = Enrolment.REGISTERED;
        }
        else if (mAttendingButton.isChecked()) {
            status = Enrolment.ATTENDING;
        }
        else if (mDefferedButton.isChecked()) {
            status = Enrolment.DEFERRED;
        }
        else if (mWithdrawnButton.isChecked()) {
            status = Enrolment.WITHDRAWN;
        }
        return status;
    }

    private void setEnrolment(Enrolment enrolment) {
        mEnrolment.setId(enrolment.getId());
        mEnrolment.setDate(enrolment.getDate());
        mEnrolment.setTime(enrolment.getTime());
        mEnrolment.setCourseId(enrolment.getCourseId());
        mEnrolment.setStudentId(enrolment.getStudentId());
        mEnrolment.setStatus(enrolment.getStatus());
        Course course = mModel.findCourseById(enrolment.getCourseId());
        mEnrolment.setCourse(course);
        Student student = mModel.findStudentById(enrolment.getStudentId());
        mEnrolment.setStudent(student);
    }
}