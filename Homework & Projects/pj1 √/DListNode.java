/* DListNode.java */

/**
 *  DListNode is a class used internally by the DList class.  An DList object
 *  is a doubly-linked list, and an DListNode is a node of a doubly-linked
 *  list.  Each DListNode has two references:  one to an object, and one to
 *  the next node in the list.
 *
 *  @author Kathy Yelick and Jonathan Shewchuk
 */

class DListNode {
  int[] info;
  DListNode prev;
  DListNode next;


  /**
   *  DListNode() (with two parameters) constructs a list node referencing the
   *  item "obj", whose next list node is to be "next".
   */

  DListNode(int[] info, DListNode prev, DListNode next) {
    this.info = info;
	this.prev = prev;
    this.next = next;
  }

  /**
   *  DListNode() (with one parameter) constructs a list node referencing the
   *  item "obj".
   */

  DListNode(int[] info) {
    this(info, null, null);
  }
}
