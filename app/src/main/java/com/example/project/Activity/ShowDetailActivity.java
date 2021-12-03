package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project.Model.Product;
import com.example.project.Model.ProductOrder;
import com.example.project.Utilities.ManagementCart;
import com.example.project.R;

public class ShowDetailActivity extends AppCompatActivity {
    private TextView addToCardBtn;
    private TextView titleTxt, feeTxt, descriptionTxt, numberOrderTxt;
    private ImageView plusBtn, minusBtn, pic;
    private Product object1;
    private int numberOrder = 1;
    private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        managementCart = new ManagementCart(this);

        initView();
        getBundle();
    }

    private void getBundle() {
      object1 = (Product) getIntent().getSerializableExtra("object");
      ProductOrder object = new ProductOrder(object1, 0);

        int drawableResourceId = this.getResources().getIdentifier(object.getProduct().getPictureUrl(), "drawable", this.getPackageName());
        String picUrl = object.getProduct().getPictureUrl();

        Glide.with(this)
                .load(picUrl)
                .into(pic);

        titleTxt.setText(object.getProduct().getName());
        feeTxt.setText("$" + object.getProduct().getPrice());
        descriptionTxt.setText(object.getProduct().getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder = numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        addToCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setNum(numberOrder);
                managementCart.insertProductOrder(object);
            }
        });
    }

    private void initView() {
        addToCardBtn = findViewById(R.id.addToCardBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        pic = findViewById(R.id.foodPic);
    }
}