package co.pricify.android.pricify.models;

import java.math.BigDecimal;
import java.net.URL;

public class Product {
    private String name;
    private BigDecimal currentPrice;
    private String companyUrl;
    private String imageUrl;

    public Product(String name, BigDecimal currentPrice, String companyUrl, String imageUrl) {
        this.name = name;
        this.currentPrice = currentPrice;
        this.companyUrl = companyUrl;
        this.imageUrl = imageUrl;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public BigDecimal getCurrentPrice() { return currentPrice; }

    public void setCurrentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; }

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
