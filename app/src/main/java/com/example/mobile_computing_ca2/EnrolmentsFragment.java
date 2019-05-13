package com.example.mobile_computing_ca2;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile_computing_ca2.model.Enrolment;
import com.example.mobile_computing_ca2.model.Model;
import com.example.mobile_computing_ca2.model.api.AbstractAPIListener;


import java.util.List;

public class EnrolmentsFragment extends Fragment {

    private EnrolmentsAdapter adapter;

    public static EnrolmentsFragment newInstance() {
        EnrolmentsFragment fragment = new EnrolmentsFragment();
        return fragment;
    }

    private OnFragmentInteractionListener mListener;

    public EnrolmentsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enrolments, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        if(view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(decoration);

            Application application = this.getActivity().getApplication();
            Model model = Model.getInstance(application);
            final List<Enrolment> enrolments = model.getEnrolments();

            final EnrolmentsAdapter adapter = new EnrolmentsAdapter(this, enrolments);
            recyclerView.setAdapter(adapter);

            model.loadEnrolments(new AbstractAPIListener(){
                @Override
                public void onEnrolmentsLoaded(List<Enrolment> loadedEnrolments) {
                    enrolments.clear();
                    enrolments.addAll(loadedEnrolments);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    public void onItemSelected(Enrolment enrolment) {
        if (mListener != null) {
            mListener.onItemSelected(enrolment);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onItemSelected(Enrolment enrolment);
    }
}
