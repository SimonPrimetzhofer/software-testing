package at.jku.swtesting;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RingBufferModelWithAdapter implements FsmModel {

    protected int capacity;
    protected int size; // corresponds to N in RingBuffer.java

    // TODO think of a way to verify the inserted data, "test # size" does not work as the buffer can overflow, thus maybe something with first or last or so?
    // maybe a second variable of actual added stuff, size2

    private RingBuffer<String> ringBuffer = new RingBuffer<>(capacity);

    public RingBufferModelWithAdapter(int capacity) {
        this.capacity = capacity;
    }

    public Object getState() {
        if (size == 0) {
            return "EMPTY";
        } else if (size == capacity) {
            return "FULL";
        } else if ((size > 0) && (size < capacity)) {
            return "FILLED";
        } else return "ERROR_UNEXPECTED_MODEL_STATE";
    }

    public void reset(boolean testing) {
        size = 0;
    }

    public boolean enqueueGuard() {
        return capacity > 0;
    }

    @Action
    public void enqueue() {
        ringBuffer.enqueue("test #" + size);
        if (capacity > size)
            size++;
    }

    public boolean dequeueGuard() {
        return size > 0;
    }

    @Action
    public void dequeue() {
        String data = ringBuffer.dequeue();
        size--;
        assertEquals("test #" + size, data);
    }

    public boolean peekGuard() {
        return size > 0;
    }

    @Action
    public void peek() {
        String data = ringBuffer.peek();
        assertEquals("test #" + size, data);
    }

    public boolean dequeueFromEmptyBufferGuard() {
        return size == 0;
    }

    @Action
    public void dequeueFromEmptyBuffer() {
        //throw new RuntimeException("Empty ring buffer.");
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            ringBuffer.dequeue();
        });
        assertEquals("Empty ring buffer.", thrown.getMessage());
    }

    public boolean initialCapacityLessThanOneGuard() {
        return capacity < 1;
    }

    @Action
    public void initialCapacityLessThanOne() {
        //throw new RuntimeException("Initial capacity is less than one.");
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            ringBuffer.dequeue();
        });
        assertEquals("Initial capacity is less than one.", thrown.getMessage());
    }
}
