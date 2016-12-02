/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.service;

import com.muzima.api.context.Context;
import com.muzima.api.context.ContextFactory;
import com.muzima.api.model.Form;
import com.muzima.api.model.FormData;
import com.muzima.api.model.FormTemplate;
import com.muzima.api.model.Tag;
import com.muzima.search.api.util.StringUtil;
import com.muzima.util.Constants;
import net.minidev.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;

/**
 * TODO: Write brief description about the class here.
 */
public class FormServiceTest {
    // baseline form
    private Form form;
    private List<Form> forms;

    private Context context;
    private FormService formService;

    private static int nextInt(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }

    @Before
    public void prepare() throws Exception {
        String path = System.getProperty("java.io.tmpdir") + "/muzima/" + UUID.randomUUID().toString();
        ContextFactory.setProperty(Constants.LUCENE_DIRECTORY_PATH, path);
        context = ContextFactory.createContext();
        context.setPreferredLocale("en");
        context.openSession();
        if (!context.isAuthenticated()) {
            context.authenticate("admin", "test", "http://demo2.muzima.org", false);
        }
        formService = context.getFormService();
        forms = formService.downloadFormsByName(StringUtil.EMPTY);
        form = forms.get(nextInt(forms.size()));
    }

    @After
    public void cleanUp() throws Exception {
        String lucenePath = ContextFactory.getProperty(Constants.LUCENE_DIRECTORY_PATH);
        File luceneDirectory = new File(lucenePath);
        for (String filename : luceneDirectory.list()) {
            File file = new File(luceneDirectory, filename);
            Assert.assertTrue(file.delete());
        }
        context.deauthenticate();
        context.closeSession();
    }

    /**
     * @verifies download form with matching uuid.
     * @see FormService#downloadFormByUuid(String)
     */
    @Test
    public void downloadFormByUuid_shouldDownloadFormWithMatchingUuid() throws Exception {
        Form downloadedForm = formService.downloadFormByUuid(form.getUuid());
        assertThat(downloadedForm, samePropertyValuesAs(form));
    }

    /**
     * @verifies download all form with partially matched name.
     * @see FormService#downloadFormsByName(String)
     */
    @Test
    public void downloadFormsByName_shouldDownloadAllFormWithPartiallyMatchedName() throws Exception {
        String name = form.getName();
        String partialName = name.substring(name.length() - 1);
        List<Form> downloadedForms = formService.downloadFormsByName(partialName);
        for (Form downloadedForm : downloadedForms) {
            assertThat(downloadedForm.getName(), containsString(partialName));
        }
    }

    /**
     * @verifies download all form when name is empty.
     * @see FormService#downloadFormsByName(String)
     */
    @Test
    public void downloadFormsByName_shouldDownloadAllFormWhenNameIsEmpty() throws Exception {
        List<Form> downloadedForms = formService.downloadFormsByName(StringUtil.EMPTY);
        assertThat(downloadedForms, hasSize(forms.size()));
    }

    /**
     * @verifies save form to local data repository.
     * @see FormService#saveForm(com.muzima.api.model.Form)
     */
    @Test
    public void saveForm_shouldSaveFormToLocalDataRepository() throws Exception {
        int formCounter = formService.countAllForms();
        formService.saveForm(form);
        assertThat(formCounter + 1, equalTo(formService.countAllForms()));
        formService.saveForm(form);
        assertThat(formCounter + 1, equalTo(formService.countAllForms()));
    }

    /**
     * @verifies save list of forms to local data repository.
     * @see FormService#saveForms(java.util.List)
     */
    @Test
    public void saveForms_shouldSaveListOfFormsToLocalDataRepository() throws Exception {
        int formCounter = formService.countAllForms();
        formService.saveForms(forms);
        assertThat(formCounter + forms.size(), equalTo(formService.countAllForms()));
        formService.saveForm(form);
        assertThat(formCounter + forms.size(), equalTo(formService.countAllForms()));
    }

