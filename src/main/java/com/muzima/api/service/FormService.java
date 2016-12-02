/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.service;

import com.google.inject.ImplementedBy;
import com.muzima.api.model.Form;
import com.muzima.api.model.FormData;
import com.muzima.api.model.FormTemplate;
import com.muzima.api.service.impl.FormServiceImpl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Service handling all operation to the @{Form} actor/model
 */
@ImplementedBy(FormServiceImpl.class)
public interface FormService extends MuzimaInterface {

    /**
     * Download a single form record from the form rest resource into the local lucene repository.
     *
     * @param uuid the uuid of the form.
     * @throws IOException when search api unable to process the resource.
     * @should download form with matching uuid.
     */
    Form downloadFormByUuid(final String uuid) throws IOException;

    /**
     * Download all forms with name similar to the partial name passed in the parameter.
     *
     * @param name     the partial name of the form to be downloaded. When empty, will return all forms available.
     * @param syncDate time the forms where last synched to the server.
     * @throws IOException when search api unable to process the resource.
     * @should download all form with partially matched name.
     * @should download all form when name is empty.
     */
    List<Form> downloadFormsByName(final String name, final Date syncDate) throws IOException;

    /**
     * Download all forms with name similar to the partial name passed in the parameter.
     *
     * @param name the partial name of the form to be downloaded. When empty, will return all forms available.
     * @throws IOException when search api unable to process the resource.
     * @should download all form with partially matched name.
     * @should download all form when name is empty.
     */
    List<Form> downloadFormsByName(final String name) throws IOException;

    /**
     * Save form object to the local lucene repository.
     *
     * @param form the form object to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save form to local data repository.
     */
    void saveForm(final Form form) throws IOException;

    /**
     * Save form objects to the local lucene repository.
     *
     * @param forms the form objects to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save list of forms to local data repository.
     */
    void saveForms(final List<Form> forms) throws IOException;

    /**
     * Update form object to the local lucene repository.
     *
     * @param form the form object to be updated.
     * @throws IOException when search api unable to process the resource.
     * @should replace existing form in local data repository.
     */
    void updateForm(final Form form) throws IOException;

    /**
     * Update form objects to the local lucene repository.
     *
     * @param forms the form objects to be updated.
     * @throws IOException when search api unable to process the resource.
     * @should replace list of forms in local data repository.
     */
    void updateForms(final List<Form> forms) throws IOException;

    /**
     * Get form by the uuid of the form.
     *
     * @param uuid the form uuid.
     * @return form with matching uuid or null when no form match the uuid.
     * @throws IOException when search api unable to process the resource.
     * @should return form with matching uuid.
     * @should return null when no form match the uuid.
     */
    Form getFormByUuid(final String uuid) throws IOException;

    /**
     * Count all forms with matching name.
     *
     * @param name the partial form name.
     * @return total number of form with matching the partial name.
     * @throws IOException when search api unable to process the resource.
     * @should count all forms with matching name.
     */
    Integer countFormByName(final String name) throws IOException;

    /**
     * Get all forms with matching name (or partial name).
     *
     * @param name the form name.
     * @return form with matching uuid or null when no form match the uuid.
     * @throws IOException when search api unable to process the resource.
     * @should return form with matching name.
     * @should return null when no form match the name.
     */
    List<Form> getFormByName(final String name) throws IOException;

    /**
     * Count all form objects.
     *
     * @return the total number of the form objects.
     * @throws IOException when search api unable to process the resource.
     * @should return number of all forms in local data repository.
     */
    Integer countAllForms() throws IOException;

    /**
     * Get all form objects.
     *
     * @return all registered forms or empty list when no form is registered.
     * @throws IOException when search api unable to process the resource.
     * @should return all registered forms.
     * @should return empty list when no form is registered.
     */
    List<Form> getAllForms() throws IOException;

