package com.example.shifumi.fragment.listener;

import android.view.View;

import com.example.shifumi.MainActivity;

public class ExitButtonListener implements View.OnClickListener{
    private final MainActivity mainActivity;

    public ExitButtonListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View v) {
        mainActivity.finish();
        System.exit(0);
    }
}

