package demoMediaPlayer;

/**
 * A simple list, partner 1 should implement this using a link structure.
 * Partner 2 should implement this using an array structure. You don't need to
 * implement this fully, or consider edge cases, or test this. At the very least
 * start each method with your particular structure. This is an example to
 * demonstrate what happens when you and a partner make changes to the same
 * file, it is not intended to evaluate your ability to write a data structure.
 */
public class MyList {
	private class Node {
		private String data;
		private Node next;
	}

	private Node head;

	public MyList() {
		this.head = null;
	}

	/**
	 * Adds the element as the first element of the list. You don't need to
	 * implement this fully, just have something that is a start using the
	 * appropriate structure.
	 */
	public void addFirst(String element) {
		// Make a new node, with the element, then adjust head.
		Node newHead = new Node();
		newHead.data = element;
		newHead.next = this.head;
		this.head = newHead;
	}

	/**
	 * Removes the first element of the list. You don't need to implement this
	 * fully, must have something that is a start using the appropriate structure.
	 */
	public void removeFirst() {
// If the head isn't null, then adjust head to point at its next.
		if (this.head != null) {
			this.head = this.head.next;
		}
	}
}