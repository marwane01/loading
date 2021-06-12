package com.example.loading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.loading.model.ProfileModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends ArrayAdapter<ProfileModel> {
    Context context;
    int resource;
    List<ProfileModel> profileList;


    ProfileAdapter(Context context,int resource,List<ProfileModel> profileList)
    {
        super(context,resource,profileList);
        this.context = context;
        this.resource = resource;
        this.profileList = profileList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView==null){
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resource,null,false);

        }
        ImageView imageView=convertView.findViewById(R.id.image);
        TextView txtname=convertView.findViewById(R.id.txtname);
        TextView txtspecies=convertView.findViewById(R.id.txtspecies);
        TextView txtstatus=convertView.findViewById(R.id.txtstatus);
        TextView txtgender=convertView.findViewById(R.id.txtgender);

        ProfileModel model=profileList.get(position);
        Picasso.get().load(model.getImage()).into(imageView);
        txtname.setText(model.getName());
        txtspecies.setText(model.getSpecies());
        txtstatus.setText(model.getStatus());
        txtgender.setText(model.getGender());

        return convertView;
    }
}
