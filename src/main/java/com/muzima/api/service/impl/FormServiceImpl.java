/**
 * Copyright 2012 Muzima Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.muzima.api.service.impl;

import com.google.inject.Inject;
import com.muzima.api.dao.FormDao;
import com.muzima.api.dao.FormDataDao;
import com.muzima.api.dao.FormTemplateDao;
import com.muzima.api.model.Form;
import com.muzima.api.model.FormData;
import com.muzima.api.model.FormTemplate;
import com.muzima.api.service.FormService;
import com.muzima.search.api.util.StringUtil;
import com.muzima.util.Constants;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormServiceImpl implements FormService {

    @Inject
    private FormDao formDao;

    @Inject
    private FormDataDao formDataDao;

    @Inject
    private FormTemplateDao formTemplateDao;

    protected FormServiceImpl() {
    }

    /**
     * Download a single form record from the form rest resource into the local lucene repository.
     *
     * @param uuid the uuid of the form.
     * @throws IOException when search api unable to process the resource.
     * @should download form with matching uuid.
     */
    @Override
    public Form downloadFormByUuid(final String uuid) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>(){{
            put("uuid", uuid);
        }};
        List<Form> forms = formDao.download(parameter, Constants.UUID_FORM_RESOURCE);
        if (forms.size() > 1) {
            throw new IOException("Unable to uniquely identify a form record.");
        } else if (forms.size() == 0) {
            return null;
        }
        return forms.get(0);
    }

    /**
     * Download all forms with name similar to the partial name passed in the parameter.
     *
     * @param name the partial name of the form to be downloaded. When empty, will return all forms available.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should download all form with partially matched name.
     * @should download all form when name is empty.
     */
    @Override
    public List<Form> downloadFormsByName(final String name) throws IOException, ParseException {
        Map<String, String> parameter = new HashMap<String, String>(){{
            put("q", name);
        }};
        return formDao.download(parameter, Constants.SEARCH_FORM_RESOURCE);
    }

    /**
     * Save form object to the local lucene repository.
     *
     * @param form the form object to be saved.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public void saveForm(final Form form) throws IOException {
        formDao.save(form, Constants.UUID_FORM_RESOURCE);
    }

    /**
     * Update form object to the local lucene repository.
     *
     * @param form the form object to be updated.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public void updateForm(final Form form) throws IOException {
        formDao.update(form, Constants.UUID_FORM_RESOURCE);
    }

    /**
     * Get form by the uuid of the form.
     *
     * @param uuid the form uuid.
     * @return form with matching uuid or null when no form match the uuid.
     * @throws IOException when search api unable to process the resource.
     * @should return form with matching uuid.
     * @should return null when no form match the uuid.
     */
    @Override
    public Form getFormByUuid(final String uuid) throws IOException {
        return formDao.getByUuid(uuid);
    }

    /**
     * Get all form with matching name (or partial name).
     *
     * @param name the form name.
     * @return form with matching uuid or null when no form match the uuid.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return form with matching uuid.
     * @should return null when no form match the uuid.
     */
    @Override
    public List<Form> getFormByName(final String name) throws IOException, ParseException {
        return formDao.getByName(name);
    }

    /**
     * @return all registered forms or empty list when no form is registered.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return all registered forms.
     * @should return empty list when no form is registered.
     */
    @Override
    public List<Form> getAllForms() throws IOException, ParseException {
        return formDao.getAll();
    }

    /**
     * Delete form from the repository.
     *
     * @param form the form to be deleted.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public void deleteForm(final Form form) throws IOException {
        formDao.delete(form, Constants.UUID_FORM_RESOURCE);
    }

    /**
     * Check whether the form template for a particular form object is already downloaded or not.
     *
     * @param formUuid the uuid of the form.
     * @return true when the form template for the form is already downloaded.
     * @throws java.io.IOException when the search api unable to process the resource.
     */
    @Override
    public Boolean isFormTemplateDownloaded(final String formUuid) throws IOException {
        return formTemplateDao.exists(formUuid);
    }

    /**
     * Download form template by the uuid of the form associated with the form template.
     *
     * @param uuid the uuid of the form.
     * @return the form template with matching uuid downloaded from the server.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public FormTemplate downloadFormTemplateByUuid(final String uuid) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>(){{
            put("uuid", uuid);
        }};
        List<FormTemplate> formTemplates = formTemplateDao.download(parameter, Constants.UUID_FORM_TEMPLATE_RESOURCE);
        if (formTemplates.size() > 1) {
            throw new IOException("Unable to uniquely identify a form template record.");
        } else if (formTemplates.size() == 0) {
            return null;
        }
        return formTemplates.get(0);
    }

    /**
     * Download form templates by the name of the form associated with the form template.
     *
     * @param name the name of the form.
     * @return list of all matching form templates based on the name of the form.
     * @throws org.apache.lucene.queryParser.ParseException
     *                             when query parser from lucene unable to parse the query string.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public List<FormTemplate> downloadFormTemplatesByName(final String name) throws IOException, ParseException {
        Map<String, String> parameter = new HashMap<String, String>(){{
            put("q", name);
        }};
        return formTemplateDao.download(parameter, Constants.SEARCH_FORM_TEMPLATE_RESOURCE);
    }

    /**
     * Save a new form template to the repository.
     *
     * @param formTemplate the form template to be saved.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public void saveFormTemplate(final FormTemplate formTemplate) throws IOException {
        formTemplateDao.save(formTemplate, Constants.UUID_FORM_TEMPLATE_RESOURCE);
    }

    /**
     * Get a form template by the uuid.
     *
     * @param uuid the form template uuid.
     * @return the form template.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public FormTemplate getFormTemplateByUuid(final String uuid) throws IOException {
        return formTemplateDao.getByUuid(uuid);
    }

    /**
     * Get all saved form templates from the local repository.
     *
     * @return all saved form templates or empty list when there's no form template saved.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public List<FormTemplate> getAllFormTemplates() throws IOException, ParseException {
        return formTemplateDao.getAll();
    }

    /**
     * Delete a form template from the repository.
     *
     * @param formTemplate the form template to be deleted.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public void deleteFormTemplate(final FormTemplate formTemplate) throws IOException {
        formTemplateDao.delete(formTemplate, Constants.UUID_FORM_TEMPLATE_RESOURCE);
    }

    /**
     * Save a new form data object to the database.
     *
     * @param formData the form data to be saved.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public void saveFormData(final FormData formData) throws IOException {
        formDataDao.save(formData, Constants.LOCAL_FORM_DATA_RESOURCE);
    }

    /**
     * Get a single form data object from the local data repository.
     *
     * @param uuid the uuid for the form data.
     * @return the form data object.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public FormData getFormDataByUuid(final String uuid) throws IOException {
        return formDataDao.getByUuid(uuid);
    }

    /**
     * Get all form data filtering on the status of the form data.
     *
     * @param status the status of the form data (optional).
     * @return all form data with matching status.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public List<FormData> getAllFormData(final String status) throws IOException, ParseException {
        return formDataDao.getAll(StringUtil.EMPTY, StringUtil.EMPTY, status);
    }

    /**
     * Get form data associated with certain user with filtering on the status of the form data.
     *
     * @param userUuid the uuid of the user.
     * @param status   the status of the form data (optional).
     * @return all form data for the user with matching status.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public List<FormData> getFormDataByUser(final String userUuid, final String status) throws IOException, ParseException {
        return formDataDao.getAll(StringUtil.EMPTY, userUuid, status);
    }

    /**
     * Get form data associated with certain user with filtering on the status of the form data.
     *
     * @param patientUuid the uuid of the patient
     * @param status      the status of the form data (optional).
     * @return all form data for the patient with matching status.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public List<FormData> getFormDataByPatient(final String patientUuid, final String status) throws IOException, ParseException {
        return formDataDao.getAll(patientUuid, StringUtil.EMPTY, status);
    }

    /**
     * Delete an instance of form data.
     *
     * @param formData the form data
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public void deleteFormDate(final FormData formData) throws IOException {
        formDataDao.delete(formData, Constants.LOCAL_FORM_DATA_RESOURCE);
    }
}
