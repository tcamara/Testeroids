package com.timcamara.testeroids.utils;

import java.util.ArrayList;

public class Store<T> {
	ArrayList<T>    free = new ArrayList<T>();
	StoreFactory<T> storeFactory;

	public Store(StoreFactory<T> storeFactory) {
		this.storeFactory = storeFactory;
	}
	
	// Returns an object from the Store, it reuses an object from the free collection if there is one or creates a new object otherwise.
	public T get() {
		if (free.isEmpty()) {
			return newObject();
		}
		else {
			return reuseObject();
		}
	}
	
	// Frees the object by returning it to the free objects collection.
	public void free(T t) {
		if (free.contains(t)) {
			return;
		}
		
		free.add(t);
	}
	
	// Returns the size of the created objects collection.
	public int size() {
		return free.size();
	}
	
	// Creates the specified number of objects and adds them to the free collection in the store.
	public void preCreate(int count) {
		for (int i = 0; i < count; i++)
			free(storeFactory.createObject());
	}
	
	protected T reuseObject() {
		T t = free.remove(free.size() - 1);
		return t;
	}
	
	protected T newObject() {
		T t = storeFactory.createObject();
		return t;
	}
}