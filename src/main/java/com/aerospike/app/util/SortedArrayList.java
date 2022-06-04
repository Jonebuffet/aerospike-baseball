package com.aerospike.app.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortedArrayList<T> extends ArrayList<T> implements List<T> {

    @SuppressWarnings("unchecked")
    public void insertSorted(T value) {
    	System.out.println("#################################################");
    	System.out.println("############# YOU ARE SORTING ###################");
    	System.out.println("#################################################");
        add(value);
        Comparable<T> cmp = (Comparable<T>) value;
        for (int i = size()-1; i > 0 && cmp.compareTo(get(i-1)) < 0; i--)
            Collections.swap(this, i, i-1);
    }
}