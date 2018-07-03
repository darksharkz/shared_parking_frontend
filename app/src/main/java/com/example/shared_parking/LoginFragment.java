package com.example.shared_parking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginFragment extends Fragment implements View.OnClickListener{
    private EditText Mail;
    private EditText Password;
    private Button Button;
    private TextView TextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Mail = (EditText) view.findViewById(R.id.etLoginMail);
        Password = (EditText) view.findViewById(R.id.etLoginPassword);
        Button = (Button) view.findViewById(R.id.btnLogin);
        TextView = (TextView) view.findViewById(R.id.tvLogin);

        Button.setOnClickListener(this);
        TextView.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                break;
            case R.id.tvLogin:
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_coordinator, new SignUpFragment()).addToBackStack(null);
                ft.commit();
                break;
        }

    }
}
