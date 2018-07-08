package com.example.shared_parking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpFragment extends Fragment implements View.OnClickListener{
        private EditText Mail, FirstName, LastName, Password;
        private Button Button;
        private TextView TextView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_signup, container, false);

            Mail = (EditText) view.findViewById(R.id.etSignUpMail);
            FirstName = (EditText) view.findViewById(R.id.etSignUpFirstName);
            LastName = (EditText) view.findViewById(R.id.etSignUpLastName);
            Password = (EditText) view.findViewById(R.id.etSignUpPassword);
            Button = (Button) view.findViewById(R.id.btnSignUp);
            TextView = (TextView) view.findViewById(R.id.tvSignUp);

            Button.setOnClickListener(this);
            TextView.setOnClickListener(this);

            return view;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnSignUp:
                    break;
                case R.id.tvSignUp:
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.main_coordinator, new LoginFragment()).addToBackStack(null);
                    ft.commit();
                    break;
            }

        }
}

