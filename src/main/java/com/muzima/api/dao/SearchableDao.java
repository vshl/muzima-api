package com.muzima.api.dao;

import com.muzima.search.api.model.object.Searchable;

import java.io.IOException;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
public interface SearchableDao<T extends Searchable> {
    /**
     * Save the object to the local repository.
     *
     * @param object   the object to be saved.
     * @param resource the resource descriptor used for saving.
     * @throws IOException when search api unable to process the resource.
     */
    void save(final T object, final String resource) throws IOException;

    /**
     * Save list of objects to the local repository. Use this save method when you want to save multiple objects
     * at the same time.
     *
     * @param objects  the objects to be saved.
     * @param resource the resource descriptor used for saving.
     * @throws IOException when search api unable to process the resource.
     */
    void save(final List<T> objects, final String resource) throws IOException;

    /**
     * Update the saved object in the local repository.
     *
     * @param object   the object to be updated.
     * @param resource the resource descriptor used for saving.
     * @throws IOException when search api unable to process the resource.
     */
    void update(final T object, final String resource) throws IOException;

    /**
     * Update list of objects in the local repository. Use this save method when you want to update multiple objects
     * at the same time.
     *
     * @param objects  the objects to be updated.
     * @param resource the resource descriptor used for updating.
     * @throws IOException when search api unable to process the resource.
     */
    void update(final List<T> objects, final String resource) throws IOException;

    /**
     * Count total of searchable objects for a type.
     *
     * @return total number of searchable objects.
     * @throws IOException when search api unable to process the resource.
     */
    Integer countAll() throws IOException;

    /**
     * Get all searchable object for the particular type.
     *
     * @return list of all searchable object or empty list.
     * @throws IOException when search api unable to process the resource.
     */
    List<T> getAll() throws IOException;

    /**
     * Get all searchable object for a particular type with paging.
     *
     * @param page     the page number.
     * @param pageSize the number of elements in the page.
     * @return list of objects less or equals than the page size parameter.
     */
    List<T> getAll(final Integer page, final Integer pageSize) throws IOException;

    /**
     * Delete the searchable object from the lucene repository.
     *
     * @param searchable the object to be deleted.
     * @param resource   the resource descriptor used to retrieve the object from the repository.
     * @throws IOException when search api unable to process the resource.
     */
    void delete(final T searchable, final String resource) throws IOException;

    /**
     * Delete list of objects from the local repository. Use this save method when you want to delete multiple
     * objects at the same time.
     *
     * @param objects  the objects to be deleted.
     * @param resource the resource descriptor used for deleting.
     * @throws IOException when search api unable to process the resource.
     */
    void delete(final List<T> objects, final String resource) throws IOException;
}
