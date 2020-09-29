package com.example.ddm.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.ddm.DataBase;
import com.example.ddm.Local;
import com.example.ddm.R;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Inicialização e atribuição dos itens a variaveis
        Spinner localUF = (Spinner) getActivity().findViewById(R.id.spinnerLocalUf);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.LocalListUFs, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        localUF.setAdapter(adapter);

        Button btnLocalCreate = (Button) getActivity().findViewById(R.id.buttonLocalConfirm);
        btnLocalCreate.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick (View v) {

                if(validateInputs()){
                    saveData();
                    Toast.makeText(getContext(), "Salvo com sucesso!", Toast.LENGTH_SHORT);
                }
            }
        });

        Button btnBack = (Button) getActivity().findViewById(R.id.buttonLocalCancel);
        btnBack.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick (View v) {
                Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Boolean validateInputs(){
        EditText localNome = getActivity().findViewById(R.id.editTextLocalNome);
        EditText localDescricao = getActivity().findViewById(R.id.editTextLocalDescricao);
        EditText localCep = getActivity().findViewById(R.id.editTextLocalCep);
        EditText localRua = getActivity().findViewById(R.id.editTextLocalRua);
        EditText localBairro = getActivity().findViewById(R.id.editTextLocalBairro);
        EditText localNmro = getActivity().findViewById(R.id.editTextLocalNumber);
        EditText localComplemento = getActivity().findViewById(R.id.editTextLocalComplemento);
        EditText localCidade = getActivity().findViewById(R.id.editTextLocalCidade);

        int flag = 1;

        if(localNome.getText().toString().equals("")){
            localNome.setError("Favor informar o nome!");
            flag = 0;
        }
        if(localDescricao.getText().toString().equals("")){
            localDescricao.setError("Favor informar a descrição!");
            flag = 0;
        }
        if(localCep.getText().toString().equals("")){
            localCep.setError("Favor informar o CEP!");
            flag = 0;
        }
        if(localRua.getText().toString().equals("")){
            localRua.setError("Favor informar a rua!");
            flag = 0;
        }
        if(localBairro.getText().toString().equals("")){
            localBairro.setError("Favor informar o bairro!");
            flag = 0;
        }
        if(localNmro.getText().toString().equals("")){
            localNmro.setError("Favor informar o número!");
            flag = 0;
        }
        if(localCidade.getText().toString().equals("")){
            localCidade.setError("Favor informar a cidade!");
            flag = 0;
        }

        if(flag == 0){
            return false;
        }
        return true;
    }

    private void saveData(){
        EditText localNome = getActivity().findViewById(R.id.editTextLocalNome);
        EditText localDescricao = getActivity().findViewById(R.id.editTextLocalDescricao);
        EditText localCep = getActivity().findViewById(R.id.editTextLocalCep);
        EditText localRua = getActivity().findViewById(R.id.editTextLocalRua);
        EditText localBairro = getActivity().findViewById(R.id.editTextLocalBairro);
        EditText localNmro = getActivity().findViewById(R.id.editTextLocalNumber);
        EditText localComplemento = getActivity().findViewById(R.id.editTextLocalComplemento);
        EditText localCidade = getActivity().findViewById(R.id.editTextLocalCidade);
        Spinner localUF = (Spinner) getActivity().findViewById(R.id.spinnerLocalUf);

        Local local = new Local();
        DataBase db = new DataBase(getContext());

        local.setPersonid(1);
        local.setAvaliacaoneg(0);
        local.setAvaliacaopos(0);
        local.setTitulo(localNome.getText().toString());
        local.setDescricao(localDescricao.getText().toString());
        local.setCep(localCep.getText().toString());
        local.setRua(localRua.getText().toString());
        local.setBairro(localBairro.getText().toString());
        local.setNumero(localNmro.getText().toString());
        local.setComplemento(localComplemento.getText().toString());
        local.setCidade(localCidade.getText().toString());
        local.setUf(localUF.getSelectedItem().toString());
        local.setLatitude("0");
        local.setLongitude("0");

        try {
            db.addLocal(local);
            Toast.makeText(getContext(), "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}