    /**
     * @verifies replace existing form in local data repository.
     * @see FormService#updateForm(com.muzima.api.model.Form)
     */
    @Test
    public void updateForm_shouldReplaceExistingFormInLocalDataRepository() throws Exception {
        Form nullForm = formService.getFormByUuid(form.getUuid());
        assertThat(nullForm, nullValue());
        formService.saveForm(form);
        Form savedForm = formService.getFormByUuid(form.getUuid());
        assertThat(savedForm, notNullValue());
        assertThat(savedForm, samePropertyValuesAs(form));

        String formName = "New Form Name";
        savedForm.setName(formName);
        formService.updateForm(savedForm);
        Form updatedForm = formService.getFormByUuid(savedForm.getUuid());
        assertThat(updatedForm, not(samePropertyValuesAs(form)));
        assertThat(updatedForm.getName(), equalTo(formName));
    }

    /**
     * @verifies replace list of forms in local data repository.
     * @see FormService#updateForms(java.util.List)
     */
    @Test
    public void updateForms_shouldReplaceListOfFormsInLocalDataRepository() throws Exception {
        int counter = 0;
        formService.saveForms(forms);
        List<Form> savedForms = formService.getAllForms();
        for (Form form : savedForms) {
            form.setName("New Name For Form: " + counter++);
        }
        formService.updateForms(savedForms);
        List<Form> updatedForms = formService.getAllForms();
        for (Form updatedForm : updatedForms) {
            for (Form form : forms) {
                assertThat(updatedForm, not(samePropertyValuesAs(form)));
            }
            assertThat(updatedForm.getName(), containsString("New Name For Form"));
        }
    }

    /**
     * @verifies return form with matching uuid.
     * @see FormService#getFormByUuid(String)
     */
    @Test
    public void getFormByUuid_shouldReturnFormWithMatchingUuid() throws Exception {
        Form nullForm = formService.getFormByUuid(form.getUuid());
        assertThat(nullForm, nullValue());
        formService.saveForm(form);
        Form savedForm = formService.getFormByUuid(form.getUuid());
        assertThat(savedForm, notNullValue());
        assertThat(savedForm, samePropertyValuesAs(form));
    }

    /**
     * @verifies return null when no form match the uuid.
     * @see FormService#getFormByUuid(String)
     */
    @Test
    public void getFormByUuid_shouldReturnNullWhenNoFormMatchTheUuid() throws Exception {
        Form nullForm = formService.getFormByUuid(form.getUuid());
        assertThat(nullForm, nullValue());
        formService.saveForm(form);
        String randomUuid = UUID.randomUUID().toString();
        Form savedForm = formService.getFormByUuid(randomUuid);
        assertThat(savedForm, nullValue());
    }

    /**
     * @verifies count all forms with matching name.
     * @see FormService#countFormByName(String)
     */
    @Test
    public void countFormByName_shouldCountAllFormsWithMatchingName() throws Exception {
        assertThat(0, equalTo(formService.countAllForms()));
        formService.saveForm(form);
        assertThat(1, equalTo(formService.countFormByName(form.getName())));
        formService.saveForm(form);
        assertThat(1, equalTo(formService.countFormByName(form.getName())));
    }

    /**
     * @verifies return form with matching name.
     * @see FormService#getFormByName(String)
     */
    @Test
    public void getFormByName_shouldReturnFormWithMatchingName() throws Exception {
        assertThat(0, equalTo(formService.countAllForms()));
        formService.saveForm(form);
        List<Form> savedStaticForms = formService.getFormByName(form.getName());
        assertThat(1, equalTo(savedStaticForms.size()));
    }

    /**
     * @verifies return null when no form match the name.
     * @see FormService#getFormByName(String)
     */
    @Test
    public void getFormByName_shouldReturnNullWhenNoFormMatchTheName() throws Exception {
        String randomName = UUID.randomUUID().toString();
        formService.saveForm(form);
        List<Form> savedStaticForms = formService.getFormByName(randomName);
        assertThat(0, equalTo(savedStaticForms.size()));
        assertThat(form, not(isIn(savedStaticForms)));
    }

    /**
     * @verifies return number of all forms in local data repository.
     * @see FormService#countAllForms()
     */
    @Test
    public void countAllForms_shouldReturnNumberOfAllFormsInLocalDataRepository() throws Exception {
        assertThat(0, equalTo(formService.countAllForms()));
        formService.saveForms(forms);
        assertThat(forms.size(), equalTo(formService.countAllForms()));
    }

