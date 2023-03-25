package at.jku.swtesting;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RingBufferTest {

	private RingBuffer<Integer> ringBufferInt;

	private final int capacity = 5;

	@BeforeEach
	void setUp() {
		ringBufferInt = new RingBuffer<>(capacity);
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void capacity() {
		// empty buffer length test
		assertEquals(capacity, ringBufferInt.capacity());

		// filling buffer while checking capacity
		for (int i = 0; i < capacity + 2; i++) {
			ringBufferInt.enqueue(i);
			assertEquals(capacity, ringBufferInt.capacity());
		}

		// dequeuing buffer while checking capacity
		for(int i = capacity; i > 0; i--) {
			ringBufferInt.dequeue();
			assertEquals(capacity, ringBufferInt.capacity());
		}
	}

	@Test
	void size() {
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
	void isEmpty() {
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
	void createEmptyBuffer() {
		assertThrows(IllegalArgumentException.class, () -> new RingBuffer<Integer>(0));
	}

	@Test
	void isFull() {
	}

	@Test
	void enqueue() {
	}

	@Test
	void dequeue() {
	}

	@Test
	void peek() {
	}

	@Test
	void iterator() {
	}
}
