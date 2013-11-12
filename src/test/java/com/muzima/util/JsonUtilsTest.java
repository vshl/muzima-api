package com.muzima.util;

import net.minidev.json.JSONObject;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class JsonUtilsTest {
    @Test
    public void shouldWriteAsDate() throws Exception {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("1956-12-11");

        JSONObject jsonObject = new JSONObject();

        JsonUtils.writeAsDate(jsonObject, "birthdate", date);
        assertThat((String) jsonObject.get("birthdate"), is("1956-12-11"));
    }

    @Test
    public void shouldWriteAsDateWriteNullAsNull() throws Exception {
        JSONObject jsonObject = new JSONObject();

        JsonUtils.writeAsDate(jsonObject, "birthdate", null);
        assertThat((String) jsonObject.get("birthdate"), nullValue());
    }

    @Test
    public void shouldReadAsDate() throws Exception {
        Date expected = new SimpleDateFormat("yyyy-MM-dd").parse("1956-12-11");

        String serialized = "{birthdate:\"1956-12-11\"}";
        Date actualDate = JsonUtils.readAsDate(serialized, "$['birthdate']");
        assertThat(actualDate, is(expected));
    }

    @Test
    public void shouldReadAsDateReturnNullIfThePathDoesNotExist() throws Exception {
        String serialized = "{}";
        Date actualDate = JsonUtils.readAsDate(serialized, "$['birthdate']");
        assertThat(actualDate, nullValue());
    }
}
