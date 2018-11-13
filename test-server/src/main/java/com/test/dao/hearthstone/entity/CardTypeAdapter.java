package com.test.dao.hearthstone.entity;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Arrays;

public class CardTypeAdapter extends TypeAdapter<Card> {

    @Override
    public void write(JsonWriter out, Card value) throws IOException {
        Gson gson = new Gson();
        out.jsonValue(gson.toJson(value));
    }

    @Override
    public Card read(JsonReader in) throws IOException {
        Arrays.stream(Card.class.getFields()).forEach(field -> {
        });
        return null;
    }
}
