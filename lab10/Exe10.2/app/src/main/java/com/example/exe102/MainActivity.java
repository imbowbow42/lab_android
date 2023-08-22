package com.example.exe102;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class MainActivity extends AppCompatActivity {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    RequestQueue queue;

    Spinner spinner;
    MaterialTextView tvTime;
    ArrayAdapter<String> spinnerAdapter;
    RecyclerView rv;
    Adapter adapter;

    ThemeColor themeColor;

    List<String> spinnerItems = new ArrayList<>();
    List<Statistic> statisticsList = new ArrayList<>();
    ConcurrentHashMap<String, JsonObject> objectsMap = new ConcurrentHashMap<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        spinnerAdapter = new ArrayAdapter<>(MainActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        queue = Volley.newRequestQueue(this);
        StringRequest getRequest = new StringRequest(Request.Method.GET,
                "https://covid-193.p.rapidapi.com/statistics",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JsonElement je = JsonParser.parseString(response);
                        JsonObject jsonObject = je.getAsJsonObject();
                        for (JsonElement e: jsonObject.getAsJsonArray("response")) {
                            JsonObject o = e.getAsJsonObject();
                            if (!Objects.equals(o.get("country").getAsString(), "All")) {
                                spinnerItems.add(o.get("country").getAsString().trim());
                                spinnerAdapter.notifyDataSetChanged();
                                objectsMap.put(o.get("country").getAsString().trim(), o);
                            }
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            spinnerItems.sort(new Comparator<String>() {
                                @Override
                                public int compare(String s, String t1) {
                                    return s.compareTo(t1);
                                }
                            });
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d("ERROR","error => "+error.toString());
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("X-RapidAPI-Key", "6b0ff211bfmsh499fc69ffea17dbp1c2becjsn9ac94b7a197d");
                params.put("X-RapidAPI-Host", "covid-193.p.rapidapi.com");

                return params;
            }
        };
        queue.add(getRequest);


    }

    @Override
    protected void onStart() {
        super.onStart();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) spinner.getSelectedView();
                String selectedCountry = tv.getText().toString();
                JsonElement je = Objects.requireNonNull(objectsMap.get(selectedCountry)).get("cases");
                JsonElement confirmed = je.getAsJsonObject().get("total");
                JsonElement recovered = je.getAsJsonObject().get("recovered");
                JsonElement critical = je.getAsJsonObject().get("critical");
                JsonElement deaths = Objects.requireNonNull(objectsMap.get(selectedCountry)).get("deaths").getAsJsonObject().get("total");
                JsonElement updatedTime = Objects.requireNonNull(objectsMap.get(selectedCountry)).get("time");
                statisticsList.clear();
                if (!confirmed.isJsonNull())
                    statisticsList.add(new Statistic(Label.CONFIRMED, confirmed.getAsString()));
                if (!recovered.isJsonNull())
                    statisticsList.add(new Statistic(Label.RECOVERED, recovered.getAsString()));
                if (!critical.isJsonNull())
                    statisticsList.add(new Statistic(Label.CRITICAL, critical.getAsString()));
                if (!deaths.isJsonNull())
                    statisticsList.add(new Statistic(Label.DEATHS, deaths.getAsString()));

                if (!updatedTime.isJsonNull()) {
                    tvTime.setText("Updated on: " + updatedTime.getAsString());
                } else tvTime.setText("Updated on: N/A");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                System.out.println("Nothing");
            }
        });
    }

    private  void init() {
        themeColor = new ThemeColor(getTheme(), getWindow());
        tvTime = findViewById(R.id.tv_time);
        spinner = findViewById(R.id.spinner);
        rv = findViewById(R.id.rv);
        adapter = new Adapter(this, statisticsList);
        adapter.notifyDataSetChanged();
        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rv.setAdapter(adapter);
    }
}