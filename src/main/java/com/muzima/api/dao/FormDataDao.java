/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */
package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.FormDataDaoImpl;
import com.muzima.api.model.FormData;

import java.io.IOException;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
@ImplementedBy(FormDataDaoImpl.class)
public interface FormDataDao extends SearchableDao<FormData> {

    /**
     * Count all searchable form data with a particular status.
     *
     * @param patientUuid the patient uuid associated to this form data.
     * @param userUuid    user's uuid associated to this form data.
     * @param status      the status of this form data.
     * @return total count of all searchable objects or zero.
     * @throws IOException when search api unable to process the resource.
     */
    int countAllFormData(final String patientUuid, final String userUuid,
                         final String status) throws IOException;
    /**
     * Get all searchable form data with a particular status.
     *
     * @param patientUuid the patient uuid associated to this form data.
     * @param userUuid    user's uuid associated to this form data.
     * @param status      the status of this form data.
     * @return list of all searchable object or empty list.
     * @throws IOException when search api unable to process the resource.
     */
    List<FormData> getAllFormData(final String patientUuid, final String userUuid,
                                  final String status) throws IOException;

    List<FormData> getAllFormData(final String patientUuid, final String userUuid, final String status,
                                  final Integer page, final Integer pageSize) throws IOException;
    /**
     * Get the form data object using the uuid.
     *
     * @param uuid the uuid of the form data object.
     * @return the form data object.
     * @throws IOException when search api unable to process the resource.
     */
    FormData getFormDataByUuid(final String uuid) throws IOException;
    /**
     * Get all form data objects matching given uuids
     *
     * @param uuids the list of uuids of the form data objects.
     * @return the list of matched form data objects.
     * @throws IOException when search api unable to process the resource.
     */
    List<FormData> getFormDataByUuid(final List<String> uuids) throws IOException;

    boolean syncFormData(final FormData formData) throws IOException;

    /**
     * Get the FormData based on templateUUID.
     *
     * @param templateUUID template UUID, which requires FormData
     * @return List of FormData associated with the given templateUUID. Returns empty list if no FormData present.
     * @throws IOException
     */
    List<FormData> getFormDataByTemplateUUID(final String templateUUID) throws IOException;
}