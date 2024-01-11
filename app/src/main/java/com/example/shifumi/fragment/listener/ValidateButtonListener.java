package com.example.shifumi.fragment.listener;

import android.view.View;
import android.widget.Button;

import com.example.shifumi.MainActivity;
import com.example.shifumi.fragment.PlayFragment;

import java.util.List;

public class ValidateButtonListener implements View.OnClickListener {
    private final PlayFragment fragment;
    private final MainActivity mainActivity;
    private final List<Button> buttons;

    public ValidateButtonListener(PlayFragment fragment, MainActivity mainActivity, List<Button> buttons) {
        this.fragment = fragment;
        this.mainActivity = mainActivity;
        this.buttons = buttons;
    }

    @Override
    public void onClick(View v) {
        mainActivity.getClient().setOwnChoice(fragment.getSelectedChoice());
        buttons.forEach(button -> button.setEnabled(false));
    }
}
