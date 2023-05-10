package at.jku.swtesting;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RingBufferModelWithAdapter implements FsmModel {

    protected int capacity;
    protected int size; // corresponds to N in RingBuffer.java, the 'counter'

    // as it does not work with the counter/size variable to check for correct data in the ring buffer as it can overflow
    // we introduced a first and last variable
    protected int first = 0;
    protected int last = 0;


    private RingBuffer<String> ringBuffer;

    public RingBufferModelWithAdapter(int capacity) {
        this.capacity = capacity;
        ringBuffer = new RingBuffer<>(capacity);
    }

    public Object getState() {
        if (size == 0) {
            return "EMPTY";
        }
        else if (size == capacity) {
            return "FULL";
        }
        else if ((size > 0) && (size < capacity)) {
            return "FILLED";
        }
        else return "ERROR_UNEXPECTED_MODEL_STATE";
    }

    public void reset(boolean testing) {
        size = 0;
        last = first = 0;
        ringBuffer = new RingBuffer<>(capacity);
    }

    public boolean enqueueGuard() {
        return capacity > 0;
    }

    @Action
    public void enqueue() {
        assertEquals(ringBuffer.size(), size);

        ringBuffer.enqueue("test #" + last);

        last++;
        if (last - capacity > first)
            first = last - capacity + 1;
        if (capacity > size)
            size++;

        assertEquals(ringBuffer.size(), size);
    }

    public boolean dequeueGuard() {
        return size > 0;
    }

    @Action
    public void dequeue() {
        assertEquals(ringBuffer.size(), size);
        String data = ringBuffer.dequeue();
        size--; first++;
        assertEquals(ringBuffer.size(), size);
        assertEquals("test #" + (first - 1), data);
    }

    public boolean peekGuard() {
        return size > 0;
    }

    @Action
    public void peek() {
        assertEquals(ringBuffer.size(), size);
        String data = ringBuffer.peek();
        assertEquals(ringBuffer.size(), size);
        assertEquals("test #" + (first), data);
    }

    @Action
    public void size() {
        int data = ringBuffer.size();
        assertEquals(size, data);
    }

    @Action
    public void isEmpty() {
        boolean empty = ringBuffer.isEmpty();
        assertEquals(size == 0, empty);
    }

    public boolean dequeueFromEmptyBufferGuard() {
        return size == 0;
    }

    @Action
    public void dequeueFromEmptyBuffer() {
        //throw new RuntimeException("Empty ring buffer.");
        RuntimeException thrown = assertThrows(RuntimeException.class, ringBuffer::dequeue);
        assertEquals("Empty ring buffer.", thrown.getMessage());
    }

    public boolean initialCapacityLessThanOneGuard() {
        return capacity < 1;
    }

    @Action
    public void initialCapacityLessThanOne() {
        //throw new RuntimeException("Initial capacity is less than one.");
        RuntimeException thrown = assertThrows(RuntimeException.class, ringBuffer::dequeue);
        assertEquals("Initial capacity is less than one.", thrown.getMessage());
    }
}
