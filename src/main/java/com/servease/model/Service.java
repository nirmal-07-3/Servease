package com.servease.model;

public class Service {

    private int id;
    private int provider_id;
    private String name;
    private String description;
    private double price;

    public Service() {
    }

    // 🔥 Constructor (FOR ADD)
    public Service(int provider_id, String name, String description, double price) {
        this.provider_id = provider_id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // 🔥 Constructor (FOR UPDATE)
    public Service(int id, int provider_id, String name, String description, double price) {
        this.id = id;
        this.provider_id = provider_id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // GETTERS
    public int getId() { return id; }
    public int getProvider_id() { return provider_id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }

    // SETTERS
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }

    public void setId(int id) {
        this.id = id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }
}