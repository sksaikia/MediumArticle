package com.webtutsplus.ecommerceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webtutsplus.ecommerceapp.Model.Category;
import com.webtutsplus.ecommerceapp.R;
import com.webtutsplus.ecommerceapp.Utility.OnCategoryItemClickListener;

import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.CategoryViewHolder>{

    private Context context;
    private List<Category> categories;
    private OnCategoryItemClickListener clickListener;

    public HomeCategoryAdapter (Context context, List<Category> categories, OnCategoryItemClickListener clickListener) {
        this.context = context;
        this.categories = categories;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public HomeCategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listitem_home_category_item, parent, false);
        return new HomeCategoryAdapter.CategoryViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryAdapter.CategoryViewHolder holder, int position) {
        Category c = categories.get(position);
        String url = c.getImageUrl();
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.catImage);
        holder.catName.setText(c.getCategoryName());
        holder.catDescription.setText(c.getDescription());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView catImage;
        TextView catId, catName, catDescription;
        OnCategoryItemClickListener onCategoryItemClickListener;

        public CategoryViewHolder(@NonNull View itemView, OnCategoryItemClickListener onCategoryItemClickListener) {
            super(itemView);
            this.onCategoryItemClickListener = onCategoryItemClickListener;
            catImage = (ImageView) itemView.findViewById(R.id.catImage);
            catName = (TextView) itemView.findViewById(R.id.catName);
            catDescription = (TextView) itemView.findViewById(R.id.catDescription);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCategoryItemClickListener.onItemClick(categories.get(getAdapterPosition()));
        }
    }

}
