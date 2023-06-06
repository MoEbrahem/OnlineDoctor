package com.example.onlinedoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class Heart extends AppCompatActivity {

    private EditText cp, thalachh, slp, restecg, chol, trtbps, fbs, oldpeak;
    private Button predict;
    private ImageButton info1, info2, info3, info4, info5, info6, info7, info8;
    private TextView result;
    private Button tips;
    String url = "http://192.168.1.35:5000/predict_heart";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);

        cp = findViewById(R.id.cp);
        thalachh = findViewById(R.id.thalachh);
        slp = findViewById(R.id.slp);
        restecg = findViewById(R.id.restecg);
        chol = findViewById(R.id.chol);
        trtbps = findViewById(R.id.trtbps);
        fbs = findViewById(R.id.fbs);
        oldpeak = findViewById(R.id.oldpeak);
        predict = findViewById(R.id.predict);
        result = findViewById(R.id.result);


        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cp.getText().toString().isEmpty() || (!cp.getText().toString().equals("0")
                        && !cp.getText().toString().equals("1") && !cp.getText().toString().equals("2") && !cp.getText().toString().equals("3"))) {
                    cp.setError("Should be in 0-3 range");
                } else if (thalachh.getText().toString().isEmpty() || Integer.parseInt(thalachh.getText().toString()) < 0) {
                    thalachh.setError("Cannot be Empty");
                } else if (slp.getText().toString().isEmpty() || (!slp.getText().toString().equals("0")
                        && !slp.getText().toString().equals("1") && !slp.getText().toString().equals("2"))) {
                    slp.setError("Should be in 0-2 range");
                } else if (restecg.getText().toString().isEmpty() || (!restecg.getText().toString().equals("0")
                        && !restecg.getText().toString().equals("1") && !restecg.getText().toString().equals("2"))) {
                    restecg.setError("Should be in 0-2 range");
                } else if (chol.getText().toString().isEmpty() || Integer.parseInt(chol.getText().toString()) < 0) {
                    chol.setError("Cannot be Empty");
                } else if (trtbps.getText().toString().isEmpty() || Integer.parseInt(trtbps.getText().toString()) < 0) {
                    trtbps.setError("Cannot be Empty");
                } else if (fbs.getText().toString().isEmpty() || (!fbs.getText().toString().equals("0") && !fbs.getText().toString().equals("1"))) {
                    fbs.setError("Should be in 0-1 range");
                } else if (oldpeak.getText().toString().isEmpty() || Float.parseFloat(oldpeak.getText().toString()) < 0) {
                    oldpeak.setError("Cannot be Empty");
                } else {

                    //API -> Volley

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String data = jsonObject.getString("hearth_disease");


                                        if (data.equals("0")) {
                                            result.setTextColor(Color.parseColor("#5bdeac"));
                                            result.setText("Result: No Heart Disease");
                                        } else {
                                            result.setTextColor(Color.parseColor("#EC4C4C"));
                                            result.setText("Result: Heart Disease");
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String err = (error.getMessage() == null) ? "Failed! Please Try Again" : error.getMessage();
                            Toast.makeText(Heart.this, err, Toast.LENGTH_SHORT).show();
                            Log.d("API ERROR : ", err);
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {

                            Map<String, String> params = new HashMap();
                            params.put("cp", cp.getText().toString());
                            params.put("thalachh", thalachh.getText().toString());
                            params.put("slp", slp.getText().toString());
                            params.put("restecg", restecg.getText().toString());
                            params.put("chol", chol.getText().toString());
                            params.put("trtbps", trtbps.getText().toString());
                            params.put("fbs", fbs.getText().toString());
                            params.put("oldpeak", oldpeak.getText().toString());

                            return params;
                        }
                    };

                    RequestQueue queue = Volley.newRequestQueue(Heart.this);
                    queue.add(stringRequest);
                }
            }

        });


    }
}
