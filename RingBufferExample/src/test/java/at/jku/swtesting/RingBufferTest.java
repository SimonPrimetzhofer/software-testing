package at.jku.swtesting;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class RingBufferTest {

    private final int capacity = 5;
    private RingBuffer<Integer> ringBufferInt;

    @BeforeEach
    void setUp() {
        ringBufferInt = new RingBuffer<Integer>(capacity);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCapacity() {
        // empty buffer length test
        assertEquals(capacity, ringBufferInt.capacity());

        // filling buffer while checking capacity
        for (int i = 0; i < capacity; i++) {
            ringBufferInt.enqueue(i);
            assertEquals(capacity, ringBufferInt.capacity());
        }

        // overflow the buffer
        ringBufferInt.enqueue(5);
        assertEquals(capacity, ringBufferInt.capacity());
        ringBufferInt.enqueue(6);
        assertEquals(capacity, ringBufferInt.capacity());

        // dequeuing buffer while checking capacity
        for (int i = capacity; i > 0; i--) {
            ringBufferInt.dequeue();
            assertEquals(capacity, ringBufferInt.capacity());
        }
    }

    @Test
    void testSize() {
        // empty buffer size test
        assertEquals(0, ringBufferInt.size());

        // filling buffer while checking size
        for (int i = 0; i < capacity; i++) {
            ringBufferInt.enqueue(i);
            assertEquals(i + 1, ringBufferInt.size());
        }

        // overflow the buffer
        ringBufferInt.enqueue(5);
        ringBufferInt.enqueue(6);

        // dequeuing buffer while checking size
        for (int i = capacity; i > 0; i--) {
            ringBufferInt.dequeue();
            assertEquals(i - 1, ringBufferInt.size());
        }
    }

    @Test
    void testIsEmpty() {
        // empty buffer isEmpty test
        assertTrue(ringBufferInt.isEmpty());

        // filling buffer while checking isEmpty
        for (int i = 0; i < capacity; i++) {
            ringBufferInt.enqueue(i);
            assertFalse(ringBufferInt.isEmpty());
        }

        // overflow the buffer
        ringBufferInt.enqueue(5);
        assertFalse(ringBufferInt.isEmpty());
        ringBufferInt.enqueue(6);
        assertFalse(ringBufferInt.isEmpty());

        // dequeuing buffer while checking isEmpty
        for (int i = capacity; i > 0; i--) {
            ringBufferInt.dequeue();
            assertEquals(i == 1, ringBufferInt.isEmpty());
        }
    }

    @Test
    void testCreateEmptyBuffer() {
        assertThrows(IllegalArgumentException.class, () -> new RingBuffer<Integer>(0));
    }

    @Test
    void testIsFull() {
        assertFalse(ringBufferInt.isFull());
		for (int i = 0; i < capacity; i++) {
			ringBufferInt.enqueue(i);
		}
		assertTrue(ringBufferInt.isFull());

		ringBufferInt.enqueue(ringBufferInt.capacity() + 1);
		assertTrue(ringBufferInt.isFull());

		ringBufferInt.dequeue();
		assertFalse(ringBufferInt.isFull());
    }

    @Test
    void testEnqueue() {
        ringBufferInt.enqueue(1);
        assertEquals(1, ringBufferInt.size());

        ringBufferInt.enqueue(2);
        assertEquals(2, ringBufferInt.size());

        ringBufferInt.enqueue(3);
        assertEquals(3, ringBufferInt.size());

        ringBufferInt.enqueue(4);
        assertEquals(4, ringBufferInt.size());

        ringBufferInt.enqueue(5);
        assertEquals(5, ringBufferInt.size());

        // now, buffer should be full
        assertTrue(ringBufferInt.isFull());

        // first element is still 1
        assertEquals(1, ringBufferInt.peek());

        // add another element to full buffer
        ringBufferInt.enqueue(6);

        // buffer is still full but an element has been overwritten
        assertTrue(ringBufferInt.isFull());

        // new first element is 2 as 6 is the new last element
        assertEquals(2, ringBufferInt.peek());
    }

    @Test
    void testDequeue() {
        // add initial elements
        ringBufferInt.enqueue(1);
        ringBufferInt.enqueue(2);
        ringBufferInt.enqueue(3);
        ringBufferInt.enqueue(4);
        ringBufferInt.enqueue(5);

        assertEquals(5, ringBufferInt.size());

        // dequeue on buffer with elements shall not throw an exception
        assertDoesNotThrow(ringBufferInt::dequeue);

        // dequeue elements on by one and check size
        assertEquals(2, ringBufferInt.dequeue());
        assertEquals(3, ringBufferInt.size());

        assertEquals(3, ringBufferInt.dequeue());
        assertEquals(2, ringBufferInt.size());

        assertEquals(4, ringBufferInt.dequeue());
        assertEquals(1, ringBufferInt.size());

        assertEquals(5, ringBufferInt.dequeue());
        assertEquals(0, ringBufferInt.size());

        // buffer should have size 0 after removing all elements
        assertEquals(0, ringBufferInt.size());
    }

	@Test
	void testEmptyDequeue() {
		assertThrows(RuntimeException.class, () -> new RingBuffer<Integer>(1).dequeue());
	}

    @Test
    void testEmptyPeek() {
        assertThrows(RuntimeException.class, () -> new RingBuffer<Integer>(1).peek());
    }

    @Test
    void testPeek() {
		ringBufferInt.enqueue(1);
		ringBufferInt.enqueue(2);
		ringBufferInt.enqueue(3);

		// valid RingBuffer shall not throw exception when calling peek
		assertDoesNotThrow(ringBufferInt::peek);

		assertEquals(1, ringBufferInt.peek());

		// ensure that peek does not remove first element
		// by calling it a second time
		assertEquals(1, ringBufferInt.peek());

		// peek's value may only change when an element is dequeued
		ringBufferInt.dequeue();
		assertEquals(2, ringBufferInt.peek());
    }

    @Test
    void testEmptyIterator() {
        // Test with empty RingBuffer:
        Iterator<Integer> iter = ringBufferInt.iterator();

        // Iterator should not have any items
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, iter::next);
    }

    @Test
    void testPartiallyFilledIterator() {
        // Test with not full RingBuffer:
        ringBufferInt.enqueue(1);
        ringBufferInt.enqueue(2);
        ringBufferInt.enqueue(3);

        // Get the Iterator
        Iterator<Integer> iter = ringBufferInt.iterator();

        // Iterate through Buffer
        assertTrue(iter.hasNext());
        assertEquals(1, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(2, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(3, iter.next());
        // There should not be a next element because "3" is the last item
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, iter::next);
    }
    @Test
    void testFilledIterator() {
        // Test with filled RingBuffer:
        // fill the RingBuffer
        ringBufferInt.enqueue(1);
        ringBufferInt.enqueue(2);
        ringBufferInt.enqueue(3);
        ringBufferInt.enqueue(4);
        ringBufferInt.enqueue(5);

        // Get the Iterator
        Iterator<Integer> iter = ringBufferInt.iterator();

        // Iterate through Buffer
        assertTrue(iter.hasNext());
        assertEquals(1, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(2, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(3, iter.next());

        assertTrue(iter.hasNext());
        assertEquals(4, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(5, iter.next());
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, iter::next);
    }

    @Test
    void testDequeuedIterator() {
        // fill the RingBuffer
        ringBufferInt.enqueue(1);
        ringBufferInt.enqueue(2);
        ringBufferInt.enqueue(3);
        ringBufferInt.enqueue(4);
        ringBufferInt.enqueue(5);
        // Test Iterator with a Buffer after a Item is dequeued:
        ringBufferInt.dequeue();

        // Get the Iterator
        Iterator<Integer> iter = ringBufferInt.iterator();

        // Iterate through Buffer
        assertTrue(iter.hasNext());
        assertEquals(2, iter.next());       // <- Bug: Iterator returns null except of 2
        // FIX: return array position first + index,
        // Additionally use % a.length to stay in bounds
        assertTrue(iter.hasNext());
        assertEquals(3, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(4, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(5, iter.next());
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, iter::next);
    }

    @Test
    void testOverwrittenRingBuffer() {
        // Test iterator when last element is not end of array:
        // fill the RingBuffer
        ringBufferInt.enqueue(1);
        ringBufferInt.enqueue(2);
        ringBufferInt.enqueue(3);
        ringBufferInt.enqueue(4);
        ringBufferInt.enqueue(5);

        ringBufferInt.dequeue();

        ringBufferInt.enqueue(6);
        ringBufferInt.enqueue(7);
        ringBufferInt.enqueue(8);

        // Get Iterator
        Iterator<Integer> iter = ringBufferInt.iterator();


        // Iterate through Buffer
        assertTrue(iter.hasNext());
        assertEquals(4, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(5, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(6, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(7, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(8, iter.next());
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, iter::next);
    }

    @Test
    void testIteratorRemove() {
        // Test with empty RingBuffer:
        Iterator<Integer> iter = ringBufferInt.iterator();

        // Test remove(), expecting an Exception because it is not implemented:
        assertThrows(UnsupportedOperationException.class, iter::remove);
    }
}
