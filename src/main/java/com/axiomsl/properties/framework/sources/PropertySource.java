package com.axiomsl.properties.framework.sources;

import com.axiomsl.properties.framework.Configuration;
import com.axiomsl.properties.framework.resources.NamedResource;

import java.util.Set;


public abstract class PropertySource<T> implements NamedResource {

    protected final String name;

    protected final T source;


    /**
     * Create a new {@code PropertySource} with the given name and source object.
     */
    public PropertySource(String name, T source) {
        this.name = name;
        this.source = source;
    }


    @SuppressWarnings("unchecked")
    public PropertySource(String name) {
        this(name, (T) new Object());
    }


    /**
     * Return the name of this {@code PropertySource}
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the underlying source object for this {@code PropertySource}.
     */
    public T getSource() {
        return this.source;
    }

    /**
     * Return whether this {@code PropertySource} contains the given name.
     * <p>This implementation simply checks for a {@code null} return value
     * from {@link #getProperty(String)}. Subclasses may wish to implement
     * a more efficient algorithm if possible.
     *
     * @param name the property name to find
     */
    public boolean containsProperty(String name) {
        return (getProperty(name) != null);
    }

    /**
     * Return the value associated with the given name,
     * or {@code null} if not found.
     *
     * @param name the property to find
     * @see Configuration#getRequiredProperty(String)
     */
    public abstract Object getProperty(String name);


    /**
     * Return all names of known properties.
     *
     * @return
     */
    public abstract Set<String> getAllPropertiesKeys();


    @Override
    public String toString() {
        return String.format("%s@%s [name='%s', properties=%s]",
                getClass().getSimpleName(), System.identityHashCode(this), this.name, this.source);
    }
}