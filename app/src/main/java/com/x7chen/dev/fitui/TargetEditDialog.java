package com.x7chen.dev.fitui;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class TargetEditDialog extends Dialog {
    private int TargetSteps;
    private CallBacks mCallBacks;
    public int getTargetSteps() {
        return TargetSteps;
    }

    public void setTargetSteps(int targetSteps) {
        TargetSteps = targetSteps;
    }

    public TargetEditDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public TargetEditDialog(Context context) {
        super(context);
    }

    protected TargetEditDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_target_edit);
        final EditText editText = (EditText) findViewById(R.id.TargetEditer);
        editText.setText(String.valueOf(getTargetSteps()));
        Button button = (Button) findViewById(R.id.bt_target_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCallBacks.updateTarget(Integer.parseInt(editText.getText().toString()));
                TargetEditDialog.this.dismiss();
            }
        });
    }
    public void setCallBacks(CallBacks callBacks){
        mCallBacks = callBacks;
    }
    interface CallBacks{
        void updateTarget(int target);
    }
}
