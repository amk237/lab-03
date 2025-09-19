package com.example.listycitylab3;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {
    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] cities = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON" };
        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }
        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> AddCityFragment.newInstanceForAdd().show(getSupportFragmentManager(), "AddCity"));

        cityList.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            City current = dataList.get(position);
            AddCityFragment.newInstanceForEdit(position, current).show(getSupportFragmentManager(), "EditCity");
        });
    }

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCity(int index, City updated) {
        if (index >= 0 && index < dataList.size()) {
            City existing = dataList.get(index);
            existing.setName(updated.getName());
            existing.setProvince(updated.getProvince());
            cityAdapter.notifyDataSetChanged();
        }
    }
}
