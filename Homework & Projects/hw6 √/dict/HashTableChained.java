/* HashTableChained.java */

package dict;

import list.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
	protected List[] table;
	protected int[] collisions;
	


  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    // Your solution here.
	int buckets = 0;
	for (int i = sizeEstimate * 5 / 4; ; i++) {
		if (isPrimer(i)) {
			buckets = i;
			break;
		}
	}
	table = new SList[buckets];
	collisions = new int[buckets];
  }
  
  private boolean isPrimer(int num) {
	if (num < 2) return false;
	
	for (int i = 2; i <= Math.sqrt(num); i++) {
		if (num % i == 0) {
			return false;
		}
	}
	return true;
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    // Your solution here.
	table = new SList[101];
	collisions = new int[101];
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    // Replace the following line with your solution.
	int compCode = code % table.length;
	if (compCode < 0) {
		compCode += table.length;
	}
    return compCode;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
	int sizeOfEntries = 0;
	for (int i = 0; i < table.length; i++) {
		sizeOfEntries += table[i].length();
	}
    return sizeOfEntries;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    // Replace the following line with your solution.
    return table.length == 0;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    // Replace the following line with your solution.
	Entry entry = new Entry();
	entry.key = key;
	entry.value = value;
	int compCode = compFunction(key.hashCode());
	if (table[compCode] == null) {
		List list = new SList();
		list.insertFront(entry);
		table[compCode] = list;
	} else {
		table[compCode].insertFront(entry);
	}
	collisions[compCode]++;
    return entry;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    // Replace the following line with your solution.
	int compCode = compFunction(key.hashCode());
	if (table[compCode] == null) return null;
	
	Entry entry = new Entry();
	try {
		ListNode node = table[compCode].front();
		entry = (Entry) node.item();
	} catch (InvalidNodeException ine) {
		System.err.println("HashTableChained's find() failed!");
	}
	return entry;
	
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    // Replace the following line with your solution.
	int compCode = compFunction(key.hashCode());
	if (table[compCode] == null) return null;
	
	Entry entry = new Entry();
	try {
		ListNode node = table[compCode].front();
		entry = (Entry) node.item();
		node.remove();
	} catch (InvalidNodeException ine) {
		System.err.println("HashTableChained's remove() failed!");
	}
	collisions[compCode]--;
	return entry;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
	for (int i = 0; i < table.length; i++) {
		table[i] = null;
	}
  }
  
  /**
   *  counts the number of collisions and prints a histograph of the number of entries in each bucket.
   */
  public void collisionsInfo() {
    // Your solution here.
	int collisionsNum = 0;
	for (int i = 0; i < collisions.length; i++) {
		System.out.print(collisions[i] + " ");
		if (collisions[i] > 1) {
			collisionsNum++;
		}
	}
	System.out.println();
	System.out.print("In this HashTableChained, collisions number is  " + collisionsNum + " ,collision rate is " + 
		collisionsNum + "/" + collisions.length + "(about " + (collisionsNum + 0.0) / collisions.length + ").");
  }

}
