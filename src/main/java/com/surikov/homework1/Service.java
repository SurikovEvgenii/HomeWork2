package com.surikov.homework1;

public class Service {

    public static void main(String[] args) {
        Service service = new Service();
        service.info();
    }

    public void info(){
        System.out.println(this.getClass().getCanonicalName().toUpperCase());
    }
}
