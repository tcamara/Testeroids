package com.timcamara.testeroids.utils;

public interface StoreFactory<T> {
	// Returns a new object of the corresponding type.
	T createObject();
}