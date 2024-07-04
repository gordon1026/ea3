package com.example.ea3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.ArrayList;

public class Map extends AppCompatActivity {

    private Spinner regionSpinner, districtSpinner;
    private RecyclerView recyclerView;
    private java.util.Map<String, List<Stadium>> districtStadiumMap;
    private java.util.Map<String, List<String>> regionMap;
    private ImageView btnBackTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        regionSpinner = findViewById(R.id.region_spinner);
        districtSpinner = findViewById(R.id.district_spinner);
        recyclerView = findViewById(R.id.recycler_view);
        btnBackTo = findViewById(R.id.BackTo);


        regionMap = Mapdata.getRegionMap();
        districtStadiumMap = Mapdata.getDistrictStadiumMap();


        ArrayAdapter<String> regionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>(regionMap.keySet()));
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(regionAdapter);

        btnBackTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRegion = (String) parent.getItemAtPosition(position);
                updateDistrictSpinner(selectedRegion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new StadiumAdapter(new ArrayList<>())); // 初始化空的適配器
    }
    private void updateDistrictSpinner(String region) {
        List<String> districts = regionMap.get(region);
        if (districts != null) {
            ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, districts);
            districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            districtSpinner.setAdapter(districtAdapter);


            districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedDistrict = (String) parent.getItemAtPosition(position);
                    updateRecyclerView(selectedDistrict);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Do nothing
                }
            });
        } else {

            districtSpinner.setAdapter(null);

            recyclerView.setAdapter(new StadiumAdapter(new ArrayList<>()));
            Toast.makeText(this, "該區域沒有地區", Toast.LENGTH_SHORT).show();
        }
    }private void updateRecyclerView(String district) {
        List<Stadium> stadiums = districtStadiumMap.get(district); // 確保這裡是 List<Stadium>
        if (stadiums != null && !stadiums.isEmpty()) {
            StadiumAdapter adapter = new StadiumAdapter(stadiums);
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.setAdapter(new StadiumAdapter(new ArrayList<>()));
            Toast.makeText(this, "該地區沒有體育館", Toast.LENGTH_SHORT).show();
        }
    }
}