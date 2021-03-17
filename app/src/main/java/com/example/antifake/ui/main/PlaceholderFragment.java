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
import com.peersafe.chainsql.core.Submit;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    private Button btn=null;
    //方便测试使用
    private Button btnTest=null;

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
        //方便测试使用
        btnTest=root.findViewById(R.id.button_test_add);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int atest=getArguments().getInt(ARG_SECTION_NUMBER);
                switch (atest) {
                    case 1:
                        editTextAddress.setText("zEX33AirGeFUyY4H56viye5hp5J9WwKUv3");
                        editTextSecret.setText("xp1kUTT42HcwEWFxH9kkg6BGd1VBE");
                        break;
                    case 2:
                        editTextAddress.setText("zKhdUEXNWMYG3uEquQkhGvYM3mZRGqYqNf");
                        editTextSecret.setText("xp1vcANddqbBhbfEr8i624pXcA5B4");
                        break;
                    case 3:
                        editTextAddress.setText("zNoePXrfYvz8jvDiDNr3RNi4PwtBYhQxAR");
                        editTextSecret.setText("xhpgTk8ALwWMugf921ak9eJdqtG4Q");
                        break;
                    case 4:
                        editTextAddress.setText("zL36kWKGdqx9fXK4dzVc95ErriGuCQng5z");
                        editTextSecret.setText("xnejoG6irLTUgNgELJM5Y5ipsSwDT");
                        break;
                }
            }
        });



        btn= root.findViewById(R.id.button_enter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int activity_index=getArguments().getInt(ARG_SECTION_NUMBER);
                switch (activity_index){
                    case 1:
                        address=editTextAddress.getText().toString();
                        secret=editTextSecret.getText().toString();
                        Intent intent1=new Intent(getActivity(),BrandMainActivity.class);
                        Bundle bundle1=new Bundle();
                        bundle1.putString("address", address);
                        bundle1.putString("secret",secret);
                        intent1.putExtras(bundle1);
                        startActivity(intent1);

                        break;
                    case 2://生产商
                        address=editTextAddress.getText().toString();
                        secret=editTextSecret.getText().toString();
                        Intent intent2=new Intent(getActivity(), ManufacturerMainActivity.class);
                        Bundle bundle2=new Bundle();
                        bundle2.putString("address", address);
                        bundle2.putString("secret",secret);
                        intent2.putExtras(bundle2);
                        startActivity(intent2);
                        break;
                    case 3:
                        address=editTextAddress.getText().toString();
                        secret=editTextSecret.getText().toString();
                        Intent intent3=new Intent(getActivity(), DealerMainActivity.class);
                        Bundle bundle3=new Bundle();
                        bundle3.putString("address", address);
                        bundle3.putString("secret",secret);
                        intent3.putExtras(bundle3);
                        startActivity(intent3);
                        break;
                    case 4:
                        address=editTextAddress.getText().toString();
                        secret=editTextSecret.getText().toString();
                        Intent intent4=new Intent(getActivity(), RegulatorMainActivity.class);
                        Bundle bundle4=new Bundle();
                        bundle4.putString("address", address);
                        bundle4.putString("secret",secret);
                        intent4.putExtras(bundle4);
                        startActivity(intent4);
                        break;

                }
            }
        });

        return root;
    }
}