package com.axiomsl.properties.framework.resources;

import java.util.Objects;

class ResourceHolder<T extends NamedResource> {
    private final String name;
    private final T resource;

    private ResourceHolder(String name, T resource) {
        this.name = name;
        this.resource = resource;
    }

    public static <T extends NamedResource> ResourceHolder<T> ofStub(String name) {
        return new ResourceHolder<>(name, null);
    }

    public static <T extends NamedResource> ResourceHolder<T> of(T resource) {
        return new ResourceHolder<>(resource.getName(), resource);
    }

    public T getResource() {
        return resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceHolder<?> that = (ResourceHolder<?>) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
