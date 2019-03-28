import java.util.ArrayList;
import java.util.LinkedList;

// TODO: comment and complete your HashTableADT implementation
// DO ADD UNIMPLEMENTED PUBLIC METHODS FROM HashTableADT and DataStructureADT TO YOUR CLASS
// DO IMPLEMENT THE PUBLIC CONSTRUCTORS STARTED
// DO NOT ADD OTHER PUBLIC MEMBERS (fields or methods) TO YOUR CLASS
//
// TODO: implement all required methods
//
// TODO: describe the collision resolution scheme you have chosen
// identify your scheme as open addressing or bucket
//
// TODO: explain your hashing algorithm here 
// NOTE: you are not required to design your own algorithm for hashing,
//       since you do not know the type for K,
//       you must use the hashCode provided by the <K key> object
//       and one of the techniques presented in lecture
//
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {
	/**
	 * Inner class of the linked list to store key and value.
	 * @author Chanwoong Jhon
	 * @param <K> 	key of the insert
	 * @param <V>	value of the insert
	 */
	private class Pair<K,V>{
		K key;
		V value;
		private Pair(K key, V value) {
			 this.key = key;
			 this.value = value;
		 }
		
		/**
		 * return the key that the pair node has inside
		 * @return key inside of the Pair node
		 */
		private K getKey() {
			return key;
		}
		
		/**
		 * return the value that the pair node has inside
		 * @return value inside of the Pair node
		 */
		private V getValue() {
			return value;
		}
	}
	private ArrayList<LinkedList <Pair<K,V>>> hashTable;
	private double loadFactorThreshold;
	private double loadFactor;
	private int capacity;
	private int numKeys;
	
	// TODO: ADD and comment DATA FIELD MEMBERS needed for your implementation
	
	// TODO: comment and complete a default no-arg constructor

	/**
	 * Constructer method of Hash Table
	 */
	public HashTable() {
	}
	
	
	// TODO: comment and complete a constructor that accepts 
	// initial capacity and load factor threshold
	// threshold is the load factor that causes a resize and rehash
	/**
	 * Constructer method of Hash Table. Generates a hash table
	 * @param initialCapacity	table size of the Hash table
	 * @param loadFactorThreshold	if the hash table has bigger percentage, expand hash table size
	 */
	public HashTable(int initialCapacity, double loadFactorThreshold) {
		int i;
		capacity =  initialCapacity;
		numKeys = 0;
		loadFactor = numKeys()/getCapacity(); 
		this.loadFactorThreshold = loadFactorThreshold;
		hashTable = new ArrayList<LinkedList <Pair<K,V>>>(capacity);

		for(i=0; i<capacity; i++) {
			hashTable.add(i, new LinkedList<Pair<K,V>>());
		}
	}


	/**
	 * Add the key,value pair to the data structure and increase the number of keys.
	 * @param key that user want to insert as a value
	 * @param value that user want to insert as a value
	 */
	@Override
	public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {		
		if(key ==null) {
			throw new IllegalNullKeyException();
		}
		
	    // If key is already in data structure, throw DuplicateKeyException();
		int keyHashCode = Math.abs(key.hashCode())%capacity;
		
		LinkedList<Pair<K,V>> currentLinkedList = hashTable.get(keyHashCode);
		Pair<K,V> pairNode = new Pair(key, value);
		
		if(linkedListContainsKey(key, keyHashCode))
			throw new DuplicateKeyException();
				
		hashTable.get(keyHashCode).add(pairNode);
		numKeys ++;
		double numKey = (double)numKeys;
		loadFactor = numKey/capacity;
				
		if (getLoadFactor()>=getLoadFactorThreshold()) {
			expanedTable(capacity);
		}
		
	}
	


	/**
	 * expand the table size and add the previous data to the new data list.
	 * @throws DuplicateKeyException 	if the key is duplicated
	 * @throws IllegalNullKeyException	if the key is null
	 */
	private void expanedTable(int capacity) throws IllegalNullKeyException, DuplicateKeyException {
		int i;
		int j;
		ArrayList<Pair<K,V>>tempTable;
		tempTable = new ArrayList<Pair<K,V>>(capacity*2+1);
		
		for(i=0; i<hashTable.size();i++) {
			for(j=0;j<hashTable.get(i).size();j++) {
				if (hashTable.get(i).get(j) != null)
					tempTable.add(new Pair<K,V>(hashTable.get(i).get(j).getKey(), hashTable.get(i).get(j).getValue()));
			}
		}
		
		hashTable.clear();
		this.capacity =  capacity*2+1;
		numKeys = 0;
		
		hashTable = new ArrayList<LinkedList <Pair<K,V>>>(this.capacity);

		for(i=0; i<this.capacity; i++) {
			hashTable.add(i, new LinkedList<Pair<K,V>>());
		}
		
		loadFactor = numKeys()/getCapacity();
		for(i=0; i<tempTable.size();i++) {
			insert(tempTable.get(i).getKey(),tempTable.get(i).getValue());
		}
	}

	/**
	 * Check does the ADT has the key
	 * @param key	key of the node
	 * @param hashCode	hashcode made of key that i want to find
	 * @return true when the key exists, false when the key does not exit
	 */
	private boolean linkedListContainsKey(K key, int hashCode) {
		LinkedList<Pair<K, V>> linkedListIterator = hashTable.get(Math.abs(hashCode % capacity));
		int i;
		//if null
		if (linkedListIterator ==null)
				return false;
		
		else {			
			for( i=0; i< linkedListIterator.size(); i++) {			
				if(linkedListIterator.get(i).getKey().equals(key))
					return true;
			}
			return false;
		}
	}

	/**
	 * remove a node from ADT
	 * @param key	key of the node to remove
	 * @throws IllegalNullKeyException if the key is null
	 * @return true when the remove is processed. false when is not processed.
	 */
	@Override
	public boolean remove(K key) throws IllegalNullKeyException {
		int i;
		// If key is null, throw IllegalNullKeyException
		if (key == null)
			throw new IllegalNullKeyException();
		
		int hashCode = Math.abs(key.hashCode());
		
	    // If key is found, 
	    //    remove the key,value pair from the data structure
	    //    decrease number of keys.
	    //    return true
		if (linkedListContainsKey(key, hashCode)) {
			hashTable.get(hashCode).clear();
			for (i=0; i<hashTable.get(hashCode).size();i++) {
				if(hashTable.get(hashCode).get(i).getKey().equals(key)) {
					hashTable.get(hashCode).remove(i);
				}
			}
			numKeys--;
			return true;
		}			
	    // If key is not found, return false
		return false;
	}

	/**
	 * get the value of the node
	 * @param key the key that want to get the value
	 * @Returns V the value associated with the specified key
	 */
	@Override
	public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
		int i;
		// If key is null, throw IllegalNullKeyException 
		
		if (key == null)
			throw new IllegalNullKeyException();

	    // Does not remove key or decrease number of keys
		int hashCode = Math.abs(key.hashCode()%capacity);

		// Returns the value associated with the specified key
		if (linkedListContainsKey(key, hashCode)) {
			for(i=0; i<hashTable.get(hashCode).size();i++) {
				if(hashTable.get(hashCode).get(i).getKey().equals(key))
					return hashTable.get(hashCode).get(i).getValue();
				}
			}
		//If key is not found, throw KeyNotFoundException().
		throw new KeyNotFoundException();
		}

	
	/**
	 * return number of nodes that hash table has.
	 * @return numKeys number of the nodes that hash table has
	 */
	@Override
	public int numKeys() {
		return numKeys;
	}

	/**
	 * return the threshold that user input
	 * @return loadFactorThreshold to user
	 * @see HashTableADT#getLoadFactorThreshold()
	 */
	@Override
	public double getLoadFactorThreshold() {
		return loadFactorThreshold;
	}

	/** return loadFactor of the hash table
	 * @return loadFactor numberOfNodes/tableSize
	 * @see HashTableADT#getLoadFactor()
	 */
	@Override
	public double getLoadFactor() {
		return loadFactor;
	}
	
	/**
	 * return the capacity of the hash table
	 * @return capacity	which is the size of the hash table
	 */
	@Override
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Linked list is the way to solve collision
	 * @return 5. I used Chained bucket
	 */
	@Override
	public int getCollisionResolution() {
		return 5;
	}







		
}
