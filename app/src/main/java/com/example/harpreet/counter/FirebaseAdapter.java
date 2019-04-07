package com.example.harpreet.counter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.harpreet.counter.Utils.Data;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class FirebaseAdapter extends FirestoreRecyclerAdapter<Data,FirebaseAdapter.ViewHolder> {

    private OnitemClickListener listener;

    public FirebaseAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View V=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_list_item,viewGroup,false);
        return new ViewHolder(V);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Data model) {

        holder.city.setText(model.getCity());
      //  holder.current_temp.setText(model.getCurrent_temp());
        holder.date.setText(model.getDate());
 //       holder.wind.setText(model.getWind());
  //      holder.current_humidity.setText(model.getCurrent_humidity());
        holder.condition.setText(model.getCurrent_condition());
        String imagevalue="a"+model.getCurrent_image();
   //     int id=holder.itemView.getResources().getIdentifier(imagevalue,"drawable", MainActivity.PACKAGE_NAME);
        //Drawable res = holder.itemView.getResources().getDrawable(id);
     //   holder.current_image.setImageResource(id);

    }

    //this method will take care of deleting the document form firebase and only feature is to be added to invoke this method
    public void deleteItem(int position)
    {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

       public  TextView city;
       public TextView current_temp,current_condition,condition;
        public TextView date,wind,current_humidity;
        public ImageView current_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            city = itemView.findViewById(R.id.city_name);
            date = itemView.findViewById(R.id.date);
            condition=itemView.findViewById(R.id.condition);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listener!=null)
                    {
                        listener.OnitemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }
    public interface OnitemClickListener{
        void OnitemClick(DocumentSnapshot snapshot,int position);
    }

    public void setOnItemClickListener(OnitemClickListener onItemClickListener){
    this.listener=onItemClickListener;
    }
}
