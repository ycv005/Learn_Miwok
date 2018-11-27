package com.example.android.miwok;

import android.app.Activity;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    //here, i am inherting Arraylist and telling the all the data source will be of type ColorWord

    public WordAdapter(Activity context, ArrayList<Word> word){
        //Here, we initialize the internal storage of ArrayAdapter for the given context and list.
        //Here, 2nd argument is used to inflate the ArrayAdapter but since we are using Custom ArrayAdapter,
        //we pass 0.
        super(context,0,word);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //this method is get called when the list view is showing the list item at a given position
        //listView pass the view which is convertView, and listView pass the whole list to the parent or you can say, it passes itself
        //purpose of the getView is to return the list item view to the ListView
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        //creating a reference variable that store the position of the object in the list.
        Word currentColorActivity= getItem(position);
        //
        TextView tv1= (TextView)listItemView.findViewById(R.id.text_miwok);
        TextView tv2= (TextView)listItemView.findViewById(R.id.text_english);
        ImageView iv1= (ImageView)listItemView.findViewById(R.id.image_represent);

        //we are using here, currentNumberActivity because it contains the current position.
        tv1.setText(currentColorActivity.getMivok_word());
        tv2.setText(currentColorActivity.getEnglish_word());
        if(currentColorActivity.getImgagePresence()){
            iv1.setImageResource(currentColorActivity.getImageResourceId());
        iv1.setVisibility(View.VISIBLE);}
            else{
            iv1.setVisibility(View.GONE);   //making the varable gone forever, it will not take space even.
        }
        return listItemView;

    }
}