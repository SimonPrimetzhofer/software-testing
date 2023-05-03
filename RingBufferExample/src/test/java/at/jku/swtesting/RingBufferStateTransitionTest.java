package at.jku.swtesting;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RingBufferStateTransitionTest {

    @Test
    void test0Capacity() {
        assertThrows(IllegalArgumentException.class, () -> new RingBuffer<Integer>(0));
    }

    @Test
    void testEmptyPeek() {
        RingBuffer<String> ringBuffer = new RingBuffer<String>(1);
        assertThrows(RuntimeException.class, ringBuffer::peek);
    }

    @Test
    void testEmptyDequeue() {
        RingBuffer<String> ringBuffer = new RingBuffer<String>(1);
        assertThrows(RuntimeException.class, ringBuffer::dequeue);
    }

    @Test
    void testEmptyToFull() {
        RingBuffer<String> ringBuffer = new RingBuffer<String>(1);
        ringBuffer.enqueue("a");
        assertTrue(ringBuffer.isFull());
    }

    @Test
    void testFullPeek() {
        RingBuffer<String> ringBuffer = new RingBuffer<String>(1);
        ringBuffer.enqueue("a");
        ringBuffer.peek();
        assertTrue(ringBuffer.isFull());
    }

    @Test
    void testFullDequeueEmpty() {
        RingBuffer<String> ringBuffer = new RingBuffer<String>(1);
        ringBuffer.enqueue("a");
        ringBuffer.dequeue();
        assertTrue(ringBuffer.isEmpty());
    }

    @Test
    void testFullEnqueueFull() {
        RingBuffer<String> ringBuffer = new RingBuffer<String>(1);
        ringBuffer.enqueue("a");
        ringBuffer.enqueue("b");
        assertTrue(ringBuffer.isFull());
    }

    @Test
    void testEmptyEnqueueFilled() {
        RingBuffer<String> ringBuffer = new RingBuffer<String>(3);
        ringBuffer.enqueue("a");
        assertFalse(ringBuffer.isFull());
        assertFalse(ringBuffer.isEmpty());
    }

    @Test
    void testFilledPeek() {
        RingBuffer<String> ringBuffer = new RingBuffer<String>(3);
        ringBuffer.enqueue("a");
        ringBuffer.peek();
        assertFalse(ringBuffer.isFull());
        assertFalse(ringBuffer.isEmpty());
    }

    @Test
    void testFilledDequeueEmpty() {
        RingBuffer<String> ringBuffer = new RingBuffer<String>(3);
        ringBuffer.enqueue("a");
        ringBuffer.dequeue();
        assertTrue(ringBuffer.isEmpty());
    }

    @Test
    void testFilledEnqueue() {
        RingBuffer<String> ringBuffer = new RingBuffer<String>(3);
        ringBuffer.enqueue("a");
        ringBuffer.enqueue("b");
        assertFalse(ringBuffer.isEmpty());
        assertFalse(ringBuffer.isFull());
    }

    @Test
    void testFilledEnqueueFull() {
        RingBuffer<String> ringBuffer = new RingBuffer<String>(3);
        ringBuffer.enqueue("a");
        ringBuffer.enqueue("b");
        ringBuffer.enqueue("c");
        assertTrue(ringBuffer.isFull());
    }

    @Test
    void testFullDequeueFilled() {
        RingBuffer<String> ringBuffer = new RingBuffer<String>(3);
        ringBuffer.enqueue("a");
        ringBuffer.enqueue("b");
        ringBuffer.enqueue("c");
        ringBuffer.dequeue();
        assertFalse(ringBuffer.isEmpty());
        assertFalse(ringBuffer.isFull());
    }
}
