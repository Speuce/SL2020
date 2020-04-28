/*
 * Copyright (c) Zachery Unrau
 */

package main.java.lucia.client.content.menu.legacy.impl;

import main.java.lucia.client.content.menu.legacy.Item;

public class Misc extends Item {

    private int rowNum = -1;

    public Misc(String name, long price) {
        super(name);
        super.setPrice(price);
    }

    @Override
    public Item safeClone() {
        return new Misc(name, price);
    }

    @Override
    @Deprecated
    public void generateName() {

    }

    @Override
    @Deprecated
    public void calculatePrice() {
    }

    public long getPrice() {
        return price;
    }
}
