package com.example.ddm.ui.slideshow;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ddm.DataBase;
import com.example.ddm.Local;
import com.example.ddm.Person;
import com.example.ddm.R;
import com.example.ddm.User;

public class SlideshowFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        preencheData();

        final Button btnEditPerson = (Button) getActivity().findViewById(R.id.buttonEditUser);
        btnEditPerson.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick (View v) {

                if(btnEditPerson.getText().toString().equals("Editar")){
                    btnEditPerson.setText("Salvar");
                    getActivity().findViewById(R.id.editTextPersonFirstName).setFocusableInTouchMode(true);
                    getActivity().findViewById(R.id.editTextPersonSecondName).setFocusableInTouchMode(true);
                    getActivity().findViewById(R.id.editTextPersonPhone).setFocusableInTouchMode(true);
                    getActivity().findViewById(R.id.editTextPersonMail).setFocusableInTouchMode(true);
                    getActivity().findViewById(R.id.editTextPersonPassword).setFocusableInTouchMode(true);
                } else {
                    if(validateInputs()) {
                        if (saveData()) {
                            btnEditPerson.setText("Editar");
                            Toast.makeText(getContext(), "Alterado com Sucesso!", Toast.LENGTH_SHORT).show();
                            getActivity().findViewById(R.id.editTextPersonFirstName).setFocusable(false);
                            getActivity().findViewById(R.id.editTextPersonSecondName).setFocusable(false);
                            getActivity().findViewById(R.id.editTextPersonPhone).setFocusable(false);
                            getActivity().findViewById(R.id.editTextPersonMail).setFocusable(false);
                            getActivity().findViewById(R.id.editTextPersonPassword).setFocusable(false);
                        }
                    }
                }
            }
        });
    }

    private Boolean validateInputs(){
        EditText editTextPersonFirst = getActivity().findViewById(R.id.editTextPersonFirstName);
        EditText editTextPersonSecond = getActivity().findViewById(R.id.editTextPersonSecondName);
        EditText editTextPersonTel = getActivity().findViewById(R.id.editTextPersonPhone);
        EditText editTextPersonMail = getActivity().findViewById(R.id.editTextPersonMail);
        EditText editTextPersonPass = getActivity().findViewById(R.id.editTextPersonPassword);

        int flag = 1;

        if(editTextPersonFirst.getText().toString().equals("")){
            editTextPersonFirst.setError("Favor informar o primeiro nome!");
            flag = 0;
        }
        if(editTextPersonSecond.getText().toString().equals("")){
            editTextPersonSecond.setError("Favor informar o segundo nome!");
            flag = 0;
        }
        if(editTextPersonTel.getText().toString().equals("")){
            editTextPersonTel.setError("Favor informar o telefone!");
            flag = 0;
        }
        if(editTextPersonMail.getText().toString().equals("")){
            editTextPersonMail.setError("Favor informar o e-mail!");
            flag = 0;
        }
        if(editTextPersonPass.getText().toString().equals("")){
            editTextPersonPass.setError("Favor informar a senha!");
            flag = 0;
        }

        if(flag == 0){
            return false;
        }
        return true;
    }



    private boolean saveData(){
        try {
            EditText editTextPersonFirst = getActivity().findViewById(R.id.editTextPersonFirstName);
            EditText editTextPersonSecond = getActivity().findViewById(R.id.editTextPersonSecondName);
            EditText editTextPersonTel = getActivity().findViewById(R.id.editTextPersonPhone);
            EditText editTextPersonMail = getActivity().findViewById(R.id.editTextPersonMail);
            EditText editTextPersonPass = getActivity().findViewById(R.id.editTextPersonPassword);

            Person person = new Person();
            User user = new User();
            DataBase db = new DataBase(getContext());

            SharedPreferences sharedPref = getActivity().getPreferences(getContext().MODE_PRIVATE);
            long idPerson = sharedPref.getLong("IdPerson", 1);

            person.setNome(editTextPersonFirst.getText().toString());
            person.setSobrenome(editTextPersonSecond.getText().toString());
            person.setTelefone(editTextPersonTel.getText().toString());
            person.setId(Integer.parseInt(Long.toString(idPerson)));
            user.setLogin(editTextPersonMail.getText().toString());
            user.setSenha(editTextPersonPass.getText().toString());
            user.setPersonId(Integer.parseInt(Long.toString(idPerson)));

            db.updatePerson(person);
            db.updateUser(user);
            return true;
        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void preencheData(){
        EditText editTextPersonFirst = getActivity().findViewById(R.id.editTextPersonFirstName);
        EditText editTextPersonSecond = getActivity().findViewById(R.id.editTextPersonSecondName);
        EditText editTextPersonTel = getActivity().findViewById(R.id.editTextPersonPhone);
        EditText editTextPersonMail = getActivity().findViewById(R.id.editTextPersonMail);
        EditText editTextPersonPass = getActivity().findViewById(R.id.editTextPersonPassword);

        SharedPreferences sharedPref = getActivity().getPreferences(getContext().MODE_PRIVATE);
        long idPerson = sharedPref.getLong("IdPerson", 1);

        DataBase db = new DataBase(getContext());
        Person person = db.selecionarPerson(Integer.parseInt(Long.toString(idPerson)));
        User user = db.selecionarUser(Integer.parseInt(Long.toString(idPerson)));

        editTextPersonFirst.setText(person.getNome());
        editTextPersonSecond.setText(person.getSobrenome());
        editTextPersonTel.setText(person.getTelefone());
        editTextPersonMail.setText(user.getLogin());
        editTextPersonPass.setText(user.getSenha());

    }

}