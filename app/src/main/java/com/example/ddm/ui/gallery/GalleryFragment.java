package com.example.ddm.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.ddm.DataBase;
import com.example.ddm.Local;
import com.example.ddm.MaskEditUtil;
import com.example.ddm.R;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GalleryFragment extends Fragment {

    private FragmentManager fragmentManager;
    private GalleryViewModel galleryViewModel;
    private String localPath;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.FrameLocal, new MapsFragment(), "MapsFragment");
        transaction.commitAllowingStateLoss();

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

        ImageButton btnAddImg = getActivity().findViewById(R.id.imageButtonLocalAddImg);
        btnAddImg.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick (View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 123);
            }
        });

        Button btnLocalCreate = (Button) getActivity().findViewById(R.id.buttonLocalConfirm);
        btnLocalCreate.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick (View v) {

                if(validateInputs()){
                    saveData();
                    Toast.makeText(getContext(), "Salvo com sucesso!", Toast.LENGTH_SHORT);
                    getActivity().onBackPressed();
                }
            }
        });

        Button btnBack = (Button) getActivity().findViewById(R.id.buttonLocalCancel);
        btnBack.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick (View v) {
                getActivity().onBackPressed();
            }
        });

        Button btnGetCep = (Button) getActivity().findViewById(R.id.buttonFindCep);
        btnGetCep.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick (View v) {

                EditText editTextCep = getActivity().findViewById(R.id.editTextLocalCep);

                if(!editTextCep.getText().toString().isEmpty()){

                    String url = "http://viacep.com.br/ws/" + editTextCep.getText().toString().replace("-","") + "/json";
                    Ion.with(getContext()).load(url)
                            .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if(e != null){
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            } else if(!result.has("erro")) {
                                EditText localRua = getActivity().findViewById(R.id.editTextLocalRua);
                                EditText localBairro = getActivity().findViewById(R.id.editTextLocalBairro);
                                EditText localComplemento = getActivity().findViewById(R.id.editTextLocalComplemento);
                                EditText localCidade = getActivity().findViewById(R.id.editTextLocalCidade);
                                Spinner localUF = (Spinner) getActivity().findViewById(R.id.spinnerLocalUf);
                                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.LocalListUFs, android.R.layout.simple_spinner_item);

                                localRua.setText(result.get("logradouro").toString().replace("\"", ""));
                                localBairro.setText(result.get("bairro").toString().replace("\"", ""));
                                localComplemento.setText(result.get("complemento").toString().replace("\"", ""));
                                localCidade.setText(result.get("localidade").toString().replace("\"", ""));
                                int position = adapter.getPosition(result.get("uf").toString().toUpperCase().replace("\"", ""));
                                localUF.setSelection(position);


                            } else {
                                Toast.makeText(getContext(), "CEP não encontrado!", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                }
            }
        });

        EditText localCep = getActivity().findViewById(R.id.editTextLocalCep);
        localCep.addTextChangedListener(MaskEditUtil.mask(localCep, MaskEditUtil.FORMAT_CEP));
    }

    private Boolean validateInputs(){
        EditText localNome = getActivity().findViewById(R.id.editTextLocalNome);
        EditText localDescricao = getActivity().findViewById(R.id.editTextLocalDescricao);
        EditText localCep = getActivity().findViewById(R.id.editTextLocalCep);
        EditText localRua = getActivity().findViewById(R.id.editTextLocalRua);
        EditText localBairro = getActivity().findViewById(R.id.editTextLocalBairro);
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
        try {

            EditText localNome = getActivity().findViewById(R.id.editTextLocalNome);
            EditText localDescricao = getActivity().findViewById(R.id.editTextLocalDescricao);
            EditText localCep = getActivity().findViewById(R.id.editTextLocalCep);
            EditText localRua = getActivity().findViewById(R.id.editTextLocalRua);
            EditText localBairro = getActivity().findViewById(R.id.editTextLocalBairro);
            EditText localNmro = getActivity().findViewById(R.id.editTextLocalNumber);
            EditText localComplemento = getActivity().findViewById(R.id.editTextLocalComplemento);
            EditText localCidade = getActivity().findViewById(R.id.editTextLocalCidade);
            Spinner localUF = (Spinner) getActivity().findViewById(R.id.spinnerLocalUf);
            TextView localLatitude = getActivity().findViewById(R.id.textViewLocalLatitudeValue);
            TextView localLongitude = getActivity().findViewById(R.id.textViewLocalLongitudeValue);

            Local local = new Local();
            DataBase db = new DataBase(getContext());

            SharedPreferences sharedPref = getActivity().getPreferences(getContext().MODE_PRIVATE);
            long idPerson = sharedPref.getLong("IdPerson", 1);

            local.setPersonid(Integer.parseInt(Long.toString(idPerson)));
            //local.setAvaliacaoneg(0);
            //local.setAvaliacaopos(0);
            local.setTitulo(localNome.getText().toString());
            local.setDescricao(localDescricao.getText().toString());
            local.setCep(localCep.getText().toString());
            local.setRua(localRua.getText().toString());
            local.setBairro(localBairro.getText().toString());
            local.setNumero(localNmro.getText().toString());
            local.setComplemento(localComplemento.getText().toString());
            local.setCidade(localCidade.getText().toString());
            local.setUf(localUF.getSelectedItem().toString());
            local.setLatitude(localLatitude.getText().toString());
            local.setLongitude(localLongitude.getText().toString());
            //local.setPath(getImg());

            db.addLocal(local);
            Toast.makeText(getContext(), "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private String SaveImg() throws IOException {
        File dir = getContext().getDir("ImagePath", Context.MODE_PRIVATE);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(String.valueOf(dir));
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        String currentPhotoPath = image.getAbsolutePath();

        return currentPhotoPath;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == getActivity().RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

            File dir = getContext().getDir("ImagePath", Context.MODE_PRIVATE);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
            String imageFileName = "IMG_" + timeStamp + "_";
            File storageDir = getActivity().getExternalFilesDir(String.valueOf(dir));
            try {
                File image = File.createTempFile(cursor.getString(columnIndex), ".jpg", storageDir);
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.localPath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageViewLocal = (ImageView) getActivity().findViewById(R.id.imageViewLocal);
            imageViewLocal.setImageBitmap(BitmapFactory.decodeFile(localPath));

        }
    }

}