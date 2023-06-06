package com.example.onlinedoctor;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Liver extends AppCompatActivity {

    EditText Age, Gender, Total_Bilirubin, Direct_Bilirubin, Alkaline_Phosphotase, Alamine_Aminotransferase,
            Aspartate_Aminotransferase, Total_Protiens, Albumin, Albumin_and_Globulin_Ratio;
    Button predict;
    TextView result;
    String url= "http://192.168.1.40:5000/predict_liver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liver);

        Age =findViewById(R.id.Age);
        Gender =findViewById(R.id.Gender);
        Total_Bilirubin =findViewById(R.id.Total_Bilirubin);
        Direct_Bilirubin =findViewById(R.id.Direct_Bilirubin);
        Alkaline_Phosphotase =findViewById(R.id.Alkaline_Phosphotase);
        Alamine_Aminotransferase =findViewById(R.id.Alamine_Aminotransferase);
        Aspartate_Aminotransferase =findViewById(R.id.Aspartate_Aminotransferase);
        Total_Protiens =findViewById(R.id.Total_Protiens);
        Albumin =findViewById(R.id.Albumin);
        Albumin_and_Globulin_Ratio =findViewById(R.id.Albumin_and_Globulin_Ratio);
        predict =findViewById(R.id.predict);
        result =findViewById(R.id.result);

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject =new JSONObject(response);
                                    String data = jsonObject.getString("placement ");
                                    if(data.equals("1")) {
                                        result.setText(" The patient isNot infected ");
                                    }else{
                                        result.setText(" The patient is infected ");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Liver.this,error.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("Age",Age.getText().toString());
                        params.put("Gender",Gender.getText().toString());
                        params.put("Total_Bilirubin",Total_Bilirubin.getText().toString());
                        params.put("Direct_Bilirubin",Direct_Bilirubin.getText().toString());
                        params.put("Alkaline_Phosphotase",Alkaline_Phosphotase.getText().toString());
                        params.put("Alamine_Aminotransferase",Alamine_Aminotransferase.getText().toString());
                        params.put("Aspartate_Aminotransferase",Aspartate_Aminotransferase.getText().toString());
                        params.put("Total_Protiens",Total_Protiens.getText().toString());
                        params.put("Albumin",Albumin.getText().toString());
                        params.put("Albumin_and_Globulin_Ratio",Albumin_and_Globulin_Ratio.getText().toString());

                        return params;
                    }
                };
                RequestQueue queue= Volley.newRequestQueue(Liver.this);
                queue.add(stringRequest);
            }
        });

    }
}