    /**
     * @verifies return all registered forms.
     * @see FormService#getAllForms()
     */
    @Test
    public void getAllForms_shouldReturnAllRegisteredForms() throws Exception {
        formService.saveForms(forms);
        List<Form> savedForms = formService.getAllForms();
        assertThat(savedForms, hasSize(forms.size()));
    }

    /**
     * @verifies return empty list when no form is registered.
     * @see FormService#getAllForms()
     */
    @Test
    public void getAllForms_shouldReturnEmptyListWhenNoFormIsRegistered() throws Exception {
        assertThat(formService.getAllForms(), hasSize(0));
    }

    /**
     * @verifies delete form from local data repository.
     * @see FormService#deleteForm(com.muzima.api.model.Form)
     */
//    @Test
    public void deleteForm_shouldDeleteFormFromLocalDataRepository() throws Exception {
        assertThat(formService.getAllForms(), hasSize(0));
        formService.saveForms(forms);
        int formCount = forms.size();
        assertThat(formService.getAllForms(), hasSize(formCount));
        for (Form form : forms) {
            formService.deleteForm(form);
            assertThat(formService.getAllForms(), hasSize(--formCount));
        }
    }

    /**
     * @verifies delete list of forms from local data repository.
     * @see FormService#deleteForms(java.util.List)
     */
//    @Test
    public void deleteForms_shouldDeleteListOfFormsFromLocalDataRepository() throws Exception {
        assertThat(formService.getAllForms(), hasSize(0));
        formService.saveForms(forms);
        List<Form> savedForms = formService.getAllForms();
        assertThat(savedForms, hasSize(forms.size()));
        formService.deleteForms(savedForms);
        assertThat(formService.getAllForms(), hasSize(0));
    }

    /**
     * @verifies return true if the template of a form is downloaded.
     * @see FormService#isFormTemplateDownloaded(String)
     */
    @Test
    public void isFormTemplateDownloaded_shouldReturnTrueIfTheTemplateOfAFormIsDownloaded() throws Exception {
        assertThat(formService.getAllFormTemplates(), hasSize(0));
        FormTemplate formTemplate = formService.downloadFormTemplateByUuid(form.getUuid());
        formService.saveFormTemplate(formTemplate);
        assertThat(formService.getAllFormTemplates(), hasSize(1));
        assertThat(formService.isFormTemplateDownloaded(form.getUuid()), equalTo(true));
    }

    /**
     * @verifies download the form template by the uuid of the form.
     * @see FormService#downloadFormTemplateByUuid(String)
     */
    @Test
    public void downloadFormTemplateByUuid_shouldDownloadTheFormTemplateByTheUuidOfTheForm() throws Exception {
        assertThat(formService.getAllFormTemplates(), hasSize(0));
        FormTemplate formTemplate = formService.downloadFormTemplateByUuid(form.getUuid());
        formService.saveFormTemplate(formTemplate);
        assertThat(formService.getAllFormTemplates(), hasSize(1));
        assertThat(formService.isFormTemplateDownloaded(form.getUuid()), equalTo(true));
    }

    /**
     * @verifies download the form template by the name of the form.
     * @see FormService#downloadFormTemplatesByName(String)
     */
    @Test
    public void downloadFormTemplatesByName_shouldDownloadTheFormTemplateByTheNameOfTheForm() throws Exception {
        assertThat(formService.getAllFormTemplates(), hasSize(0));
        List<FormTemplate> formTemplates = formService.downloadFormTemplatesByName(form.getName());
        formService.saveFormTemplates(formTemplates);
        assertThat(formService.getAllFormTemplates(), hasSize(formTemplates.size()));
    }

    /**
     * @verifies save the form template to the local data repository.
     * @see FormService#saveFormTemplate(com.muzima.api.model.FormTemplate)
     */
    @Test
    public void saveFormTemplate_shouldSaveTheFormTemplateToTheLocalDataRepository() throws Exception {
        assertThat(formService.getAllFormTemplates(), hasSize(0));
        FormTemplate formTemplate = formService.downloadFormTemplateByUuid(form.getUuid());
        formService.saveFormTemplate(formTemplate);
        assertThat(formService.getAllFormTemplates(), hasSize(1));
        assertThat(formService.isFormTemplateDownloaded(form.getUuid()), equalTo(true));
    }

