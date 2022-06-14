package com.example.domain;

import lombok.Data;

import java.util.List;


@Data
public class PageResult {
    private int pagenum;
    private int pagesize;
    private long total;
    private List<Store> storerows;
    private List<OutOrder> outOrderrows;
    private List<InOrder> inOrderrows;
    private List<User> userrows;
    private List<Product> productrows;
    public Store store;
    public InOrder inOrder;
    public OutOrder outOrder;
    public User user;
    public Product product;

}
