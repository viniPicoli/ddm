package com.example.ddm.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
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
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.ddm.BuildConfig;
import com.example.ddm.DataBase;
import com.example.ddm.Local;
import com.example.ddm.MaskEditUtil;
import com.example.ddm.R;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GalleryFragment extends Fragment {

    private FragmentManager fragmentManager;
    private GalleryViewModel galleryViewModel;
    private String selectedImagePath = "";
    final private int PICK_IMAGE = 1;
    private String imgPath;

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

        Button btnAddImg = getActivity().findViewById(R.id.buttonAddLocalImage);
        btnAddImg.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick (View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE);
            }
        });

        Button btnLocalCreate = (Button) getActivity().findViewById(R.id.buttonLocalConfirm);
        btnLocalCreate.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick (View v) {

                if(validateInputs()){
                    if(saveData()){
                        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date()) + ".png";
                        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "PescaFacilImgs");
                        if (!folder.exists()) {
                            folder.mkdirs();
                        }
                        File pic = new File(folder, timeStamp);
                        imgPath = pic.getAbsolutePath();
                        File file = new File(selectedImagePath);
                        //Log.println(Log.ERROR, "e", pic.getAbsolutePath());
                        try {
                            copy(file, pic);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getContext(), "Salvo com sucesso!", Toast.LENGTH_SHORT);
                        getActivity().onBackPressed();
                    }
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



    private boolean saveData(){
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
            local.setPath(imgPath);

            db.addLocal(local);
            return true;
        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }

    }


    public Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }

    public String getAbsolutePath(Uri uri) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == PICK_IMAGE) {
                ImageView imageViewLocal = getActivity().findViewById(R.id.imageViewLocal);
                selectedImagePath = getAbsolutePath(data.getData());
                imageViewLocal.setImageBitmap(decodeFile(selectedImagePath));
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

}