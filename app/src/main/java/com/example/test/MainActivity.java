package com.example.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Iterator;
import java.util.ServiceLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        ServiceLoader<UserService> service = ServiceLoader.load(UserService.class);
        Iterator<UserService> iterator = service.iterator();
        while (iterator.hasNext()) {
            UserService next = iterator.next();
            Log.e("TAG", "next-->" + next);
            next.show();
        }
    }
}
