package com.mahasin.instgram;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottom_navi;

    Bitmap bitmap;
    String encodedImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_navi = findViewById(R.id.bottom_navi);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, new Home_frag());
        fragmentTransaction.commit();

        bottom_navi.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemcount = item.getItemId();

                if (itemcount == R.id.home) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.frameLayout, new Home_frag());
                    fragmentTransaction.commit();

                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                } else if (itemcount == R.id.search) {
                    Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_SHORT).show();
                } else if (itemcount == R.id.creat) {

                  startActivity(new Intent(MainActivity.this,Post.class));

                    Toast.makeText(MainActivity.this, "Creat", Toast.LENGTH_SHORT).show();
                } else if (itemcount == R.id.reels) {
                    Toast.makeText(MainActivity.this, "Reels", Toast.LENGTH_SHORT).show();
                } else if (itemcount == R.id.profile) {
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.frameLayout,new Profile_frag());
                    fragmentTransaction.commit();
                    Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                }


                return true;
            }
        });


    }

    //============================================================







}