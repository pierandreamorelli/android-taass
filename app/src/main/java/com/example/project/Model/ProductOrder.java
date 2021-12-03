package com.example.project.Model;

public class ProductOrder {

    Product product;
    int quantity;

    public ProductOrder(Product product, int num) {
        this.product = product;
        this.quantity = num;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNum() {
        return quantity;
    }

    public void setNum(int num) {
        this.quantity = num;
    }
}
