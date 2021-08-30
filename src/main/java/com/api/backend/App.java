package com.api.backend;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("#No.1 Sports App");
        ApiCalls api = new ApiCalls();
        api.fetchLeagues();
    }
}
