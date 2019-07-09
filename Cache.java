/*
 * @author alex acevedo
 * 
 * CS 321
 * Instructor Yeh
 * 
 * This class help the Test main class by creating methods that
 * will be used throughout the Test code.
 * 
 */

import java.util.LinkedList;

public class Cache<E> {
	private LinkedList<E> cache;
	private int inputSize;
	private boolean hit;

	public Cache(int inputSize) {
		this.inputSize = inputSize;
		cache = new LinkedList<>();
	}
	
	public int cacheSize() {
		return inputSize;
	}

	public boolean contains(E element) {
		if (cache.contains(element)) {
			hit = true;
		} else {
			hit = false;
		}
		return hit;
	}

	public void add(E element) {
		
		if (cache.size() < inputSize) {
			
			if (cache.contains(element)) {
				cache.remove(element);
				cache.addFirst(element);
				
			} else {
				cache.addFirst(element);
			}
		}
		
		else {
			if (cache.contains(element)) {
				cache.remove(element);
				cache.addFirst(element);
			} else {
				cache.removeLast();
				cache.addFirst(element);
			}
		}
	}

}