    /**
     * @verifies save the list of form templates to local data repository.
     * @see FormService#saveFormTemplates(java.util.List)
     */
    @Test
    public void saveFormTemplates_shouldSaveTheListOfFormTemplatesToLocalDataRepository() throws Exception {
        assertThat(formService.getAllFormTemplates(), hasSize(0));
        List<FormTemplate> formTemplates = formService.downloadFormTemplatesByName(form.getName());
        formService.saveFormTemplates(formTemplates);
        assertThat(formService.getAllFormTemplates(), hasSize(formTemplates.size()));
    }

    /**
     * @verifies get the form template by the uuid.
     * @see FormService#getFormTemplateByUuid(String)
     */
    @Test
    public void getFormTemplateByUuid_shouldGetTheFormTemplateByTheUuid() throws Exception {
        assertThat(formService.getAllFormTemplates(), hasSize(0));
        FormTemplate formTemplate = formService.downloadFormTemplateByUuid(form.getUuid());
        formService.saveFormTemplate(formTemplate);
        assertThat(formService.getAllFormTemplates(), hasSize(1));
        assertThat(formService.isFormTemplateDownloaded(form.getUuid()), equalTo(true));
        FormTemplate savedFormTemplate = formService.getFormTemplateByUuid(formTemplate.getUuid());
        assertThat(savedFormTemplate, samePropertyValuesAs(formTemplate));
    }

    /**
     * @verifies count all form templates in local data repository.
     * @see FormService#countAllFormTemplates()
     */
    @Test
    public void countAllFormTemplates_shouldCountAllFormTemplatesInLocalDataRepository() throws Exception {
        assertThat(formService.getAllFormTemplates(), hasSize(0));
        List<FormTemplate> formTemplates = formService.downloadFormTemplatesByName(form.getName());
        formService.saveFormTemplates(formTemplates);
        assertThat(formService.getAllFormTemplates(), hasSize(formTemplates.size()));
        assertThat(formService.countAllFormTemplates(), equalTo(formTemplates.size()));
    }

    /**
     * @verifies return all saved form templates fom local data repository.
     * @see FormService#getAllFormTemplates()
     */
    @Test
    public void getAllFormTemplates_shouldReturnAllSavedFormTemplatesFomLocalDataRepository() throws Exception {
        assertThat(formService.getAllFormTemplates(), hasSize(0));
        List<FormTemplate> formTemplates = formService.downloadFormTemplatesByName(form.getName());
        formService.saveFormTemplates(formTemplates);
        assertThat(formService.getAllFormTemplates(), hasSize(formTemplates.size()));
    }

    /**
     * @verifies delete the form template from local data repository.
     * @see FormService#deleteFormTemplate(com.muzima.api.model.FormTemplate)
     */
    @Test
    public void deleteFormTemplate_shouldDeleteTheFormTemplateFromLocalDataRepository() throws Exception {
        assertThat(formService.getAllFormTemplates(), hasSize(0));
        List<FormTemplate> formTemplates = formService.downloadFormTemplatesByName(form.getName());
        formService.saveFormTemplates(formTemplates);
        List<FormTemplate> savedFormTemplates = formService.getAllFormTemplates();
        assertThat(savedFormTemplates, hasSize(formTemplates.size()));
        int formTemplateCounter = savedFormTemplates.size();
        for (FormTemplate savedFormTemplate : savedFormTemplates) {
            formService.deleteFormTemplate(savedFormTemplate);
            formTemplateCounter--;
            assertThat(formService.countAllFormTemplates(), equalTo(formTemplateCounter));
        }
        assertThat(formService.countAllFormTemplates(), equalTo(0));
    }

