package com.mahasin.instgram;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Post extends AppCompatActivity {

    ImageView selectButton;
    ImageView imageView;
    Bitmap bitmap;
    String encodedImage;
    ProgressBar progressBar;
    MaterialToolbar materialToolbar;
    EditText ed_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);



        selectButton=findViewById(R.id.selectButton);
        materialToolbar=findViewById(R.id.materialToolbar);
        progressBar=findViewById(R.id.progressBar);
        imageView=findViewById(R.id.imageView);
        ed_text=findViewById(R.id.ed_text);

        String title=ed_text.getText().toString();



        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemC=item.getItemId();
                progressBar.setVisibility(View.VISIBLE);

                if (itemC==R.id.uploadButton){

                            String url = "https://mahasin786.000webhostapp.com/uploadimage.php";

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(Post.this, ""+response, Toast.LENGTH_SHORT).show();

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Log.d("serVe",error.getMessage());
                                    Toast.makeText(Post.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();


                                }
                            }){

                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String,String> params=new HashMap<>();
                                    params.put("image",encodedImage);
                                    return params;
                                }
                            };


                            RequestQueue queue = Volley.newRequestQueue(Post.this);
                            queue.add(stringRequest);


                            String urll ="https://mahasin786.000webhostapp.com/insert.php?t="+title;

                            StringRequest stringRequest1=new StringRequest(Request.Method.GET, urll, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressBar.setVisibility(View.GONE);

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {


                                }
                            });


                   // queue.add(stringRequest1);



                }

                return false;
            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dexter.withActivity(Post.this)
                        .withPermission(READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image"),1);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                                permissionToken.continuePermissionRequest();

                            }
                        })
                        .check();



            }
        });






    }
    //============================================================


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==1 && resultCode==RESULT_OK && data!=null ){
            Uri filePath = data.getData();
            try {
                InputStream inputStream= getContentResolver().openInputStream(filePath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

                imageStore(bitmap);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }


        super.onActivityResult(requestCode, resultCode, data);


    }

    //==========================
    private void imageStore(Bitmap bitmap) {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream);
        byte[] imageByte=stream.toByteArray();

        encodedImage= android.util.Base64.encodeToString(imageByte, Base64.DEFAULT);

    }





}