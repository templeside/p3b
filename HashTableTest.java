//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION ////////////////////
//
// Title: P3
// Files: HashTable, HashTableTest
// Course:CS400, Spring2019        
//
// Author: Chanwoong Jhon
// Email:  cjhon@wisc.edu
// Lecturer's Name: Andrew L KUEMMEL (004)
// Due Date: 3/14/2019
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   X  Write-up states that pair programming is allowed for this assignment.
//   X  We have both read and understand the course Pair Programming Policy.
//   X  We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates, 
// strangers, and others do.  If you received no outside help from either type
//  of source, then please explicitly indicate NONE.
//
// Persons: None
// Online Sources: None
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import static org.junit.jupiter.api.Assertions.*; // org.junit.Assert.*; 
import org.junit.jupiter.api.Assertions;
 
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
 
import java.util.Random;



/** TODO: add class header comments here*/
public class HashTableTest{

    @Before
    public void setUp() throws Exception {

    }

    // TODO: add code that runs after each test method
    @After
    public void tearDown() throws Exception {

    }
    
    /** 
     * Tests that a HashTable returns an integer code
     * indicating which collision resolution strategy 
     * is used.
     * REFER TO HashTableADT for valid collision scheme codes.
     */
    @Test
    public void test000_collision_scheme() {
        HashTableADT htIntegerKey = new HashTable<Integer,String>();
        int scheme = htIntegerKey.getCollisionResolution();
        if (scheme < 1 || scheme > 9) 
            fail("collision resolution must be indicated with 1-9");
    }
        
    /**
     * Tests that insert(null,null) throws IllegalNullKeyException
     */
    @Test
    public void test001_IllegalNullKey() {
        try {
            HashTableADT htIntegerKey = new HashTable<Integer,String>();
            htIntegerKey.insert(null, null);
            fail("should not be able to insert null key");
        } 
        catch (IllegalNullKeyException e) { /* expected */ } 
        catch (Exception e) {
            fail("insert null key should not throw exception "+e.getClass().getName());
        }
    }
    
    /**
     * Tests that insert duplicate key throws DuplicateKeyException
     */
    @Test
    public void test002_DuplicateKeyException() {
        try {
            HashTableADT htIntegerKey = new HashTable<Integer,String>(5,0.7);
            htIntegerKey.insert(1, "HI");
            htIntegerKey.insert(1, "HI");
            fail("should not be able to duplicate key");
        } 
        catch (DuplicateKeyException e) {/* expected */ 
        } 
        catch (Exception e) {
            fail("insert null key should not throw exception "+e.getClass().getName());
        }
    }
    
    /**
     * Tests that delete methods works.
     */
    @Test
    public void test003_RemoveKey() {
    		try {
            HashTableADT htIntegerKey = new HashTable<Integer,String>(5,0.7);
            htIntegerKey.insert(1, "HI");
            htIntegerKey.insert(2, "Nice to meet you");
            
            htIntegerKey.remove(1);
            if (htIntegerKey.get(1)==null)
            		return;
            
    		}
    		catch (DuplicateKeyException e) {}
    		catch(KeyNotFoundException e) {}
    		catch(IllegalNullKeyException e) {
    			fail("insert null key should not throw exception ");}
            
        }
    
    /**
     * Tests that get methods works.
     */
    @Test
    public void test004_getKey() {
       try {
            HashTableADT htIntegerKey = new HashTable<Integer,String>(5,0.7);
            htIntegerKey.insert(1, "HI");
            htIntegerKey.insert(2, "Nice to meet you");
            String result = (String)htIntegerKey.get(1);
            if (result.equals("HI")){}
            	
       }catch (DuplicateKeyException e) {
    	   fail("The ADT should return 'HI'");}
       
       catch(KeyNotFoundException e) {
    	   fail("The ADT should return 'HI'");}
       catch (IllegalNullKeyException e) {
    	   fail("The ADT should return 'HI'");}
        }
    /**
     * Tests that hashTable resize and results the size as 2n+1.
     * @throws KeyNotFoundException 
     */
    @Test
    public void test005_rehash() throws KeyNotFoundException {
       try {
            HashTableADT htIntegerKey = new HashTable<Integer,String>(3,0.9);
            htIntegerKey.insert(1, "HI");
            htIntegerKey.insert(2, "nice");
            htIntegerKey.insert(3, "3");
            htIntegerKey.insert(4, "4");
            htIntegerKey.insert(5, "5");
            if (htIntegerKey.getCapacity() == 7){}
            else
            	fail("The ADT should return 7");
            	
       }catch (DuplicateKeyException e) {
    	   fail("The ADT should return 7");}
       
       catch (IllegalNullKeyException e) {
    	   fail("The ADT should return 7");}
        }
    
    /**
     * Tests that hashTable inserts well.
     * @throws KeyNotFoundException
     */
    @Test
    public void test005_insert() throws KeyNotFoundException {
        try {
             HashTableADT htIntegerKey = new HashTable<Integer,String>(5,0.5);
             htIntegerKey.insert(0, "HI");
             htIntegerKey.insert(2, "nice");
             htIntegerKey.insert(3, "3");

             String result = "";
             result = result + htIntegerKey.get(0).toString();
             result = result + htIntegerKey.get(2).toString();
             result = result + htIntegerKey.get(3).toString();
             if (result.equals("HInice3")){}
             else
             	fail("The ADT should return 'HInice3'");
             	
        }catch (DuplicateKeyException e) {
     	   fail("The ADT should return 'HInice3'");}
        
        catch (IllegalNullKeyException e) {
     	   fail("The ADT should return 'HInice3'");}
         }
     

    
    
}