    /**
     * @verifies delete the list of forms from local data repository.
     * @see FormService#deleteFormTemplates(java.util.List)
     */
    @Test
    public void deleteFormTemplates_shouldDeleteTheListOfFormsFromLocalDataRepository() throws Exception {
        assertThat(formService.getAllFormTemplates(), hasSize(0));
        List<FormTemplate> formTemplates = formService.downloadFormTemplatesByName(form.getName());
        formService.saveFormTemplates(formTemplates);
        List<FormTemplate> savedFormTemplates = formService.getAllFormTemplates();
        assertThat(savedFormTemplates, hasSize(formTemplates.size()));
        assertThat(formService.countAllFormTemplates(), equalTo(formTemplates.size()));
        formService.deleteFormTemplates(savedFormTemplates);
        assertThat(formService.countAllFormTemplates(), equalTo(0));
    }

    /**
     * @verifies save form data to local data repository.
     * @see FormService#saveFormData(com.muzima.api.model.FormData)
     */
    @Test
    public void saveFormData_shouldSaveFormDataToLocalDataRepository() throws Exception {
        assertThat(formService.countAllFormData(), equalTo(0));
        FormData formData = new FormData();
        formService.saveFormData(formData);
        assertThat(formService.countAllFormData(), equalTo(1));
    }

    /**
     * @verifies return form data by the uuid.
     * @see FormService#getFormDataByUuid(String)
     */
    @Test
    public void getFormDataByUuid_shouldReturnFormDataByTheUuid() throws Exception {
        String formDataUuid = UUID.randomUUID().toString();
        assertThat(formService.countAllFormData(), equalTo(0));
        FormData formData = new FormData();
        formData.setUuid(formDataUuid);
        formService.saveFormData(formData);
        assertThat(formService.countAllFormData(), equalTo(1));
        assertThat(formService.getFormDataByUuid(formDataUuid), samePropertyValuesAs(formData));

    }

    /**
     * @verifies return form data by the uuid.
     * @see FormService#getFormDataByUuids(java.util.List>)
     */
    @Test
    public void getFormDataByUuids_shouldReturnAllFormDataWithMatchingUuids() throws Exception {
        List<String> formDataList = new ArrayList<String>();
        String userUuid = UUID.randomUUID().toString();
        assertThat(formService.countAllFormData(), equalTo(0));
        FormData firstFormData = new FormData();
        firstFormData.setUuid(UUID.randomUUID().toString());
        firstFormData.setStatus("Some random status");
        firstFormData.setUserUuid(userUuid);
        formService.saveFormData(firstFormData);
        formDataList.add(firstFormData.getUuid());

        FormData secondFormData = new FormData();
        secondFormData.setUuid(UUID.randomUUID().toString());
        secondFormData.setStatus("Some other random status");
        secondFormData.setUserUuid(userUuid);
        formService.saveFormData(secondFormData);
        formDataList.add(secondFormData.getUuid());

        assertThat(formService.getFormDataByUuids(formDataList), hasSize(2));

        FormData thirdFormData = new FormData();
        thirdFormData.setUuid(UUID.randomUUID().toString());
        thirdFormData.setStatus("Some other random status");
        thirdFormData.setUserUuid(userUuid);
        formService.saveFormData(thirdFormData);

        assertThat(formService.getFormDataByUuids(formDataList), hasSize(2));

        formDataList.add(thirdFormData.getUuid());
        assertThat(formService.getFormDataByUuids(formDataList), hasSize(3));
    }

    /**
     * @verifies count all form data in local data repository.
     * @see FormService#countAllFormData()
     */
    @Test
    public void countAllFormData_shouldCountAllFormDataInLocalDataRepository() throws Exception {
        assertThat(formService.countAllFormData(), equalTo(0));
        FormData formData = new FormData();
        formService.saveFormData(formData);
        assertThat(formService.countAllFormData(), equalTo(1));
    }

    /**
     * @verifies return all form data with matching status from local data repository.
     * @see FormService#getAllFormData(String)
     */
    @Test
    public void getAllFormData_shouldReturnAllFormDataWithMatchingStatusFromLocalDataRepository() throws Exception {
        assertThat(formService.countAllFormData(), equalTo(0));
        FormData firstFormData = new FormData();
        firstFormData.setUuid(UUID.randomUUID().toString());
        firstFormData.setStatus("Some random status");
        formService.saveFormData(firstFormData);
        FormData secondFormData = new FormData();
        secondFormData.setUuid(UUID.randomUUID().toString());
        secondFormData.setStatus("Some other random status");
        formService.saveFormData(secondFormData);
        assertThat(formService.countAllFormData(), equalTo(2));
        List<FormData> savedFormData = formService.getAllFormData("Some random status");
        assertThat(savedFormData, hasSize(1));
        for (FormData formData : savedFormData) {
            assertThat(formData.getStatus(), equalTo("Some random status"));
        }
    }

