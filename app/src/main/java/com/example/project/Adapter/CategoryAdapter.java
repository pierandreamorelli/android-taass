package com.example.project.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.Activity.MainActivity;
import com.example.project.Model.CategoryDomain;
import com.example.project.R;
import com.example.project.Utilities.FetchDataTask;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    public final MainActivity mainActivity;
    ArrayList<CategoryDomain> categoryDomains;

    public CategoryAdapter(ArrayList<CategoryDomain> categoryDomains, MainActivity mainActivity) {
        this.categoryDomains = categoryDomains;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cat, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryName.setText(categoryDomains.get(position).getTitle());
        String picUrl = "";
        switch (position) {
            case 0: {
                picUrl = "cat_11";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_background1));
                break;
            }
            case 1: {
                picUrl = "cat_22";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_background2));
                break;
            }
            case 2: {
                picUrl = "cat_33";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_background3));
                break;
            }
            case 3: {
                picUrl = "cat_44";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_background4));
                break;
            }

        }
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.categoryPic);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (holder.categoryName.getText().toString()){
                    case "All":
                        String url = "http://192.168.1.140:8080/api/products";
                        new FetchDataTask(mainActivity).execute(url,"GET");
                        return;
                    case"Laptops":
                        String url1 = "http://192.168.1.140:8080/api/products/Laptop";
                        new FetchDataTask(mainActivity).execute(url1,"GET");
                        return;
                    case"Components":
                        String url2 = "http://192.168.1.140:8080/api/products/Component";
                        new FetchDataTask(mainActivity).execute(url2,"GET");
                        return;
                    case"Accessories":
                        String url3 = "http://192.168.1.140:8080/api/products/Accessories";
                        new FetchDataTask(mainActivity).execute(url3,"GET");
                        return;
                    default:
                        return;

                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return categoryDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
