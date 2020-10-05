package com.example.ddm.ui.login;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;


import com.example.ddm.DataBase;
import com.example.ddm.User;
import com.example.ddm.R;
import com.example.ddm.ui.newRegister.RegisterFragment;
import com.example.ddm.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;

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

        SharedPreferences sharedPref = getActivity().getPreferences(getContext().MODE_PRIVATE);
        long idPerson = sharedPref.getLong("IdPerson", 0);

        if(idPerson > 0){
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            HomeFragment home = new HomeFragment();
            transaction.disallowAddToBackStack();
            transaction.replace(R.id.FrameRegister, home);
            transaction.commit();
        }

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
                if(validateInputs()) {
                    long personId = login();
                    if(personId > 0) {
                        Toast.makeText(getContext(), "Logado com Sucesso!", Toast.LENGTH_SHORT).show();

                        //pegar idperson a partir do user logado
                        SharedPreferences sharedPref = getActivity().getPreferences(getContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putLong("IdPerson", personId);
                        editor.apply();

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        HomeFragment home = new HomeFragment();
                        transaction.disallowAddToBackStack();
                        transaction.replace(R.id.FrameRegister, home);
                        transaction.commit();
                    } else {
                        Toast.makeText(getContext(), "Erro ao Logar", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private Boolean validateInputs() {
        EditText emailLogin = getActivity().findViewById(R.id.emailLogin);
        EditText passLogin = getActivity().findViewById(R.id.passwordLogin);

        int flag = 1;
        if (emailLogin.getText().toString().equals("")) {
            emailLogin.setError("Favor informar o e-mail!");
            flag = 0;
        }
        if (passLogin.getText().toString().equals("")) {
            passLogin.setError("Favor informar a senha!");
            flag = 0;
        }
        if (flag == 0) {
            return false;
        }
        return true;
    }


    private long login(){
        try {
            EditText loginEmail = getActivity().findViewById(R.id.emailLogin);
            EditText loginPassword = getActivity().findViewById(R.id.passwordLogin);
            User user = new User();
            user.setLogin(loginEmail.getText().toString());
            user.setSenha(loginPassword.getText().toString());

            DataBase db = new DataBase(getContext());
            long test = db.getUser(user);

            return test;

        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return 0;
        }
    }
}