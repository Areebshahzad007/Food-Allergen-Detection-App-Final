package com.example.foodallergendetection;
public class Product {
    private String product_name;
    private String ingredients_text;

    public String getProductName() {
        return product_name;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    public String getIngredientsText() {
        return ingredients_text;
    }

    public void setIngredientsText(String ingredients_text) {
        this.ingredients_text = ingredients_text;
    }

    @Override
    public String toString() {
        return "Product{name='" + product_name + "', ingredients='" + ingredients_text + "'}";
    }
}
