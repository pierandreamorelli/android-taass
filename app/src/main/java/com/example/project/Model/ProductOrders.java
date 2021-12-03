package com.example.project.Model;

import java.util.ArrayList;

public class ProductOrders {
    ArrayList<ProductOrder> productOrders;

    public ProductOrders(ArrayList<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }

    public ArrayList<ProductOrder> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(ArrayList<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }

}
