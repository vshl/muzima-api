/**
 * Copyright (c) 2014 - 2017. The Trustees of Indiana University, Moi University, and Vanderbilt
 * University Medical Center.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional healthcare
 * disclaimer. If the user is an entity intending to commercialize any application that uses this code in
 * a for-profit venture, please contact the copyright holder.
 */
package com.muzima.api.model;

import com.muzima.util.DateUtils;

import java.util.Date;

public class CohortMembership extends OpenmrsSearchable implements Comparable<CohortMembership> {

	private Cohort cohort;

	private Patient patient;

	private Date startDate;

	private Date endDate;

	private Boolean voided;

	public CohortMembership() {
	}

	public CohortMembership(final Patient patient, final Date startDate) {
		this.patient = patient;
		this.startDate = startDate;
	}

	public CohortMembership(final Patient patient) {
		new CohortMembership(patient, new Date());
	}

	public Cohort getCohort() {
		return cohort;
	}

	public void setCohort(Cohort cohort) {
		this.cohort = cohort;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getVoided() {
		return voided;
	}

	public void setVoided(Boolean voided) {
		this.voided = voided;
	}

	/**
	 * Compares asOfDate to [startDate, endDate], inclusive of both endpoints.
	 * @param asOfDate date to compare if handler is active or inactive
	 * @return boolean true/false if handler is active/inactive
	 */
	public boolean isActive(Date asOfDate) {
		Date date = asOfDate == null ? new Date() : asOfDate;
		return !this.getVoided() && DateUtils.compare(startDate, date) <= 0
				&& DateUtils.compareWithNullAsLatest(date, endDate) <= 0;
	}

	public boolean isActive() {
		return isActive(null);
	}

	public boolean contains(String uuid) {
		return getUuid().equalsIgnoreCase(uuid);
	}

	@Override
	public int compareTo(CohortMembership o) {
		int ret = this.getVoided().compareTo(o.getVoided());
		if (ret == 0) {
			ret = -DateUtils.compareWithNullAsLatest(this.getEndDate(), o.getEndDate());
		}
		if (ret == 0) {
			ret = -DateUtils.compareWithNullAsEarliest(this.getStartDate(), o.getStartDate());
		}
		if (ret == 0) {
			ret = this.getUuid().compareTo(o.getUuid());
		}
		return ret;
	}

	/**
	 * Hash code implementation will be based on the uuid value of the object.
	 *
	 * @return the hash code of the object.
	 */
	@Override
	public int hashCode() {
		if (getUuid() == null) {
			return super.hashCode();
		}
		return getUuid().hashCode();
	}

	/**
	 * Object equality implementation will be based on the uuid of the object.
	 *
	 * @param obj the reference object with which to compare.
	 * @return {@code true} if this object is the same as the obj
	 * argument; {@code false} otherwise.
	 * @see #hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CohortMembership)) {
			return false;
		}
		CohortMembership other = (CohortMembership) obj;
		return getUuid() != null && getUuid().equalsIgnoreCase(other.getUuid())
				&& getPatient().getUuid().equalsIgnoreCase(other.getPatient().getUuid())
				&& isActive() == other.isActive();
	}
}
