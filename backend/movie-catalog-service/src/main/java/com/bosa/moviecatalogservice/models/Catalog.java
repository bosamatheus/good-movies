package com.bosa.moviecatalogservice.models;

import java.util.ArrayList;
import java.util.List;

public class Catalog {

    private List<CatalogItem> items;

    public Catalog() {
        this.items = new ArrayList<>();
    }

    public List<CatalogItem> getItems() {
        return items;
    }

    public void setItems(List<CatalogItem> items) {
        this.items = items;
    }
}
