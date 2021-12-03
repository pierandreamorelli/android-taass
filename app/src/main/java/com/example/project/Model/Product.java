package com.example.project.Model;

import java.io.Serializable;

public class Product implements Serializable {

    private Long id;
    private String name;
    private ProductCategories category;
    private Double price;
    private String pictureUrl;
    private String description;


    public Product(Long id, String name, Double price){
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(Long id, String name, Double price, ProductCategories category, String pictureUrl, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.category = category;
        this.description = description;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public ProductCategories getCategory() { return category;}

    public void setCategory(ProductCategories category) {this.category = category;}

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public static ProductCategories stringToCategory(String category){
        switch (category){

            case "Component":
                return ProductCategories.Component;
            case "Laptop":
                return ProductCategories.Laptop;
            case "Accessories":
                return ProductCategories.Accessories;
            default:
                return null;

        }

    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
