package com.cibertec.dawi.utils;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;

import com.cibertec.dawi.models.Registro;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class RegistroSoloImpactoYFechaSerializer implements JsonSerializer<List<Registro>>{

	@Override
	public JsonElement serialize(List<Registro> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray array = new JsonArray();
        for (Registro registro : src) {
            JsonObject object = new JsonObject();
            object.addProperty("impacto", registro.getImpacto());
            object.addProperty("fecha", new SimpleDateFormat("dd-MM-yyyy").format(registro.getFecha()));
            array.add(object);
        }
        return array;
    }
	

}
