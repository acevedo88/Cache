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
	
	public void clear() {
		cache.clear();
	}
	
	public void removeObject(E obj) {
		cache.remove(obj);
	}
	
	public void addObject(E obj) {
		cache.add(obj);
	}
	
	public E getObject(E obj) {
		return obj;
	}
	
	public void addIndex(int i, E element) {
		cache.add(i, element);
	}


	public void removeLast() {
		cache.removeLast();
	}

	public void removeElement(E element) {
		cache.remove(element);
	}

	public E getElement(E element) {
		return element;
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