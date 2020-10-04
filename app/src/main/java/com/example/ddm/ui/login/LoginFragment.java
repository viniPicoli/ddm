package com.example.ddm.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.ddm.R;
import com.example.ddm.ui.newRegister.RegisterFragment;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btnRegister = (Button) getActivity().findViewById(R.id.buttonRegister);
        btnRegister.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick (View v) {

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                RegisterFragment register = new RegisterFragment();
                transaction.addToBackStack(null);
                transaction.replace(R.id.FrameRegister, register);
                transaction.commit();

            }
        });

        Button btnLogin = (Button) getActivity().findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick (View v) {

                //validate login


//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                RegisterFragment register = new RegisterFragment();
//                transaction.addToBackStack(null);
//                transaction.replace(R.id.FrameRegister, register);
//                transaction.commit();

            }
        });

    }



}