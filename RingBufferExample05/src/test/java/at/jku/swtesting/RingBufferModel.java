package at.jku.swtesting;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class RingBufferModel implements FsmModel {

    protected int capacity;
    protected int size; // corresponds to N in RingBuffer.java

    public RingBufferModel(int capacity) {
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
        if (capacity > size)
            size++;
    }

    public boolean dequeueGuard() {
        return size > 0;
    }

    @Action
    public void dequeue() {
        size--;
    }

    public boolean peekGuard() {
        return size > 0;
    }

    @Action
    public void peek() {

    }

    public boolean dequeueFromEmptyBufferGuard() {
        return size == 0;
    }

    @Action
    public void dequeueFromEmptyBuffer() {
        //throw new RuntimeException("Empty ring buffer.");
    }

    public boolean initialCapacityLessThanOneGuard() {
        return capacity < 1;
    }

    @Action
    public void initialCapacityLessThanOne() {
        //throw new RuntimeException("Initial capacity is less than one.");
    }
}
