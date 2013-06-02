package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.MemberDaoImpl;
import com.muzima.api.model.Member;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
@ImplementedBy(MemberDaoImpl.class)
public interface MemberDao extends OpenmrsDao<Member> {
    /**
     * Get member objects by their cohort's uuid.
     *
     * @param cohortUuid the cohort uuid.
     * @return list of all member objects for the cohort's uuid.
     * @throws IOException    when search api unable to process the resource.
     */
    List<Member> getByCohortUuid(final String cohortUuid) throws IOException;
}
