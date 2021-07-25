package com.example.loading;

import  androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.loading.model.ProfileModel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    ListView listView;
    final String url = "https://rickandmortyapi.com/api/character?page=";
    List<ProfileModel> alist;
    ProfileAdapter profileAdapter;
    RequestQueue requestQueue;
    public Handler mHandler;
    public View ftView;
    public boolean isLoading = false;
    int num=32;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listview);
        LayoutInflater li =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ftView=li.inflate(R.layout.footer_view,null);
        mHandler=new MyHandler();

        requestQueue = Volley.newRequestQueue(this);


        alist=new ArrayList<>();
        ArrayList<ProfileModel> lstResult = getMoreData(num);


        profileAdapter=new ProfileAdapter(this,R.layout.list_row,alist);

        listView.setAdapter(profileAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (view.getLastVisiblePosition()==totalItemCount-1 && listView.getCount()>19 && isLoading==false){
                    isLoading=true;
                    Thread thread = new ThreadGetMoreData();
                    thread.start();

                }
            }
        });



    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    //Add loading view search processing
                    listView.addFooterView(ftView);
                    break;
                case 1:
                    //update data adapter and UI
                    profileAdapter.addlistItemToAdapter((ArrayList<ProfileModel>)msg.obj);
                    //Remove loading view after  update listview
                    listView.removeFooterView(ftView);
                    isLoading=false;
                default:
                    break;
            }
        }



    }
    private ArrayList<ProfileModel> getMoreData(int num){
        ArrayList<ProfileModel>lst =new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url+num, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("results");

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        alist.add(new ProfileModel(jsonObject));
                    }
                    profileAdapter.notifyDataSetChanged();
                }
                catch (Exception w)
                {
                    Toast.makeText(MainActivity.this,w.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

        return lst;
    }

    public class ThreadGetMoreData extends Thread{
        @Override
        public void run() {

            mHandler.sendEmptyMessage(0);
            //Search more data

            num++;

            ArrayList<ProfileModel> lstResult = getMoreData(num);

            try {
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            Message msg=mHandler.obtainMessage(1,lstResult);
            mHandler.sendMessage(msg);

        }
    }
}
