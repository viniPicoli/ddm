package com.example.ddm.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.ddm.R;

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

        Button btnRegister = (Button) getActivity().findViewById(R.id.register);
        btnRegister.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick (View v) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {
        //startActivity(new Intent(LoginFragment.this, RegisterFragment.class));
    }

}