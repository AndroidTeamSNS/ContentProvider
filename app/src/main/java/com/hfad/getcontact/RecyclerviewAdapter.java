package com.hfad.getcontact;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyViewHolder> {
    private Context context;
    private List<ContactListModel> list;

    public RecyclerviewAdapter(Context context, List<ContactListModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (list.get(position).getImage().equals("NULL")) {
            holder.contactImage.setImageResource(R.drawable.avatar_image);
        } else {
            holder.contactImage.setImageURI(Uri.parse(list.get(position).getImage()));
        }
        holder.phoneNumber.setText(list.get(position).getPhoneNumber());
        holder.name.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView contactImage;
        TextView name, phoneNumber;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name_id);
            phoneNumber = itemView.findViewById(R.id.tv_phoneNumber_id);
            contactImage = itemView.findViewById(R.id.contact_image_id);
        }
    }
}