    /**
     * Delete form from the repository.
     *
     * @param form the form to be deleted.
     * @throws IOException when search api unable to process the resource.
     * @should delete form from local data repository.
     */
    void deleteForm(final Form form) throws IOException;

    /**
     * Delete forms from the repository.
     *
     * @param forms the forms to be deleted.
     * @throws IOException when search api unable to process the resource.
     * @should delete list of forms from local data repository.
     */
    void deleteForms(final List<Form> forms) throws IOException;

    /**
     * Check whether the form template for a particular form object is already downloaded or not.
     *
     * @param formUuid the uuid of the form.
     * @return true when the form template for the form is already downloaded.
     * @throws IOException when the search api unable to process the resource.
     * @should return true if the template of a form is downloaded.
     */
    Boolean isFormTemplateDownloaded(final String formUuid) throws IOException;

    /**
     * Download form template by the uuid of the form associated with the form template.
     *
     * @param uuid the uuid of the form.
     * @return the form template with matching uuid downloaded from the server.
     * @throws IOException when search api unable to process the resource.
     * @should download the form template by the uuid of the form.
     */
    FormTemplate downloadFormTemplateByUuid(final String uuid) throws IOException;

    /**
     * Download form templates by the name of the form associated with the form template.
     *
     * @param name the name of the form.
     * @return list of all matching form templates based on the name of the form.
     * @throws IOException when search api unable to process the resource.
     * @should download the form template by the name of the form.
     */
    List<FormTemplate> downloadFormTemplatesByName(final String name) throws IOException;

    /**
     * Download form templates by the name of the form associated with the form template.
     *
     * @param name     the name of the form.
     * @param syncDate time the forms where last synched to the server.
     * @return list of all matching form templates based on the name of the form.
     * @throws IOException when search api unable to process the resource.
     * @should download the form template by the name of the form.
     */
    List<FormTemplate> downloadFormTemplatesByName(final String name, final Date syncDate) throws IOException;

    /**
     * Save a new form template to the repository.
     *
     * @param formTemplate the form template to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save the form template to the local data repository.
     */
    void saveFormTemplate(final FormTemplate formTemplate) throws IOException;

    /**
     * Save form templates to the repository.
     *
     * @param formTemplates the form templates to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save the list of form templates to local data repository.
     */
    void saveFormTemplates(final List<FormTemplate> formTemplates) throws IOException;

    /**
     * Get a form template by the uuid.
     *
     * @param uuid the form template uuid.
     * @return the form template.
     * @throws IOException when search api unable to process the resource.
     * @should get the form template by the uuid.
     */
    FormTemplate getFormTemplateByUuid(final String uuid) throws IOException;

    /**
     * Get the total number of saved form templates from the local repository.
     *
     * @return the total number of saved form templates.
     * @throws IOException when search api unable to process the resource.
     * @should count all form templates in local data repository.
     */
    Integer countAllFormTemplates() throws IOException;

    /**
     * Get all saved form templates from the local repository.
     *
     * @return all saved form templates or empty list when there's no form template saved.
     * @throws IOException when search api unable to process the resource.
     * @should return all saved form templates fom local data repository.
     */
    List<FormTemplate> getAllFormTemplates() throws IOException;

    /**
     * Delete a form template from the repository.
     *
     * @param formTemplate the form template to be deleted.
     * @throws IOException when search api unable to process the resource.
     * @should delete the form template from local data repository.
     */
    void deleteFormTemplate(final FormTemplate formTemplate) throws IOException;

    /**
     * Delete form templates from the repository.
     *
     * @param formTemplates the form templates to be deleted.
     * @throws IOException when search api unable to process the resource.
     * @should delete the list of forms from local data repository.
     */
    void deleteFormTemplates(final List<FormTemplate> formTemplates) throws IOException;

    /**
     * Save a new form data object to the database.
     *
     * @param formData the form data to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save form data to local data repository.
     */
    void saveFormData(final FormData formData) throws IOException;

