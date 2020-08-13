package com.example.qbapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qbapp.Classes.Constant;
import com.example.qbapp.Helper.DatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText editusername, editpassword;
    Button btnlogin;
    private DatabaseHelper databaseHelper;
    RadioButton radio_zayed,radio_asher,radio_sphinx;
    // public static final String TAG = LoginActivity.class.getSimpleName();
    // Tag used to cancel the request
    public StringRequest request=null;
    public VolleyError volleyErrorPublic=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        radio_zayed=findViewById(R.id.radio_zayed);
        radio_asher=findViewById(R.id.radio_asher);
        radio_sphinx=findViewById(R.id.radio_sphinx);


        databaseHelper =new DatabaseHelper(this);

        editusername = findViewById(R.id.username);
        /*editusername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){

                    //execute our method for searching
                    Login();
                }

                return false;
            }
        });*/
        editpassword = findViewById(R.id.password);
        editpassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent == null
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){

                    //execute our method for searching
                    Login();
                }

                return false;
            }
        });
        btnlogin = findViewById(R.id.btn_login);
        Log.e("onCreate", "Map");


        //Login();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent gotoMain= new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(gotoMain);
                Login();
            }
        });

    }


    public void Login() {

        if (editusername.getText().toString().isEmpty() ){
            editusername.setError("من فضلك أدخل الاسم");
        }else if ( editpassword.getText().toString().isEmpty()){
            editpassword.setError("من فضلك أدخل كلمه السر");
        }else if ( !Constant.isOnline(LoginActivity.this)){
            editpassword.setError("لايوجد اتصال بالانترنت");
        }else {
            //TAG_TRIP_PRICE + Uri.encode(tripFromSelected, "utf-8").toString() + "/" +
            //        Uri.encode(tripToSelected, "utf-8").toString()
            RequestQueue queue = Volley.newRequestQueue(this);
            // String URL = Constant.LoginURL;
            request = new StringRequest(Request.Method.POST, Constant.LoginURL,

                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            if (response == null){
                                Log.e("onResponse", "response"+response);
                            }
                            String encodedstring = null;
                            try {
                                encodedstring = URLEncoder.encode(response,"ISO-8859-1");
                                response = URLDecoder.decode(encodedstring,"UTF-8");

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            Log.e("onResponse", "response"+response);
                           // Log.e("onResponse", "response"+ Uri.encode(response, "utf-8").toString());
                            Log.e("onResponse", "request"+request);
                            try {
                                JSONObject object = new JSONObject(response);
                                //Log.e("onResponse", "object"+object);

                                //JSONObject object2 = object.getJSONObject("user");
                                //String username = object.getString("status");
                               // Log.e("onResponse", "object2"+object2);

                                String status = object.getString("status");
                                Log.d("onResponse", status);

                                String message = object.getString("message");
                                Log.d("onResponse", message);


                                if (status.equalsIgnoreCase("1")){
                                    Log.d("onResponse", message);

                                    String branch = object.getString("branch");
                                    Log.d("branch", branch);
                                    String statusforuser = object.getString("statusforuser");

                                    Log.d("statusforuser", statusforuser);
                                    String accesslevel = object.getString("accesslevel");
                                    Log.d("statusforuser", accesslevel);

                                    //TODO Chexk status again
                                    if (statusforuser.equalsIgnoreCase("0")) {


                                        if (accesslevel.equalsIgnoreCase("6") ||
                                                accesslevel.equalsIgnoreCase("7")) {

                                            // databaseHelper.DeleteUserDataTables();
                                            databaseHelper.DeletelastordersTable();
                                            Intent gotomain = new Intent(LoginActivity.this, MainActivity.class);
                                            gotomain.putExtra("UserName", editusername.getText().toString());
                                            gotomain.putExtra("Branch", branch);
//                                            gotomain.putExtra("LastOrderId", "");
                                   /* databaseHelper.insertuser(User_ID,username,User_Description,Group_Name,User_status,
                                            User_Department,company,GroupID,ComplexID);*/
                                            startActivity(gotomain);

                                        }else {
                                            Toast.makeText(LoginActivity.this, " ليس لديك صلاحيه", Toast.LENGTH_SHORT).show();

                                        }



                                    }else {
                                        Toast.makeText(LoginActivity.this, "هذا المستخدم غير فعال", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(LoginActivity.this, "تاكد من الاسم وكلمة السر", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            NetworkResponse response = error.networkResponse;
                            String errorMsg = "";
                            if (response != null && response.data != null) {
                                String errorString = new String(response.data);
                                Log.i("log error", errorString);
                            }
                        }
                    }
            ){

                @Override
                protected VolleyError parseNetworkError(VolleyError volleyError) {
                    Log.i("log error no respon", "se6");
                    Log.i("log error no respon", ""+volleyError);
                    volleyErrorPublic=volleyError;
                    return super.parseNetworkError(volleyError);
                }

                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", editusername.getText().toString());
                    params.put("password", editpassword.getText().toString());
                    if (radio_zayed.isChecked()) {
                        params.put("Branch", "1");
                    }else if (radio_asher.isChecked()){
                        params.put("Branch", "2");
                    }else if (radio_sphinx.isChecked()){
                        params.put("Branch", "3");
                    }
                    //params.put("key_1","value_1");
                    // params.put("key_2", "value_2");
//                    Log.i("sending ", params.toString());
//                    Log.i("sending ", ""+request);

                    return params;
                }

            };


            // Add the realibility on the connection.
            request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));

            // Start the request immediately
            queue.add(request);

            if (volleyErrorPublic != null) {
                Toast.makeText(LoginActivity.this, "لم يتم الاتصال بالسيرفر", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

       /* JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constant.LoginURL,
                null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("JsonObject Response",response.toString());*/
                /*try {
                    JSONObject user = response.getJSONObject("user");
                    String firstName = user.getString("firstName");
                    if (firstName.equals("Lokesh")){
                        result = true;
                    }
                    else{
                        result = false;
                    }
                } catch (JSONException e) {
                    Log.d("Web Service Error",e.getMessage());
                    e.printStackTrace();
                }*/
            /*}
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("JsonerrorResponse",volleyError.toString());
            }
        });*/
        /*request.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
    // AppController.getInstance().addToRequestQueue(request);


       /* JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constant.LoginURL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("TimeonResponse", response.toString());
                       // pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
               // pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("username", "mustafa");
                params.put("password", "123");
                Log.e("TimeMap","Map");
                return params;
            }
        };
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,tag_json_obj);
    }*/
}
