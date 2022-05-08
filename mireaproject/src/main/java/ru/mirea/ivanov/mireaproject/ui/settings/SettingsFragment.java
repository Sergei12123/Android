package ru.mirea.ivanov.mireaproject.ui.settings;

import static ru.mirea.ivanov.mireaproject.MainActivity.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.mirea.ivanov.mireaproject.R;
import ru.mirea.ivanov.mireaproject.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    final String SAVED_LOOPING_AUDIO = "SAVED_LOOPING_AUDIO";

    final String SAVED_ADDRESS = "SAVED_ADDRESS";

    final String SAVED_CALCULATE_WRONG = "SAVED_CALCULATE_WRONG";

    private Switch aSwitch;

    private EditText editText1, editText2;

    private Button save1,save2;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        editText1 = root.findViewById(R.id.editTextDelitNol);
        editText1.setText(preferences.getString(SAVED_CALCULATE_WRONG, "https://developer.android.com"));

        editText2 = root.findViewById(R.id.editTextAddress);
        editText2.setText(preferences.getString(SAVED_ADDRESS, "Делить на ноль нельзя"));

        aSwitch = root.findViewById(R.id.switch1);
        aSwitch.setChecked(preferences.getBoolean(SAVED_LOOPING_AUDIO,false));


        root.findViewById(R.id.button).setOnClickListener(this::onSaveClick);
        return root;
    }

    public void onSaveClick(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(SAVED_LOOPING_AUDIO, aSwitch.isChecked());
        editor.putString(SAVED_ADDRESS, editText2.getText().toString());
        editor.putString(SAVED_CALCULATE_WRONG, editText1.getText().toString());

        editor.apply();
    }
}