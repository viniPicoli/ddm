package com.example.ddm.ui.login;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.ddm.DataBase;
import com.example.ddm.User;
import com.example.ddm.R;
import com.example.ddm.ui.newRegister.RegisterFragment;
import com.example.ddm.ui.home.HomeFragment;

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
                //login();

              FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
              RegisterFragment register = new RegisterFragment();
              transaction.addToBackStack(null);
              transaction.replace(R.id.FrameRegister, register);
              transaction.commit();

            }
        });

    }


    private void login(){
        try {
            EditText loginEmail = getActivity().findViewById(R.id.emailLogin);
            EditText loginPassword = getActivity().findViewById(R.id.passwordLogin);

            DataBase db = new DataBase(getContext());

//            db.getUser(loginEmail.getText().toString(), loginPassword.getText().toString());
            db.getUser(loginEmail.getText().toString(), loginPassword.getText().toString());

            Toast.makeText(getContext(), "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}