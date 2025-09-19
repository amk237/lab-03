package com.example.listycitylab3;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    public interface AddCityDialogListener {
        void addCity(City city);
        void updateCity(int index, City updated);
    }

    private static final String ARG_MODE = "mode";
    private static final String ARG_INDEX = "index";
    private static final String ARG_CITY = "city";
    private AddCityDialogListener listener;

    public static AddCityFragment newInstanceForAdd() {
        AddCityFragment f = new AddCityFragment();
        Bundle b = new Bundle();
        b.putString(ARG_MODE, "add");
        f.setArguments(b);
        return f;
    }

    public static AddCityFragment newInstanceForEdit(int index, City city) {
        AddCityFragment f = new AddCityFragment();
        Bundle b = new Bundle();
        b.putString(ARG_MODE, "edit");
        b.putInt(ARG_INDEX, index);
        b.putSerializable(ARG_CITY, city);
        f.setArguments(b);
        return f;
    }

    public AddCityFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        String mode = getArguments() != null ? getArguments().getString(ARG_MODE, "add") : "add";
        String title = mode.equals("edit") ? "Edit city" : "Add a city";

        if (mode.equals("edit") && getArguments() != null) {
            City c = (City) getArguments().getSerializable(ARG_CITY);
            if (c != null) {
                editCityName.setText(c.getName());
                editProvinceName.setText(c.getProvince());
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle(title)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(mode.equals("edit") ? "Save" : "Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if (mode.equals("edit")) {
                        int index = getArguments().getInt(ARG_INDEX, -1);
                        listener.updateCity(index, new City(cityName, provinceName));
                    } else {
                        listener.addCity(new City(cityName, provinceName));
                    }
                })
                .create();
    }
}
