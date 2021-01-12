package com.webtutsplus.ecommerceapp.Activity.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.webtutsplus.ecommerceapp.Activity.Category.ListCategoriesActivity;
import com.webtutsplus.ecommerceapp.Activity.Category.UpdateCategoryActivity;
import com.webtutsplus.ecommerceapp.Activity.MainActivity;
import com.webtutsplus.ecommerceapp.Activity.Product.ListProductsActivity;
import com.webtutsplus.ecommerceapp.Activity.Product.UpdateProductActivity;
import com.webtutsplus.ecommerceapp.Adapter.CategoryAdapter;
import com.webtutsplus.ecommerceapp.Adapter.HomeCategoryAdapter;
import com.webtutsplus.ecommerceapp.Adapter.HomeProductAdapter;
import com.webtutsplus.ecommerceapp.Adapter.ProductAdapter;
import com.webtutsplus.ecommerceapp.Model.Category;
import com.webtutsplus.ecommerceapp.Model.Product;
import com.webtutsplus.ecommerceapp.Network.API;
import com.webtutsplus.ecommerceapp.Network.RetrofitClient;
import com.webtutsplus.ecommerceapp.R;
import com.webtutsplus.ecommerceapp.Utility.OnCategoryItemClickListener;
import com.webtutsplus.ecommerceapp.Utility.OnItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements OnCategoryItemClickListener , OnItemClickListener {

    Button startShoppingButton;

    private RecyclerView revCategories;
    private RecyclerView revProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        startShoppingButton = findViewById(R.id.start_shopping_btn);

        startShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ListCategoriesActivity.class));
            }
        });


        API api = RetrofitClient.getInstance().getAPI();
        Call<List<Category>> call = api.getCategory();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, response.code() + "", Toast.LENGTH_LONG).show();
                    return;
                }

                List<Category> categories = response.body();
                revCategories = findViewById(R.id.revCategories);
                revCategories.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL,false));
                revCategories.setAdapter(new HomeCategoryAdapter(HomeActivity.this, categories, HomeActivity.this));



            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage() + "", Toast.LENGTH_LONG).show();
            }
        });



        Call<List<Product>> call2 = api.getProducts();

        call2.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, response.code() + "", Toast.LENGTH_LONG).show();
                    return;
                }

                List<Product> products = response.body();
                revProducts = findViewById(R.id.revProducts);
                revProducts.setLayoutManager(new LinearLayoutManager(HomeActivity.this,LinearLayoutManager.HORIZONTAL,false));
                revProducts.setAdapter(new HomeProductAdapter(HomeActivity.this, products, HomeActivity.this));
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage() + "", Toast.LENGTH_LONG).show();
            }
        });


    }
    @Override
    public void onItemClick(Category c) {
        Intent updateCategory = new Intent(getApplicationContext(), UpdateCategoryActivity.class);
        updateCategory.putExtra("id",c.getId());
        updateCategory.putExtra("name",c.getCategoryName());
        updateCategory.putExtra("desc",c.getDescription());
        updateCategory.putExtra("imageUrl",c.getImageUrl());
        startActivity(updateCategory);
    }

    @Override
    public void onItemClick(Product p) {
        Intent updateProduct = new Intent(getApplicationContext(), UpdateProductActivity.class);
        updateProduct.putExtra("id",p.getId());
        updateProduct.putExtra("categoryId",p.getCategoryId());
        updateProduct.putExtra("name",p.getName());
        updateProduct.putExtra("desc",p.getDescription());
        updateProduct.putExtra("imageUrl",p.getImageURL());
        updateProduct.putExtra("price",p.getPrice());
        startActivity(updateProduct);
    }
}