    /**
     * Update existing form data object in local data repository.
     *
     * @param formData the form data to be updated.
     * @throws IOException when search api unable to process the resource.
     * @should update form data in local data repository.
     */
    void updateFormData(final FormData formData) throws IOException;

    /**
     * Get a single form data object from the local data repository.
     *
     * @param uuid the uuid for the form data.
     * @return the form data object.
     * @throws IOException when search api unable to process the resource.
     * @should return form data by the uuid.
     */
    FormData getFormDataByUuid(final String uuid) throws IOException;

    /**
     * Get all form data matching a list of form data uuids.
     *
     * @param uuids the list of uuids.
     * @return all form data whose Ids are included in the list.
     * @throws IOException when search api unable to process the resource.
     * @should return all form data with matching uuids.
     */
    List<FormData> getFormDataByUuids(final List<String> uuids) throws IOException;

    /**
     * Count total number of form data.
     *
     * @return total number of form data.
     * @throws IOException when search api unable to process the resource.
     * @should count all form data in local data repository.
     */
    Integer countAllFormData() throws IOException;

    /**
     * Get all form data filtering on the status of the form data.
     *
     * @param status the status of the form data (optional).
     * @return all form data with matching status.
     * @throws IOException when search api unable to process the resource.
     * @should return all form data with matching status from local data repository.
     */
    List<FormData> getAllFormData(final String status) throws IOException;

    /**
     * Get form data associated with certain user with filtering on the status of the form data.
     *
     * @param userUuid the uuid of the user.
     * @param status   the status of the form data (optional).
     * @return all form data for the user with matching status.
     * @throws IOException when search api unable to process the resource.
     * @should return all form data with matching user and status.
     */
    List<FormData> getFormDataByUser(final String userUuid, final String status) throws IOException;

    /**
     * Get form data associated with certain user with filtering on the status of the form data.
     *
     * @param patientUuid the uuid of the patient
     * @param status      the status of the form data (optional).
     * @return all form data for the patient with matching status.
     * @throws IOException when search api unable to process the resource.
     * @should return all form data with matching patient and status.
     */
    List<FormData> getFormDataByPatient(final String patientUuid, final String status) throws IOException;

    /**
     * Get form data associated with certain user with filtering on the status of the form data.
     *
     * @param patientUuid the uuid of the patient
     * @param status      the status of the form data (optional).
     * @return total count of forms matching the patient Uuid and status.
     * @throws IOException when search api unable to process the resource.
     * @should return total count of forms matching the patient Uuid and status.
     */
    int countFormDataByPatient(final String patientUuid, final String status) throws IOException;

    /**
     * Delete an instance of form data.
     *
     * @param formData the form data
     * @throws IOException when search api unable to process the resource.
     * @should delete form data from local data repository.
     */
    void deleteFormData(final FormData formData) throws IOException;

    /**
     * Delete instances of form data.
     *
     * @param formData the instances of form data
     * @throws IOException when search api unable to process the resource.
     * @should delete list of form data from local data repository.
     */
    void deleteFormData(final List<FormData> formData) throws IOException;

    /**
     * Delete instances of form template.
     *
     * @param formUUID List of UUIDs of form that are to be deleted.
     * @throws IOException
     * @should delete list of form templates from local data repository.
     */
    void deleteFormTemplateByUUIDs(final List<String> formUUID) throws IOException;

    /**
     * Send form data information to the server.
     *
     * @param formData the form data.
     * @return true when the form data successfully synced to the server.
     * @throws IOException when search api unable to process the resource.
     * @should sync the form data to the server.
     */
    boolean syncFormData(final FormData formData) throws IOException;

    /**
     * Fetches FormData from DB which matches templateUUID.
     *
     * @param templateUUID
     * @return List of FormData that matches templateUUID.
     */
    List<FormData> getFormDataByTemplateUUID(String templateUUID) throws IOException;
}
