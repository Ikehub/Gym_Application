package com.example.gymapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class PlanDetailDialog extends DialogFragment {

    public interface PassPlanInterface {
        void getPlan(Plan plan);
    }

    private PassPlanInterface passPlanInterface;

    private Button btnDismiss, btnAdd;
    private TextView txtName;
    private EditText edtTxtMinutes;
    private Spinner spinnerDay;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_plan_details, null);
        initViews(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Enter Details");

        Bundle bundle = getArguments();
        if (bundle != null) {
            final Training training = bundle.getParcelable(TrainingActivity.TRAINING_KEY);
            if (training != null) {
                txtName.setText(training.getName());

                edtTxtMinutes.addTextChangedListener(minuteTextWatcher);

                btnDismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String day = spinnerDay.getSelectedItem().toString();
                        int minutes = Integer.parseInt(edtTxtMinutes.getText().toString());
                        Plan plan = new Plan(training, minutes, day, false);

                        try {
                            passPlanInterface = (PassPlanInterface) getActivity();
                            passPlanInterface.getPlan(plan);
                            dismiss();
                        }catch (ClassCastException e) {
                            e.printStackTrace();
                            dismiss();
                        }
                    }
                });
            }
        }

        return builder.create();
    }

    private void initViews(View view) {
        btnDismiss = view.findViewById(R.id.btnDismiss);
        btnAdd = view.findViewById(R.id.btnAdd);
        txtName = view.findViewById(R.id.txtName);
        edtTxtMinutes = view.findViewById(R.id.edtTxtMinutes);
        spinnerDay = view.findViewById(R.id.spinnerDays);
    }

    private TextWatcher minuteTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String minutes = edtTxtMinutes.getText().toString();
            btnAdd.setEnabled(!minutes.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
