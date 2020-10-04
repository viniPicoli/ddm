package com.example.ddm.ui.newRegister;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddm.R;
import com.example.ddm.ui.login.LoginFragment;

public class InitalFragment extends Fragment {

    public static InitalFragment newInstance() { return new InitalFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inital, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        RegisterFragment register = new RegisterFragment();
//        transaction.replace(R.id.FrameRegister, register);
//        transaction.commit();

    }

}