package ru.mirea.ivanov.mireaproject.ui.calculator;

import static ru.mirea.ivanov.mireaproject.MainActivity.preferences;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.mirea.ivanov.mireaproject.R;
import ru.mirea.ivanov.mireaproject.databinding.CalculatorFragmentBinding;

public class CalculatorFragment extends Fragment {

    private CalculatorFragmentBinding binding;

    private Integer param1, param2;

    private String action;

    private EditText editText;

    public static CalculatorFragment newInstance() {
        return new CalculatorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = CalculatorFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        editText = root.findViewById(R.id.editTextNumber);
        root.findViewById(R.id.buttonPlus).setOnClickListener(view -> onButtonClickAction("plus"));
        root.findViewById(R.id.buttonMinus).setOnClickListener(view -> onButtonClickAction("minus"));
        root.findViewById(R.id.buttonUmnozit).setOnClickListener(view -> onButtonClickAction("umnozit"));
        root.findViewById(R.id.buttonRazdelit).setOnClickListener(view -> onButtonClickAction("razdelit"));
        root.findViewById(R.id.buttonRavno).setOnClickListener(this::onButtonRavnoClick);
        root.findViewById(R.id.buttonClear).setOnClickListener(this::onButtonClearClick);

        return root;
    }

    public void makeAction() {
        switch (action) {
            case "plus":
                param1 += param2;
                break;
            case "minus":
                param1 -= param2;
                break;
            case "umnozit":
                param1 *= param2;
                break;
            case "razdelit":
                if (param2 == 0) {
                    Toast.makeText(getActivity(), preferences.getString("SAVED_CALCULATE_WRONG", "Делить на ноль нельзя!"), Toast.LENGTH_LONG).show();
                    editText.setText(param1.toString());
                    return;
                }
                param1 /= param2;
                break;
        }
    }

    public void onButtonClickAction(final String action) {
        if (!editText.getText().toString().equals("")) {
            if (param1 == null) {
                param1 = Integer.parseInt(editText.getText().toString());
            } else {
                param2 = Integer.parseInt(editText.getText().toString());
                makeAction();
            }
            editText.getText().clear();
            this.action = action;
        }
    }

    public void onButtonRavnoClick(View view) {
        onButtonClickAction(action);
        if (param1 != null) editText.setText(param1.toString());
        param1 = null;
        param2 = null;
    }

    public void onButtonClearClick(View view) {
        param1 = null;
        param2 = null;
        action = null;
        editText.getText().clear();
    }

}