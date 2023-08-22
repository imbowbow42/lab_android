package com.example.exe71;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<Contact> contacts;
    private List<Contact> oldContacts;
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public MyCustomAdapter(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
        this.oldContacts = contacts;
        //notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_contact,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Contact contact = this.contacts.get(position);
        holder.tvName.setText(contact.getName());

        holder.tvNumber.setText(contact.getNumber());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    public void setOnClick(OnItemClicked onClick){
        this.onClick=onClick;
    }

    @Override
    public int getItemCount() {
        return this.contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvNumber = itemView.findViewById(R.id.tv_number);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty())
                {
                    contacts = oldContacts;
                }
                else
                {
                    List<Contact> contact = new ArrayList<>();
                    for (Contact user: oldContacts)
                    {
                        if(user.getName().toLowerCase().contains(strSearch.toLowerCase())
                                || user.getNumber().replaceAll("[^A-Za-z0-9]","").toLowerCase().contains(strSearch.toLowerCase()))
                        {
                            contact.add(user);
                        }
                    }

                    contacts = contact;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contacts;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contacts = (List<Contact>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
