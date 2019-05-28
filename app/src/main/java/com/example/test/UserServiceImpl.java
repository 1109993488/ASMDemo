package com.example.test;

import android.widget.Toast;

/**
 * Created by BlingBling on 2019-05-17.
 */
public class UserServiceImpl implements UserService {
    @Override
    public void show() {
        Toast.makeText(App.APP, "show", Toast.LENGTH_SHORT).show();
    }
}
