package com.example.shared_parking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class TestFragment extends Fragment implements View.OnClickListener{
    Button button;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test, container, false);
        textView = view.findViewById(R.id.fragment_test_textview);
        button = view.findViewById(R.id.button2);
        button.setOnClickListener(this);

        //User user = new User(0,"Simon", "Englert");
        //MainActivity.appDatabase.userDao().insertUser(user);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button2:
                User user = MainActivity.appDatabase.userDao().getUser(1);
                textView.setText(user.getFirstName());
        }

    }
}
