/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.dao.impl;

import com.muzima.api.dao.FormDao;
import com.muzima.api.model.Form;

public class FormDaoImpl extends OpenmrsDaoImpl<Form> implements FormDao {

    private static final String TAG = FormDaoImpl.class.getSimpleName();

    protected FormDaoImpl() {
        super(Form.class);
    }
}
