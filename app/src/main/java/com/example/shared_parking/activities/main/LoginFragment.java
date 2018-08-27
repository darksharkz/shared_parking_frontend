package com.example.shared_parking.activities.main;

import android.content.Context;
import android.content.SharedPreferences;
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

import org.json.JSONException;
import org.json.JSONObject;

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
                NetworkUtilities.login(Mail.getText().toString(), Password.getText().toString(), getContext(),
                        new ServerCallback() {
                            @Override
                            public void onSuccess(JSONObject result) {
                                Context context = getContext();
                                SharedPreferences sharedPref = context.getSharedPreferences("key", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                try {
                                    editor.putString("auth_token", result.getString("auth_token"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                editor.commit();
                                Toast toast = Toast.makeText(getContext(), sharedPref.getString("auth_token", "default"), Toast.LENGTH_LONG);
                                toast.show();
                            }

                            @Override
                            public void onFailure(VolleyError error) {
                                Toast toast = Toast.makeText(getContext(), "Fataler Fehler", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        });
                break;
            case R.id.tvLogin:
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_coordinator, new SignUpFragment()).addToBackStack(null);
                ft.commit();
                break;
        }

    }
}
