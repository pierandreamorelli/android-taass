package com.example.project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Adapter.CategoryAdapter;
import com.example.project.Adapter.PopularAdapter;
import com.example.project.Model.CategoryDomain;
import com.example.project.Model.Product;
import com.example.project.Model.ProductOrder;
import com.example.project.R;
import com.example.project.Utilities.FetchDataTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "con";
    private RecyclerView.Adapter adapter ;
    private PopularAdapter adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;
    private String url = "http://192.168.1.140:8080/api/products";
    ArrayList<ProductOrder> productList = new ArrayList<ProductOrder>();
    ArrayList<ProductOrder> nuovalista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new FetchDataTask(this).execute(url,"GET");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCategory();


        bottomNavigation();
    }


    @Override
    protected void onStart() {
        super.onStart();
        RangeSlider rangeSlider = findViewById(R.id.slider);
        SearchView search = findViewById(R.id.search);


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        rangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {

            }
        });

        rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                List<Float> values = rangeSlider.getValues();
                String min =  values.get(0).toString();
                String max = values.get(1).toString();
                new FetchDataTask(MainActivity.this).execute("http://192.168.1.140:8080/api/products/"+ min + "/-/" + max,"GET");


            }

        });

        rangeSlider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                return "$ " + value;
            }
        });

    }

    private void filter(String newText) {
        nuovalista.clear();
        for (ProductOrder po : productList) {
            if(po.getProduct().getName().toLowerCase().contains(newText.toLowerCase())){
                nuovalista.add(po);

            }
            recyclerViewPopular(nuovalista);
        }
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.card_btn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout prof = findViewById(R.id.profile);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.setClassName(MainActivity.this,"com.example.project.Activity.LoginActivity");
                startActivity(intent);
            }
        });
    }

    private void recyclerViewPopular(ArrayList<ProductOrder> products) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);


        adapter2 = new PopularAdapter(products);
        recyclerViewPopularList.setAdapter(adapter2);

    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> categoryList = new ArrayList<>();
        categoryList.add(new CategoryDomain("All", "cat_11"));
        categoryList.add(new CategoryDomain("Laptops", "cat_22"));
        categoryList.add(new CategoryDomain("Components", "cat_33"));
        categoryList.add(new CategoryDomain("Accessories", "cat_44"));



        adapter = new CategoryAdapter(categoryList, this);
        recyclerViewCategoryList.setAdapter(adapter);
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void parseJSON(String data){

        this.productList.clear();
        recyclerViewPopular(productList);

        try{
            JSONArray jsonMainNode = new JSONArray(data);


            for(int i=0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                Long id = jsonChildNode.getLong("id");
                String category = jsonChildNode.getString("category");
                String name = jsonChildNode.getString("name");
                String picture_url = jsonChildNode.getString("pictureUrl");
                Double price = jsonChildNode.getDouble("price");
                String description = jsonChildNode.getString("description");
                Product p = new Product(id, name, price, Product.stringToCategory(category), picture_url,description);
                ProductOrder po = new ProductOrder(p,0);
                this.productList.add(po);
            }

            recyclerViewPopular(productList);

        }catch(Exception e){
            Log.i("App", "Error parsing data" +e.getMessage());

        }
    }

}