    /**
     * @verifies return all form data with matching user and status.
     * @see FormService#getFormDataByUser(String, String)
     */
    @Test
    public void getFormDataByUser_shouldReturnAllFormDataWithMatchingUserAndStatus() throws Exception {
        String userUuid = UUID.randomUUID().toString();
        assertThat(formService.countAllFormData(), equalTo(0));
        FormData firstFormData = new FormData();
        firstFormData.setUuid(UUID.randomUUID().toString());
        firstFormData.setStatus("Some random status");
        firstFormData.setUserUuid(userUuid);
        formService.saveFormData(firstFormData);
        FormData secondFormData = new FormData();
        secondFormData.setUuid(UUID.randomUUID().toString());
        secondFormData.setStatus("Some other random status");
        secondFormData.setUserUuid(userUuid);
        formService.saveFormData(secondFormData);
        assertThat(formService.countAllFormData(), equalTo(2));
        List<FormData> savedFormData = formService.getFormDataByUser(userUuid, "Some random status");
        assertThat(savedFormData, hasSize(1));
        for (FormData formData : savedFormData) {
            assertThat(formData.getStatus(), equalTo("Some random status"));
        }
    }

    /**
     * @verifies return all form data with matching patient and status.
     * @see FormService#getFormDataByPatient(String, String)
     */
    @Test
    public void getFormDataByPatient_shouldReturnAllFormDataWithMatchingPatientAndStatus() throws Exception {
        String patientUuid = UUID.randomUUID().toString();
        assertThat(formService.countAllFormData(), equalTo(0));
        FormData firstFormData = new FormData();
        firstFormData.setUuid(UUID.randomUUID().toString());
        firstFormData.setStatus("Some random status");
        firstFormData.setPatientUuid(patientUuid);
        formService.saveFormData(firstFormData);
        FormData secondFormData = new FormData();
        secondFormData.setUuid(UUID.randomUUID().toString());
        secondFormData.setStatus("Some other random status");
        secondFormData.setPatientUuid(patientUuid);
        formService.saveFormData(secondFormData);
        assertThat(formService.countAllFormData(), equalTo(2));
        List<FormData> savedFormData = formService.getFormDataByPatient(patientUuid, "Some random status");
        assertThat(savedFormData, hasSize(1));
        for (FormData formData : savedFormData) {
            assertThat(formData.getStatus(), equalTo("Some random status"));
        }
    }

    /**
     * @verifies return total count of forms matching the patient Uuid and status.
     * @see FormService#countFormDataByPatient(String, String)
     */
    @Test
    public void countFormDataByPatient_shouldReturnCountOfAllFormDataWithMatchingPatientAndStatus() throws Exception {
        String patientUuid = UUID.randomUUID().toString();
        assertThat(formService.countAllFormData(), equalTo(0));
        FormData firstFormData = new FormData();
        firstFormData.setUuid(UUID.randomUUID().toString());
        firstFormData.setStatus("Some random status");
        firstFormData.setPatientUuid(patientUuid);
        formService.saveFormData(firstFormData);
        FormData secondFormData = new FormData();
        secondFormData.setUuid(UUID.randomUUID().toString());
        secondFormData.setStatus("Some other random status");
        secondFormData.setPatientUuid(patientUuid);
        formService.saveFormData(secondFormData);
        assertThat(formService.countAllFormData(), equalTo(2));
        List<FormData> savedFormData = formService.getFormDataByPatient(patientUuid, "Some random status");
        assertThat(formService.countFormDataByPatient(patientUuid, "Some random status"), equalTo(1));
    }

