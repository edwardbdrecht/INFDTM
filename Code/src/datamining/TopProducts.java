package datamining;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Wayne Rijsdijk
 */
public class TopProducts {
	Map<Integer, Integer> products = new TreeMap<Integer, Integer>();
	
	public void addProduct(int itemId) {
		int productCount = this.products.containsKey(itemId) ? this.products.get(itemId) : 0;
		this.products.put(itemId, ++productCount);
	}
	
	public int[] getPopularProducts() {
		return this.getPopularProducts(10);
	}
	
	public int[] getPopularProducts(int limit) {
		limit = this.products.size() < limit ? this.products.size() : limit;
		
		SortedSet<Entry<Integer,Integer>> products = entriesSortedByValues(this.products);
		Iterator li = products.iterator();
		int[] topProducts = new int[this.products.size()];
		int counter = 0;
		while(li.hasNext()) {
			Entry<Integer,Integer> e = (Entry<Integer,Integer>) li.next();
			System.out.println(e.getKey() + " =D " + e.getValue());
			topProducts[counter] = e.getKey();
			counter++;
		}
		
		//System.out.println();
		//System.arraycopy(topProducts, this.products.size()-limit, topProducts, 0, limit);
		return topProducts;
	}
	
	static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
		SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
			new Comparator<Map.Entry<K,V>>() {
				@Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
					return e1.getValue().compareTo(e2.getValue());
				}
			}
		);
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}
}