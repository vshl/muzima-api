package com.muzima.api.model.algorithm;

import com.jayway.jsonpath.JsonPath;
import com.muzima.api.model.Form;
import com.muzima.api.model.Tag;
import com.muzima.search.api.util.StreamUtil;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FormAlgorithmTest {
    private static final String JSON_DIRECTORY = "./";

    private FormAlgorithm formAlgorithm;
    private String jsonForm;

    @Before
    public void setup() throws IOException {
        readJsonFromFile();
        formAlgorithm = new FormAlgorithm();
    }

    private void readJsonFromFile() throws IOException {
        URL jsonUri = FormAlgorithm.class.getResource(JSON_DIRECTORY);
        File jsonDirectory = new File(jsonUri.getPath());
        jsonForm = StreamUtil.readAsString(new FileReader(new File(jsonDirectory, "form.json")));
    }

    @Test
    public void deserialize_shouldReadNameFromJson() throws IOException {
        Form form = (Form) formAlgorithm.deserialize(jsonForm);
        assertThat(form.getName(), is("PMTCT Form"));
    }

    @Test
    public void deserialize_shouldReadDescriptionFromJson() throws IOException {
        Form form = (Form) formAlgorithm.deserialize(jsonForm);
        assertThat(form.getDescription(), is("Form for PMTCT registration"));
    }

    @Test
    public void deserialize_shouldReadTagsFromJson() throws IOException {
        Form form = (Form) formAlgorithm.deserialize(jsonForm);
        assertThat(form.getTags().length, is(3));

        assertThat(form.getTags()[0].getName(), is("visit"));
        assertThat(form.getTags()[0].getUuid(), is("a8ff6199-968b-4388-a894-987d0e8b03d6"));

        assertThat(form.getTags()[1].getName(), is("Form"));
        assertThat(form.getTags()[1].getUuid(), is("e448fda3-b478-4d08-8c49-355c09bad1d1"));

        assertThat(form.getTags()[2].getName(), is("PMTCT"));
        assertThat(form.getTags()[2].getUuid(), is("d07d84ca-ec2e-4c11-b392-02aa408c6004"));
    }

    @Test
    public void serialize_shouldSerializeNameToJson() throws IOException {
        Form form = buildForm();

        String formJson = formAlgorithm.serialize(form);

        Object jsonObject = JsonPath.read(formJson, "$");
        assertThat((String) JsonPath.read(jsonObject, "name"), is("PMTCT"));
    }

    @Test
    public void serialize_shouldSerializeUUIDToJson() throws IOException {
        Form form = buildForm();

        String formJson = formAlgorithm.serialize(form);

        Object jsonObject = JsonPath.read(formJson, "$");
        assertThat((String) JsonPath.read(jsonObject, "uuid"), is("uuid"));
    }

    @Test
    public void serialize_shouldSerializeDescriptionToJson() throws IOException {
        Form form = buildForm();

        String formJson = formAlgorithm.serialize(form);

        Object jsonObject = JsonPath.read(formJson, "$");
        assertThat((String) JsonPath.read(jsonObject, "description"), is("Form Description"));
    }

    @Test
    public void serialize_shouldSerializeVersionToJson() throws IOException {
        Form form = buildForm();

        String formJson = formAlgorithm.serialize(form);

        Object jsonObject = JsonPath.read(formJson, "$");
        assertThat((String) JsonPath.read(jsonObject, "version"), is("1.0"));
    }

    @Test
    public void serialize_shouldSerializeTagsToJson() throws IOException {
        Form form = buildForm();

        String formJson = formAlgorithm.serialize(form);

        Object jsonObject = JsonPath.read(formJson, "$");
        Tag[] tags = readTags(jsonObject);

        assertThat(tags.length, is(2));

        assertThat(tags[0].getName(), is("Tag 1"));
        assertThat(tags[0].getUuid(), is("tag1_uuid"));

        assertThat(tags[1].getName(), is("Tag 2"));
        assertThat(tags[1].getUuid(), is("tag2_uuid"));
    }

    private Tag[] readTags(Object jsonObject) {
        JSONArray tagsJson = JsonPath.read(jsonObject, "$['tags']");
        Tag[] tags = new Tag[tagsJson.size()];
        for(int i=0; i< tagsJson.size(); i++){
            Tag tag = new Tag();
            JSONObject tagJson = (JSONObject) tagsJson.get(i);
            tag.setName((String) tagJson.get("name"));
            tag.setUuid((String) tagJson.get("uuid"));
            tags[i] = tag;
        }
        return tags;
    }

    private Form buildForm() {
        Form form = new Form();
        Tag tag1 = new Tag() {{
            setUuid("tag1_uuid");
            setName("Tag 1");
        }};
        Tag tag2 = new Tag() {{
            setUuid("tag2_uuid");
            setName("Tag 2");
        }};
        Tag[] tags = {tag1,
                tag2};

        form.setName("PMTCT");
        form.setUuid("uuid");
        form.setDescription("Form Description");
        form.setVersion("1.0");
        form.setTags(tags);
        return form;
    }


}
