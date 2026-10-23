package com.tahirsallie.smartpantry.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.materialswitch.MaterialSwitch;
import com.tahirsallie.smartpantry.R;
import com.tahirsallie.smartpantry.util.AppPrefs;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // --- Expiring-soon toggle ---
        MaterialSwitch expirySwitch = view.findViewById(R.id.switch_expiry);
        expirySwitch.setChecked(AppPrefs.isExpiryHighlightEnabled(requireContext()));
        expirySwitch.setOnCheckedChangeListener((button, isChecked) ->
                AppPrefs.setExpiryHighlightEnabled(requireContext(), isChecked));

        // --- Default unit spinner ---
        Spinner unitSpinner = view.findViewById(R.id.spinner_default_unit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);

        String saved = AppPrefs.getDefaultUnit(requireContext());
        String[] units = getResources().getStringArray(R.array.units);
        for (int i = 0; i < units.length; i++) {
            if (units[i].equalsIgnoreCase(saved)) {
                unitSpinner.setSelection(i);
                break;
            }
        }

        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                AppPrefs.setDefaultUnit(requireContext(), parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }
}
