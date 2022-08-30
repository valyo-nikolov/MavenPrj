package org.example.twelve;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

class ObjectToJson {
    public static void main(String[] a) {
        // Creating object of Product
        Product product = new Product();
        // Inserting the data into the object
        product = getObjectData(product);
        // Creating Object of ObjectMapper define in Jackson API
        ObjectMapper obj = new ObjectMapper();
        try {
            // Converting the Java object into a JSON string
            String jsonStr = obj.writeValueAsString(product);
            // Displaying Java object into a JSON string
            System.out.println(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject animal = new JSONObject();
        JSONArray legs = new JSONArray();
        animal.put("name", "Krokodil");
        animal.put("opashka", 56);
        JSONObject something = new JSONObject();
        something.put(1, 167);
        something.put(2, 87);
        something.put(3, "Tail");
        animal.put("something", something);
        animal.put("legs", legs);

        System.out.println("ANIMAL JSON object: " + animal);
        System.out.println("ANIMAL JSON object: " + animal.get("name"));
        System.out.println("ANIMAL JSON object: " + animal.get("opashka"));
        System.out.println("ANIMAL JSON object: " + animal.get("something"));
//        JSONArray array = (JSONArray) animal.get("something");
        JSONObject array = (JSONObject) animal.get("something");
        System.out.println("ANIMAL JSON object: " + array.get(2));
    }

    // Getting data that we want to insert into an object
    public static Product getObjectData(Product product) {
        // Insert the data
        product.setId(101);
        product.setName("Spark 131");
        product.setPrice(10000);
        // Returning the product object
        return product;
    }

}

class Product {
    private int id;
    private String name;
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}