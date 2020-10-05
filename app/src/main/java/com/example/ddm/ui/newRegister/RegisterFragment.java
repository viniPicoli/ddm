package com.example.ddm.ui.newRegister;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.ddm.DataBase;
import com.example.ddm.MaskEditUtil;
import com.example.ddm.User;
import com.example.ddm.Person;

import com.example.ddm.R;
import com.example.ddm.ui.login.LoginFragment;

public class RegisterFragment extends Fragment {


    public static RegisterFragment newInstance() { return new RegisterFragment(); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.register_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EditText registerFone = getActivity().findViewById(R.id.phoneRegister);
        MaskEditUtil.mask(registerFone, MaskEditUtil.FORMAT_FONE);

        Button btnRegisterUser = (Button) getActivity().findViewById(R.id.buttonRegisterUser);
        btnRegisterUser.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick (View v) {

                if(validateInputs()){
                    saveData();
                    Toast.makeText(getContext(), "Salvo com sucesso!", Toast.LENGTH_SHORT);

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    LoginFragment login = new LoginFragment();
                    transaction.replace(R.id.FrameRegister, login);
                    transaction.commit();
                }
            }
        });

    }

    private Boolean validateInputs(){
        EditText registerName = getActivity().findViewById(R.id.firstNameRegister);
        EditText registerSecondName = getActivity().findViewById(R.id.secondNameRegister);
        EditText registerPhone = getActivity().findViewById(R.id.phoneRegister);
        EditText registerEmail = getActivity().findViewById(R.id.emailRegister);
        EditText registerPassword = getActivity().findViewById(R.id.passwordRegister);
        int flag = 1;
        if(registerName.getText().toString().equals("")){
            registerName.setError("Favor informar o Primeiro Nome!");
            flag = 0;
        }
        if(registerSecondName.getText().toString().equals("")){
            registerSecondName.setError("Favor informar o Segundo Nome!");
            flag = 0;
        }
        if(registerPhone.getText().toString().equals("")){
            registerPhone.setError("Favor informar o Telefone!");
            flag = 0;
        }
        if(registerEmail.getText().toString().equals("")){
            registerEmail.setError("Favor informar o Email!");
            flag = 0;
        }
        if(registerPassword.getText().toString().equals("")) {
            registerPassword.setError("Favor Senha!");
            flag = 0;
        }
        if(flag == 0){
            return false;
        }
        return true;
    }

    private void saveData(){
        try {
            EditText registerName = getActivity().findViewById(R.id.firstNameRegister);
            EditText registerSecondName = getActivity().findViewById(R.id.secondNameRegister);
            EditText registerPhone = getActivity().findViewById(R.id.phoneRegister);
            EditText registerEmail = getActivity().findViewById(R.id.emailRegister);
            EditText registerPassword = getActivity().findViewById(R.id.passwordRegister);
            long idUser, idPerson;

            User user = new User();
            Person person = new Person();
            DataBase db = new DataBase(getContext());

            person.setNome(registerName.getText().toString());
            person.setSobrenome(registerSecondName.getText().toString());
            person.setTelefone(registerPhone.getText().toString());
            person.setEmail(registerEmail.getText().toString());

            idPerson = db.addPerson(person);

            user.setPersonId(Integer.parseInt(Long.toString(idPerson)));
            user.setLogin(registerEmail.getText().toString());
            user.setSenha(registerPassword.getText().toString());
            db.addUser(user);

            Toast.makeText(getContext(), "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}