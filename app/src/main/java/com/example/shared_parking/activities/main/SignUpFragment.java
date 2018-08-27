package com.example.shared_parking.activities.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.shared_parking.R;
import com.example.shared_parking.networking.NetworkUtilities;
import com.example.shared_parking.networking.ServerCallback;

import org.json.JSONObject;

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
                    NetworkUtilities.register(Mail.getText().toString(), FirstName.getText().toString(), LastName.getText().toString(), Password.getText().toString(), getContext(),
                            new ServerCallback() {
                                @Override
                                public void onSuccess(JSONObject result) {
                                    Toast toast = Toast.makeText(getContext(), result.toString(), Toast.LENGTH_LONG);
                                    toast.show();
                                }

                                @Override
                                public void onFailure(VolleyError error) {
                                    Toast toast = Toast.makeText(getContext(), "Fataler Fehler", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            });
                    break;
                case R.id.tvSignUp:
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.main_coordinator, new LoginFragment()).addToBackStack(null);
                    ft.commit();
                    break;
            }

        }
}

