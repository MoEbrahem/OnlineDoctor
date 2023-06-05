package com.example.onlinedoctor.breast_cancer;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinedoctor.History;
import com.example.onlinedoctor.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BreastCancer extends AppCompatActivity {
    EditText concave_points_mean, area_mean, radius_mean, perimeter_mean, concavity_mean;
    Button predict, database;
    TextView result;
    String url = "http://192.168.1.35:5000/predict_breast";
    myDbAdapter helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breast_cancer);
        concave_points_mean = findViewById(R.id.concave_points);
        area_mean = findViewById(R.id.area_mean);
        radius_mean = findViewById(R.id.radius_mean);
        perimeter_mean = findViewById(R.id.perimeter_mean);
        concavity_mean = findViewById(R.id.concavity_mean);
        predict = findViewById(R.id.predict);
        result = findViewById(R.id.result);
        helper = new myDbAdapter(this);


        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (concave_points_mean.getText().toString().isEmpty()) {
                    concave_points_mean.setError("Cannot be Empty");
                } else if (area_mean.getText().toString().isEmpty()) {
                    area_mean.setError("Cannot be Empty");
                } else if (radius_mean.getText().toString().isEmpty()) {
                    radius_mean.setError("Cannot be Empty");
                } else if (perimeter_mean.getText().toString().isEmpty()) {
                    perimeter_mean.setError("Cannot be Empty");
                } else if (concavity_mean.getText().toString().isEmpty()) {
                    concavity_mean.setError("Cannot be Empty");
                } else {

                    // hit the API -> Volley
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        JSONObject jsonObject = new JSONObject(response);
                                        String data = jsonObject.getString("diagnosis");
                                        if (data.equals("1")) {
                                            result.setTextColor(Color.parseColor("#D21312"));
                                            concavity_mean.setText("");
                                            area_mean.setText("");
                                            radius_mean.setText("");
                                            perimeter_mean.setText("");
                                            concave_points_mean.setText("");
                                            result.setText("Malignant");
                                        } else {
                                            result.setTextColor(Color.parseColor("#98EECC"));
                                            result.setText("Benign");
                                        }
                                    } catch (JSONException e) {

                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    String err = (error.getMessage() == null) ? "Failed! Please Try Again" : error.getMessage();
                                    Toast.makeText(BreastCancer.this, err, Toast.LENGTH_LONG).show();
                                    Log.d("API ERROR : ", err);
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map params = new HashMap();
                            params.put("concave_points_mean", concave_points_mean.getText().toString());
                            params.put("area_mean", area_mean.getText().toString());
                            params.put("radius_mean", radius_mean.getText().toString());
                            params.put("perimeter_mean", perimeter_mean.getText().toString());
                            params.put("concavity_mean", concavity_mean.getText().toString());
                            return params;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(BreastCancer.this);

                    queue.add(stringRequest);
                }
            }
        });
    }

    public void addUser(View view) {
        String t1 = concave_points_mean.getText().toString();
        String t2 = area_mean.getText().toString();
        String t3 = radius_mean.getText().toString();
        String t4 = perimeter_mean.getText().toString();
        String t5 = concavity_mean.getText().toString();

        if (t1.isEmpty() || t2.isEmpty() || t3.isEmpty() || t4.isEmpty() || t5.isEmpty()) {
            Message.message(getApplicationContext(), "Enter the complete fields");
        } else {
            long id = helper.insertData(t1, t2, t3, t4, t5);
            if (id <= 0) {
                Message.message(getApplicationContext(), "Insertion Unsuccessful");
                concave_points_mean.setText("");
                area_mean.setText("");
                radius_mean.setText("");
                perimeter_mean.setText("");
                concavity_mean.setText("");
            } else {
                Message.message(getApplicationContext(), "your information have been saved");
                concave_points_mean.setText("");
                area_mean.setText("");
                radius_mean.setText("");
                perimeter_mean.setText("");
                concavity_mean.setText("");
            }
        }
    }

    public void viewdata(View view) {
        startActivity(new Intent(this, History.class));

    }

}

//Message.message(this, data);