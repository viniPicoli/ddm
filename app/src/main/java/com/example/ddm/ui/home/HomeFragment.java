package com.example.ddm.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ddm.DataBase;
import com.example.ddm.Local;
import com.example.ddm.MainActivity;
import com.example.ddm.R;
import com.example.ddm.ViewLocal;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        return root;
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        List<Local> listLocal;
        Local local = new Local();
        DataBase db = new DataBase(getContext());
        listLocal = db.selecttodoslocais();

        System.out.println(listLocal);

        ArrayAdapter<Local> adapter = new ArrayAdapter<Local>(this.getContext(), android.R.layout.simple_dropdown_item_1line, listLocal);


        Local[] locals = new Local[]{
                new Local(1,1,1,1, "açude com pintado, pirara, aaaa aaa aaaaaaa","Pesca esportiva","test","test","test","test","test","test","test","test","test"),
                new Local(1,1,1,1,"test","test","test","test","test","test","test","test","test","test","test"),
                new Local(1,1,1,1,"test","test","test","test","test","test","test","test","test","test","test"),
                new Local(1,1,1,1,"test","test","test","test","test","test","test","test","test","test","test"),
                new Local(1,1,1,1,"test","test","test","test","test","test","test","test","test","test","test"),
                new Local(1,1,1,1,"test","test","test","test","test","test","test","test","test","test","test"),
                new Local(1,1,1,1,"test","test","test","test","test","test","test","test","test","test","test"),
                new Local(1,1,1,1,"test","test","test","test","test","test","test","test","test","test","test"),
                new Local(1,1,1,1,"test","test","test","test","test","test","test","test","test","test","test")

        };

        ViewLocal viewLocal = new ViewLocal(locals);
        recyclerView.setAdapter(viewLocal);
    };

}