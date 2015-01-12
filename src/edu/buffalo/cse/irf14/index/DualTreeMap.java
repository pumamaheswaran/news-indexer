package edu.buffalo.cse.irf14.index;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * Code referenced from Stack Overflow http://stackoverflow.com/questions/3430170/how-to-create-a-2-way-map-in-java
 * @author Pravin
 *
 * @param <K>
 * @param <V>
 */
public class DualTreeMap<K,V> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private TreeMap<K,V> forwardMap = null;
	private TreeMap<V,K> backwardMap = null;
	
	public DualTreeMap()
	{
		forwardMap = new TreeMap<K,V>();
		backwardMap = new TreeMap<V,K>();
	}	
	
	public V getForwardReference(K key)
	{
		return forwardMap.get(key);
	}
	
	public K getBackWardReference(V key)
	{
		return backwardMap.get(key);
	}
	
	public void add(K key, V value)
	{
		forwardMap.put(key, value);
		backwardMap.put(value, key);
	}
	
	public boolean containsKey(K key)
	{
		return forwardMap.containsKey(key);
	}
	
	public boolean containsValue(V value)
	{
		return backwardMap.containsKey(value);
	}
	
	public int size()
	{
		return forwardMap.size();
	}

}
