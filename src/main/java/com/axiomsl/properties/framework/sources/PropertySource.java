package com.axiomsl.properties.framework.sources;

import com.axiomsl.properties.framework.Configuration;

import java.util.List;
import java.util.Set;


public abstract class PropertySource<T> {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o instanceof PropertySource) return false;

        PropertySource<?> that = (PropertySource<?>) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.format("%s@%s [name='%s', properties=%s]",
                getClass().getSimpleName(), System.identityHashCode(this), this.name, this.source);
    }


    /**
     * Return a {@code PropertySource} implementation intended for collection comparison purposes only.
     * <p>Primarily for internal use, but given a collection of {@code PropertySource} objects, may be
     * used as follows:
     * <pre class="code">
     * {@code List<PropertySource<?>> sources = new ArrayList<PropertySource<?>>();
     * sources.add(new MapPropertySource("sourceA", mapA));
     * sources.add(new MapPropertySource("sourceB", mapB));
     * assert sources.contains(PropertySource.named("sourceA"));
     * assert sources.contains(PropertySource.named("sourceB"));
     * assert !sources.contains(PropertySource.named("sourceC"));
     * }</pre>
     * The returned {@code PropertySource} will throw {@code UnsupportedOperationException}
     * if any methods other than {@code equals(Object)}, {@code hashCode()}, and {@code toString()}
     * are called.
     *
     * @param name the name of the comparison {@code PropertySource} to be created and returned.
     */
    public static PropertySource<?> named(String name) {
        return new ComparisonPropertySource(name);
    }


    /**
     * {@code PropertySource} to be used as a placeholder in cases where an actual
     * property source cannot be eagerly initialized at application context
     * creation time.  For example, a {@code ServletContext}-based property source
     * must wait until the {@code ServletContext} object is available to its enclosing
     * {@code ApplicationContext}.  In such cases, a stub should be used to hold the
     * intended default position/order of the property source, then be replaced
     * during context refresh.
     */
    public static class StubPropertySource extends PropertySource<Object> {

        public StubPropertySource(String name) {
            super(name, new Object());
        }

        /**
         * Always returns {@code null}.
         */
        @Override
        public String getProperty(String name) {
            return null;
        }

        @Override
        public Set<String> getAllPropertiesKeys() {
            return null;
        }
    }


    /**
     * @see PropertySource#named(String)
     */
    static class ComparisonPropertySource extends StubPropertySource {

        private static final String USAGE_ERROR =
                "ComparisonPropertySource instances are for use with collection comparison only";

        public ComparisonPropertySource(String name) {
            super(name);
        }

        @Override
        public Object getSource() {
            throw new UnsupportedOperationException(USAGE_ERROR);
        }

        @Override
        public boolean containsProperty(String name) {
            throw new UnsupportedOperationException(USAGE_ERROR);
        }

        @Override
        public String getProperty(String name) {
            throw new UnsupportedOperationException(USAGE_ERROR);
        }

        @Override
        public String toString() {
            return String.format("%s [name='%s']", getClass().getSimpleName(), this.name);
        }
    }

}