package com.muzima.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
public class EvaluatedCohort extends OpenmrsSearchable {

    private Cohort cohort;

    private List<Member> members;

    /**
     * Get the cohort definition for this evaluated cohort.
     *
     * @return the cohort definition for this evaluated cohort.
     */
    public Cohort getCohort() {
        return cohort;
    }

    /**
     * Set the cohort definition for this evaluated cohort.
     *
     * @param cohort the cohort definition for this evaluated cohort.
     */
    public void setCohort(final Cohort cohort) {
        this.cohort = cohort;
    }

    /**
     * Add a new member object into the evaluated cohort.
     *
     * @param member the new member.
     */
    public void addMember(final Member member) {
        getMembers().add(member);
    }

    /**
     * Get the members of the evaluated cohort.
     *
     * @return the members of the evaluated cohort.
     */
    public List<Member> getMembers() {
        if (members == null) {
            members = new ArrayList<Member>();
        }
        return members;
    }

    /**
     * Set the members of the evaluated cohort.
     *
     * @param members the members of the evaluated cohort.
     */
    public void setMembers(final List<Member> members) {
        this.members = members;
    }
}
