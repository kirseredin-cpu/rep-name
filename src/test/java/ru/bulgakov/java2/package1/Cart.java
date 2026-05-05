package ru.bulgakov.java2.package1;


import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<String> products = new ArrayList<>();
    private int totalPrice;



    public int getTotalPrice() {
        return totalPrice;
    }
}
