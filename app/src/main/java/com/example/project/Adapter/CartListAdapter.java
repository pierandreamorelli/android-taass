package com.example.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.Interface.ChangeNumberItemsListener;
import com.example.project.Model.ProductOrder;
import com.example.project.R;
import com.example.project.Utilities.ManagementCart;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private ArrayList<ProductOrder> plist;
    private ManagementCart managementCart;
    private ChangeNumberItemsListener changeNumberItemsListener;

    public CartListAdapter(ArrayList<ProductOrder> plist, Context context, ChangeNumberItemsListener changeNumberItemsListener) {

        this.plist = plist;
        managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_card, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(plist.get(position).getProduct().getName());
        holder.feeEachItem.setText(String.valueOf(plist.get(position).getProduct().getPrice()));
        holder.totalEachItem.setText(String.valueOf(Math.round((plist.get(position).getNum() * plist.get(position).getProduct().getPrice()) * 100.0) / 100.0));
        holder.num.setText(String.valueOf(plist.get(position).getNum()));

        //int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(plist.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());
        String pic_Url = plist.get(position).getProduct().getPictureUrl();
        Glide.with(holder.itemView.getContext())
                .load(pic_Url)
                .into(holder.pic);
        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.plusNumberFood(plist, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });



        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.MinusNumerFood(plist, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return plist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button prfojewo;
        TextView title, feeEachItem;
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem, num;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prfojewo = itemView.findViewById(R.id.button);
            title = itemView.findViewById(R.id.title2Txt);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            pic = itemView.findViewById(R.id.picCard);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItemTxt);
            plusItem = itemView.findViewById(R.id.plusCardBtn);
            minusItem = itemView.findViewById(R.id.minusCardBtn);

        }
    }
}
