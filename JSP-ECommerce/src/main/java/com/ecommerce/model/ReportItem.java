package com.ecommerce.model;

// A simple POJO to hold data for the top-selling products report
public class ReportItem {
    private Product product;
    private int totalQuantitySold;

    public ReportItem(Product product, int totalQuantitySold) {
        this.product = product;
        this.totalQuantitySold = totalQuantitySold;
    }

    // Getters and Setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getTotalQuantitySold() {
        return totalQuantitySold;
    }

    public void setTotalQuantitySold(int totalQuantitySold) {
        this.totalQuantitySold = totalQuantitySold;
    }
}