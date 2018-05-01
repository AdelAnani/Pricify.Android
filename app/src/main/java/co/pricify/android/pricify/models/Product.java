package co.pricify.android.pricify.models;

import java.math.BigDecimal;
import java.net.URL;

public class Product {
    private String name;
    private BigDecimal currentPrice;
    private BigDecimal highestPrice;
    private BigDecimal lowestPrice;
    private String companyUrl;
    private String imageUrl;

    public Product(String name, BigDecimal currentPrice, BigDecimal highestPrice, BigDecimal lowestPrice, String companyUrl, String imageUrl) {
        this.name = name;
        this.currentPrice = currentPrice;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.companyUrl = companyUrl;
        this.imageUrl = imageUrl;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public BigDecimal getCurrentPrice() { return currentPrice; }

    public void setCurrentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; }

    public BigDecimal getHighestPrice() { return highestPrice; }

    public void setHighestPrice(BigDecimal highestPrice) { this.highestPrice = highestPrice; }

    public BigDecimal getLowestPrice() { return lowestPrice; }

    public void setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