    /**
     * @verifies delete form data from local data repository.
     * @see FormService#deleteFormData(com.muzima.api.model.FormData)
     */
    @Test
    public void deleteFormData_shouldDeleteFormDataFromLocalDataRepository() throws Exception {
        String userUuid = UUID.randomUUID().toString();
        assertThat(formService.countAllFormData(), equalTo(0));
        FormData firstFormData = new FormData();
        firstFormData.setUuid(UUID.randomUUID().toString());
        firstFormData.setStatus("Some random status");
        firstFormData.setUserUuid(userUuid);
        formService.saveFormData(firstFormData);
        FormData secondFormData = new FormData();
        secondFormData.setUuid(UUID.randomUUID().toString());
        secondFormData.setStatus("Some other random status");
        secondFormData.setUserUuid(userUuid);
        formService.saveFormData(secondFormData);
        assertThat(formService.countAllFormData(), equalTo(2));
        List<FormData> formDataList = formService.getAllFormData(StringUtil.EMPTY);
        for (FormData formData : formDataList) {
            formService.deleteFormData(formData);
        }
        assertThat(formService.countAllFormData(), equalTo(0));
    }

    /**
     * @verifies delete list of form data from local data repository.
     * @see FormService#deleteFormData(java.util.List)
     */
    @Test
    public void deleteFormData_shouldDeleteListOfFormDataFromLocalDataRepository() throws Exception {
        String userUuid = UUID.randomUUID().toString();
        assertThat(formService.countAllFormData(), equalTo(0));
        FormData firstFormData = new FormData();
        firstFormData.setUuid(UUID.randomUUID().toString());
        firstFormData.setStatus("Some random status");
        firstFormData.setUserUuid(userUuid);
        formService.saveFormData(firstFormData);
        FormData secondFormData = new FormData();
        secondFormData.setUuid(UUID.randomUUID().toString());
        secondFormData.setStatus("Some other random status");
        secondFormData.setUserUuid(userUuid);
        formService.saveFormData(secondFormData);
        assertThat(formService.countAllFormData(), equalTo(2));
        List<FormData> formDataList = formService.getAllFormData(StringUtil.EMPTY);
        formService.deleteFormData(formDataList);
        assertThat(formService.countAllFormData(), equalTo(0));
    }

    /**
     * @verifies sync the form data to the server.
     * @see FormService#syncFormData(com.muzima.api.model.FormData)
     */
    @Test
    public void syncFormData_shouldSyncTheFormDataToTheServer() throws Exception {
        FormData formData = new FormData();
        JSONObject jsonObject = new JSONObject();
        formData.setJsonPayload(jsonObject.toJSONString());
        // for form with observation data, we use encounter as the discriminator.
        formData.setDiscriminator("encounter");
        boolean synced = formService.syncFormData(formData);
        //TODO: Change this to true when ticket MUZIMA-436
        assertThat(synced, is(false));
    }

    @Test
    public void shouldFilterFormsByName() throws Exception {
        Form form1 = getFormWithName("b form");
        Form form2 = getFormWithName("a form");
        formService.saveForm(form1);
        formService.saveForm(form2);

        List<Form> allForms = formService.getAllForms();
        assertThat(allForms.size(), is(2));

        assertThat(allForms.get(0).getName(), is(form2.getName()));
    }

    @Test
    public void shouldRetrieveFormDataMatchingTemplateUUID() throws Exception {
        formService.saveFormData(getFormData("Random1", "template1"));
        formService.saveFormData(getFormData("Random2", "template1"));
        formService.saveFormData(getFormData("Random3", "template2"));

        List<FormData> formDataList = formService.getFormDataByTemplateUUID("template1");

        assertThat(formDataList.size(), is(2));
        assertThat(formDataList.get(0).getUuid(), is("Random1"));
        assertThat(formDataList.get(1).getUuid(), is("Random2"));
    }

    @Test
    public void shouldReturnEmptyListIfNoFormDataExistForTemplateUUID() throws Exception {
        List<FormData> formDataList = formService.getFormDataByTemplateUUID("someTemplateId");
        assertThat(formDataList.size(), is(0));
    }

    private FormData getFormData(String uuid, String templateUUID) {
        FormData formData = new FormData();
        formData.setUuid(uuid);
        formData.setTemplateUuid(templateUUID);
        return formData;
    }

    private Form getFormWithName(String formName) {
        Form form = new Form();
        form.setTags(new Tag[]{});
        form.setUuid(UUID.randomUUID().toString());
        form.setName(formName);
        return form;
    }
}
