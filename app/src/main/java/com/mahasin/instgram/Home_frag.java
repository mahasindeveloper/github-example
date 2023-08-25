package com.mahasin.instgram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomButton;

import com.ablanco.zoomy.TapListener;
import com.ablanco.zoomy.Zoomy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class Home_frag extends Fragment {

    RecyclerView recyclerView,recyclerView2;
    ProgressBar progressBar;
    HashMap<String,String>hashMap;
    ArrayList<HashMap<String,String>>arrayList=new ArrayList<>();

    HashMap<String,String>hashMap2;
    ArrayList<HashMap<String,String>>arrayList2=new ArrayList<>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View myView=inflater.inflate(R.layout.fragment_home_frag, container, false);

        recyclerView=myView.findViewById(R.id.recyclerView);
        progressBar=myView.findViewById(R.id.progressBar);
        recyclerView2=myView.findViewById(R.id.recyclerView2);





        progressBar.setVisibility(View.VISIBLE);
        String url2="https://maha786.000webhostapp.com/apps/instgram_story.json";
        JsonArrayRequest jsonArrayRequest2=new JsonArrayRequest(Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);

                for (int x=1;x<response.length();x++){

                    try {
                        JSONObject jsonObject=response.getJSONObject(x);
                        String image_url=jsonObject.getString("image_url");
                        String title=jsonObject.getString("title");

                        hashMap2=new HashMap<>();
                        hashMap2.put("image_url",image_url);
                        hashMap2.put("title",title);
                        arrayList2.add(hashMap2);




                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }

                MyAdapter2 myAdapter2=new MyAdapter2();
                recyclerView2.setAdapter(myAdapter2);






            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue2=Volley.newRequestQueue(getContext());
        requestQueue2.add(jsonArrayRequest2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));












        progressBar.setVisibility(View.VISIBLE);
        String url="https://mahasin786.000webhostapp.com/App/post_item.json";
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);

                try {

                    for (int x=0;x<response.length();x++){
                        JSONObject jsonObject=response.getJSONObject(x);
                        String profile=jsonObject.getString("profile");
                        String name=jsonObject.getString("name");
                        String thum=jsonObject.getString("thum");
                        String username=jsonObject.getString("username");
                        String bio=jsonObject.getString("bio");
                        String commentimage=jsonObject.getString("commentimage");

                        hashMap=new HashMap<>();
                        hashMap.put("profile",profile);
                        hashMap.put("name",name);
                        hashMap.put("thum",thum);
                        hashMap.put("username",username);
                        hashMap.put("bio",bio);
                        hashMap.put("commentimage",commentimage);
                        arrayList.add(hashMap);


                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Server",error.toString());

            }
        });

        RequestQueue requestQueue=Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);






        MyAdapter myAdapter=new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        return  myView;

    }

    //=========================Adapter===================================
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        public class MyViewHolder extends RecyclerView.ViewHolder{
            CircleImageView tv_profile;
            TextView tv_name,tv_bio,tv_username;
            ImageView tv_thum,tv_commentimage,share;
            CardView follow;
            TextView followchgange;
            ImageView more;



            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_profile=itemView.findViewById(R.id.tv_profile);
                tv_name=itemView.findViewById(R.id.tv_name);
                tv_bio=itemView.findViewById(R.id.tv_bio);
                tv_username=itemView.findViewById(R.id.tv_username);
                tv_thum=itemView.findViewById(R.id.tv_thum);
                tv_commentimage=itemView.findViewById(R.id.tv_commentimage);
                share=itemView.findViewById(R.id.share);
                follow=itemView.findViewById(R.id.follow);
                followchgange=itemView.findViewById(R.id.followchgange);
                more=itemView.findViewById(R.id.more);





            }
        }



        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=getLayoutInflater();
            View view=layoutInflater.inflate(R.layout.home_item_post,parent,false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            HashMap<String,String> hashMap=arrayList.get(position);
            String profile=hashMap.get("profile");
            String name=hashMap.get("name");
            String thum=hashMap.get("thum");
            String username=hashMap.get("username");
            String bio=hashMap.get("bio");
            String commentimage=hashMap.get("commentimage");

            Zoomy.Builder builder = new Zoomy.Builder(getActivity())
                    .target(holder.tv_thum)
                    .enableImmersiveMode(false)
                    .animateZooming(false)
                            .tapListener(new TapListener() {
                                @Override
                                public void onTap(View v) {

                                }
                            });
            builder.register();

            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Share", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(android.content.Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(android.content.Intent.EXTRA_TEXT, "TEXT"+holder.tv_thum);
                }
            });

            holder.follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder.followchgange.setText("Follwing");
                }
            });

            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
                    bottomSheetDialog.setContentView(R.layout.button_sheet);
                    // Set the peek height
                    LinearLayout share=bottomSheetDialog.findViewById(R.id.share);
                    LinearLayout unfollow=bottomSheetDialog.findViewById(R.id.unfollow);



                    share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Toast.makeText(getContext(), "Share", Toast.LENGTH_SHORT).show();
                            bottomSheetDialog.dismiss();

                        }
                    });
                    unfollow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "Unfollow", Toast.LENGTH_SHORT).show();
                            bottomSheetDialog.cancel();
                        }
                    });


                    bottomSheetDialog.show();



                }
            });




            Picasso.get()
                    .load(profile)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.tv_profile);
              Picasso.get()
                    .load(commentimage)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.tv_commentimage);

            Picasso.get()
                    .load(profile)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.tv_profile);

            Picasso.get()
                    .load(thum)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.tv_thum);
            holder.tv_name.setText(name);
            holder.tv_username.setText(username);
            holder.tv_bio.setText(bio);





        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }




    }

    //====================================================
    public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder2>{

        public class MyViewHolder2 extends RecyclerView.ViewHolder{
            CircleImageView profile_image;
            TextView S_text;


            public MyViewHolder2(@NonNull View itemView) {
                super(itemView);
                profile_image=itemView.findViewById(R.id.profile_image);
                S_text=itemView.findViewById(R.id.S_text);

            }
        }


        @NonNull
        @Override
        public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=getLayoutInflater();
            View view=layoutInflater.inflate(R.layout.home_scrool,parent,false);
            return new MyViewHolder2(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {

      hashMap2=arrayList2.get(position);

      String profile2=hashMap2.get("image_url");
      String text=hashMap2.get("title");

            Picasso.get()
                    .load(profile2)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.profile_image);

            holder.S_text.setText(text);


        }

        @Override
        public int getItemCount() {
            return arrayList2.size();
        }




    }




}