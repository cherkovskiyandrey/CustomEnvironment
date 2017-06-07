package com.axiomsl.properties.framework;

import com.axiomsl.properties.framework.sources.PropertySource;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Allows manipulation of contained property sources and provides a constructor
 * for copying an existing {@code PropertySources} instance.
 * <p>
 * <p>Where <em>precedence</em> is mentioned in methods such as {@link #addFirst}
 * and {@link #addLast}, this is with regard to the order in which property sources
 * will be searched when resolving a given property with a {@link Configuration}.
 *
 * @author Chris Beams
 */
public class MutablePropertySources implements Iterable<PropertySource<?>> {
    private final List<PropertySource<?>> propertySourceList = new CopyOnWriteArrayList<>();

    public MutablePropertySources() {
    }

    /**
     * Create a new {@code MutablePropertySources} from the given propertySources
     * object, preserving the original order of contained {@code PropertySource} objects.
     */
    public MutablePropertySources(List<PropertySource<?>> propertySources) {
        Objects.requireNonNull(propertySources);
        propertySources.forEach(this::addLast);
    }

    public boolean contains(String name) {
        return this.propertySourceList.contains(PropertySource.named(name));
    }

    public PropertySource<?> get(String name) {
        int index = this.propertySourceList.indexOf(PropertySource.named(name));
        return (index != -1 ? this.propertySourceList.get(index) : null);
    }

    public Iterator<PropertySource<?>> iterator() {
        return this.propertySourceList.iterator();
    }

    /**
     * Add the given property source object with highest precedence.
     */
    public void addFirst(PropertySource<?> propertySource) {
        removeIfPresent(propertySource);
        this.propertySourceList.add(0, propertySource);
    }

    /**
     * Add the given property source object with lowest precedence.
     */
    public void addLast(PropertySource<?> propertySource) {
        removeIfPresent(propertySource);
        this.propertySourceList.add(propertySource);
    }

    /**
     * Add the given property source object with precedence immediately higher
     * than the named relative property source.
     */
    public void addBefore(String relativePropertySourceName, PropertySource<?> propertySource) {
        assertLegalRelativeAddition(relativePropertySourceName, propertySource);
        removeIfPresent(propertySource);
        int index = assertPresentAndGetIndex(relativePropertySourceName);
        addAtIndex(index, propertySource);
    }

    /**
     * Add the given property source object with precedence immediately lower
     * than the named relative property source.
     */
    public void addAfter(String relativePropertySourceName, PropertySource<?> propertySource) {
        assertLegalRelativeAddition(relativePropertySourceName, propertySource);
        removeIfPresent(propertySource);
        int index = assertPresentAndGetIndex(relativePropertySourceName);
        addAtIndex(index + 1, propertySource);
    }

    /**
     * Return the precedence of the given property source, {@code -1} if not found.
     */
    public int precedenceOf(PropertySource<?> propertySource) {
        return this.propertySourceList.indexOf(propertySource);
    }

    /**
     * Remove and return the property source with the given name, {@code null} if not found.
     *
     * @param name the name of the property source to find and remove
     */
    public PropertySource<?> remove(String name) {
        int index = this.propertySourceList.indexOf(PropertySource.named(name));
        return (index != -1 ? this.propertySourceList.remove(index) : null);
    }

    /**
     * Replace the property source with the given name with the given property source object.
     *
     * @param name           the name of the property source to find and replace
     * @param propertySource the replacement property source
     * @throws IllegalArgumentException if no property source with the given name is present
     * @see #contains
     */
    public void replace(String name, PropertySource<?> propertySource) {
        int index = assertPresentAndGetIndex(name);
        this.propertySourceList.set(index, propertySource);
    }

    /**
     * Return the number of {@link PropertySource} objects contained.
     */
    public int size() {
        return this.propertySourceList.size();
    }

    @Override
    public String toString() {
        return "MutablePropertySources{" +
                "propertySourceList=" + propertySourceList.stream().map(PropertySource::getName).collect(Collectors.joining(", ")) +
                '}';
    }

    /**
     * Ensure that the given property source is not being added relative to itself.
     */
    protected void assertLegalRelativeAddition(String relativePropertySourceName, PropertySource<?> propertySource) {
        String newPropertySourceName = propertySource.getName();
        if (relativePropertySourceName.equals(newPropertySourceName)) {
            throw new IllegalArgumentException(
                    String.format("PropertySource named [%s] cannot be added relative to itself", newPropertySourceName));
        }
    }

    /**
     * Remove the given property source if it is present.
     */
    protected void removeIfPresent(PropertySource<?> propertySource) {
        this.propertySourceList.remove(propertySource);
    }

    /**
     * Add the given property source at a particular index in the list.
     */
    private void addAtIndex(int index, PropertySource<?> propertySource) {
        removeIfPresent(propertySource);
        this.propertySourceList.add(index, propertySource);
    }

    /**
     * Assert that the named property source is present and return its index.
     *
     * @param name the {@linkplain PropertySource#getName() name of the property source}
     *             to find
     * @throws IllegalArgumentException if the named property source is not present
     */
    private int assertPresentAndGetIndex(String name) {
        int index = this.propertySourceList.indexOf(PropertySource.named(name));
        if (index == -1) {
            throw new IllegalArgumentException(String.format("PropertySource named [%s] does not exist", name));
        }
        return index;
    }

}