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
package com.muzima.api.model;

/**
 * FormTemplate is a single reference to the physical raw data that can be rendered for form filling process. The result
 * of a form filling process is the FormData object. FormTemplate hold a reference to the Form object.
 */
public class FormTemplate extends OpenmrsSearchable {

    private String model;

    private String modelJson;

    private String html;

    /**
     * Get the form model.
     *
     * @return the form model.
     */
    public String getModel() {
        return model;
    }

    /**
     * Set the form model.
     *
     * @param model the form model.
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Get the form model json.
     *
     * @return the form model json.
     */
    public String getModelJson() {
        return modelJson;
    }

    /**
     * Set the form model json.
     *
     * @param modelJson the form model json.
     */
    public void setModelJson(String modelJson) {
        this.modelJson = modelJson;
    }

    /**
     * Get the form html.
     *
     * @return the form html.
     */
    public String getHtml() {
        return html;
    }

    /**
     * Set the form html.
     *
     * @param html the form html.
     */
    public void setHtml(String html) {
        this.html = html;
    }
}
