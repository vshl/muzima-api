package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.FormDataDaoImpl;
import com.muzima.api.model.FormData;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
@ImplementedBy(FormDataDaoImpl.class)
public interface FormDataDao extends SearchableDao<FormData> {

    /**
     * Get all searchable form data with a particular status.
     *
     *
     * @param patientUuid the patient uuid associated to this form data.
     * @param userUuid    user's uuid associated to this form data.
     * @param status      the status of this form data.
     * @return list of all searchable object or empty list.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
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
    FormData getFormDataByUuid(String uuid) throws IOException;
}