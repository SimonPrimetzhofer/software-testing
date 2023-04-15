package at.jku.swtesting;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class RingBufferMutationTest {

    @Test
    void testCreationCapacity() {
        assertDoesNotThrow(() -> new RingBuffer<>(1));
    }
}
