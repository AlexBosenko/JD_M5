package org.example.services;

public class ProjectPrices {
    private final String name;
    private final long price;

    public ProjectPrices(String name, long price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", price=" + price;
    }
}
