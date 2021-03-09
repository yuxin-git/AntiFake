package com.example.antifake.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.antifake.regulator.RegulatorMainActivity;
import com.example.antifake.brand.BrandMainActivity;
import com.example.antifake.dealer.DealerMainActivity;
import com.example.antifake.manufacturer.ManufacturerMainActivity;
import com.example.antifake.R;
import com.peersafe.chainsql.core.Chainsql;

import java.util.logging.Level;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    private Button btn=null;

    private EditText editTextAddress;
    private EditText editTextSecret;
    String address;
    String secret;

    public Chainsql c = new Chainsql();

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);


    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        editTextAddress=root.findViewById(R.id.editText_address);
        editTextSecret=root.findViewById(R.id.editText_secret);
        btn= root.findViewById(R.id.button_enter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int activity_index=getArguments().getInt(ARG_SECTION_NUMBER);
                switch (activity_index){
                    case 1:
                        address=editTextAddress.getText().toString();
                        secret=editTextSecret.getText().toString();
                        //方便测试
                        address="zhrXKdsLwWZW9nBN2TUcntRPLfeYVwcM4p";
                        secret="xx4u3Y8nVr5QyCewf788Ajj2tod4v";

                        c.connect("ws://172.26.128.207:6006");
                        c.connection.client.logger.setLevel(Level.SEVERE);
                        c.as(address,secret);
                        Intent intent1=new Intent(getActivity(),BrandMainActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        address=editTextAddress.getText().toString();
                        secret=editTextSecret.getText().toString();

                        //方便测试
                        address="zhrXKdsLwWZW9nBN2TUcntRPLfeYVwcM4p";
                        secret="xx4u3Y8nVr5QyCewf788Ajj2tod4v";

                        c.connect("ws://172.26.128.217:6006");
                        c.connection.client.logger.setLevel(Level.SEVERE);
                        c.as(address,secret);
                        Intent intent2=new Intent(getActivity(), ManufacturerMainActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        address=editTextAddress.getText().toString();
                        secret=editTextSecret.getText().toString();

                        //方便测试
                        address="zhrXKdsLwWZW9nBN2TUcntRPLfeYVwcM4p";
                        secret="xx4u3Y8nVr5QyCewf788Ajj2tod4v";

                        c.connect("ws://172.26.128.207:6006");
                        c.connection.client.logger.setLevel(Level.SEVERE);
                        c.as(address,secret);
                        Intent intent3=new Intent(getActivity(), DealerMainActivity.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        address=editTextAddress.getText().toString();
                        secret=editTextSecret.getText().toString();

                        //方便测试
                        address="zhrXKdsLwWZW9nBN2TUcntRPLfeYVwcM4p";
                        secret="xx4u3Y8nVr5QyCewf788Ajj2tod4v";

                        c.connect("ws://172.26.128.217:6006");
                        c.connection.client.logger.setLevel(Level.SEVERE);
                        c.as(address,secret);
                        Intent intent4=new Intent(getActivity(), RegulatorMainActivity.class);
                        startActivity(intent4);
                        break;

                }
            }
        });

        return root;
    }
}