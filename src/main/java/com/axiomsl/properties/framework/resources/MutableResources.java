package com.axiomsl.properties.framework.resources;

import com.axiomsl.properties.framework.sources.PropertySource;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MutableResources<T extends NamedResource> implements Iterable<T> {
    private final List<ResourceHolder<T>> resources = new CopyOnWriteArrayList<>();

    public boolean contains(String name) {
        return resources.contains(ResourceHolder.ofStub(name));
    }

    public T get(String name) {
        int index = resources.indexOf(ResourceHolder.ofStub(name));
        return (index != -1 ? resources.get(index).getResource() : null);
    }

    @Override
    public Iterator<T> iterator() {
        final Iterator<ResourceHolder<T>> iterator = resources.iterator();
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public T next() {
                return iterator.next().getResource();
            }
        };
    }

    /**
     * Add the given property source object with highest precedence.
     */
    public void addFirst(T resource) {
        removeIfPresent(resource);
        resources.add(0, ResourceHolder.of(resource));
    }

    /**
     * Add the given property source object with lowest precedence.
     */
    public void addLast(T resource) {
        removeIfPresent(resource);
        resources.add(ResourceHolder.of(resource));
    }

    /**
     * Add the given property source object with precedence immediately higher
     * than the named relative property source.
     */
    public void addBefore(String relativePropertySourceName, T resource) {
        assertLegalRelativeAddition(relativePropertySourceName, resource);
        removeIfPresent(resource);
        int index = assertPresentAndGetIndex(relativePropertySourceName);
        addAtIndex(index, resource);
    }

    /**
     * Add the given property source object with precedence immediately lower
     * than the named relative property source.
     */
    public void addAfter(String relativePropertySourceName, T resource) {
        assertLegalRelativeAddition(relativePropertySourceName, resource);
        removeIfPresent(resource);
        int index = assertPresentAndGetIndex(relativePropertySourceName);
        addAtIndex(index + 1, resource);
    }

    /**
     * Remove and return the property source with the given name, {@code null} if not found.
     *
     * @param name the name of the property source to find and remove
     */
    public T remove(String name) {
        int index = resources.indexOf(ResourceHolder.ofStub(name));
        return (index != -1 ? resources.remove(index).getResource() : null);
    }

    /**
     * Replace the property source with the given name with the given property source object.
     *
     * @param name     the name of the property source to find and replace
     * @param resource the replacement property source
     * @throws IllegalArgumentException if no property source with the given name is present
     * @see #contains
     */
    public void replace(String name, T resource) {
        int index = assertPresentAndGetIndex(name);
        resources.set(index, ResourceHolder.of(resource));
    }

    /**
     * Return the number of {@link PropertySource} objects contained.
     */
    public int size() {
        return resources.size();
    }

    /**
     * Remove the given property source if it is present.
     */
    protected void removeIfPresent(T resource) {
        resources.remove(ResourceHolder.of(resource));
    }

    /**
     * Ensure that the given property source is not being added relative to itself.
     */
    protected void assertLegalRelativeAddition(String relativePropertySourceName, T resource) {
        String newPropertySourceName = resource.getName();
        if (relativePropertySourceName.equals(newPropertySourceName)) {
            throw new IllegalArgumentException(
                    String.format("PropertySource named [%s] cannot be added relative to itself", newPropertySourceName));
        }
    }

    /**
     * Assert that the named property source is present and return its index.
     *
     * @param name the {@linkplain PropertySource#getName() name of the property source}
     *             to find
     * @throws IllegalArgumentException if the named property source is not present
     */
    private int assertPresentAndGetIndex(String name) {
        int index = resources.indexOf(ResourceHolder.ofStub(name));
        if (index == -1) {
            throw new IllegalArgumentException(String.format("PropertySource named [%s] does not exist", name));
        }
        return index;
    }

    /**
     * Add the given property source at a particular index in the list.
     */
    private void addAtIndex(int index, T resource) {
        removeIfPresent(resource);
        resources.add(index, ResourceHolder.of(resource));
    }

    @Override
    public String toString() {
        return "MutableResources{}";
    }
}
