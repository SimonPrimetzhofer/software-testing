package at.jku.swtesting;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RingBufferTest {

    private final int capacity = 5;
    private RingBuffer<Integer> ringBufferInt;

    @BeforeEach
    void setUp() {
        ringBufferInt = new RingBuffer<>(capacity);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCapacity() {
        // empty buffer length test
        assertEquals(capacity, ringBufferInt.capacity());

        // filling buffer while checking capacity
        for (int i = 0; i < capacity + 2; i++) {
            ringBufferInt.enqueue(i);
            assertEquals(capacity, ringBufferInt.capacity());
        }

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
        for (int i = 0; i < capacity + 2; i++) {
            ringBufferInt.enqueue(i);
            assertEquals(i < capacity ? i + 1 : capacity, ringBufferInt.size());
        }

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
        for (int i = 0; i < capacity + 2; i++) {
            ringBufferInt.enqueue(i);
            assertFalse(ringBufferInt.isEmpty());
        }

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


    }

    @Test
    void testEnqueue() {
    }

    @Test
    void testDequeue() {
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

		assertEquals(ringBufferInt.peek(), 1);

		// ensure that peek does not remove first element
		// by calling it a second time
		assertEquals(ringBufferInt.peek(), 1);

		// peek's value may only change when an element is dequeued
		ringBufferInt.dequeue();
		assertEquals(ringBufferInt.peek(), 2);
    }

    @Test
    void testIterator() {
    }
}
