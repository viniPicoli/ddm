package com.example.ddm.ui.myLocals;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.ddm.DataBase;
import com.example.ddm.Local;
import com.example.ddm.R;
import com.example.ddm.ViewLocal;
import com.example.ddm.ui.gallery.GalleryFragment;
import com.example.ddm.ui.home.HomeFragment;

import java.util.List;

public class MyLocalsFragment extends Fragment {

    private MyLocalsViewModel mViewModel;

    public static MyLocalsFragment newInstance() {
        return new MyLocalsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_locals_fragment, container, false);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MyLocalsViewModel.class);

        RecyclerView recyclerView = getActivity().findViewById(R.id.RecyclerViewerMyLocals);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        SharedPreferences sharedPref = getActivity().getPreferences(getContext().MODE_PRIVATE);
        long idPerson = sharedPref.getLong("IdPerson", 1);

        List<Local> listLocal;
        DataBase db = new DataBase(getContext());
        listLocal = db.selectAllLocalsPerson(Integer.parseInt(Long.toString(idPerson)));
        ViewLocal viewLocal = new ViewLocal(this.getContext(), listLocal);
        recyclerView.setAdapter(viewLocal);


        Button btnNewLocal = getActivity().findViewById(R.id.buttonAddNewLocal);
        btnNewLocal.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick (View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                GalleryFragment local = new GalleryFragment();
                transaction.replace(R.id.nav_host_fragment, local);
                transaction.commit();
            }
        });
    }

}