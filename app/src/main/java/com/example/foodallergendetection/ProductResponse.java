package com.example.foodallergendetection;
public class ProductResponse {
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductResponse{product=" + product.toString() + "}";
    }
}

