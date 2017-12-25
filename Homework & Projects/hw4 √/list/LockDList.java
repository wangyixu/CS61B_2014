package list;

/**
 *  A LockDListNode is a node in a DList (doubly-linked list).
 */

public class LockDList extends DList {
	

	
  /**
   *  lockNode() permanently locks node.
   *  Performance:  runs in O(1) time.
   */
	public void lockNode(DListNode node) {
		if (node == null) return;
		((LockDListNode)node).isLocked = true;
	}
	

	protected DListNode newNode(Object item, DListNode prev, DListNode next) {
		return new LockDListNode(item, prev, next);
    }
	
	
    public void remove(DListNode node) {
		if (((LockDListNode)node).isLocked) return;
		super.remove(node);
    }
}