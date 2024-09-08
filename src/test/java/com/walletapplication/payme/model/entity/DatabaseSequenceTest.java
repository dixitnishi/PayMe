package com.walletapplication.payme.model.entity;

import com.walletapplication.payme.model.entity.DatabaseSequence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseSequenceTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        DatabaseSequence databaseSequence = new DatabaseSequence();
        String id = "123";
        long seq = 456;

        // Act
        databaseSequence.setId(id);
        databaseSequence.setSeq(seq);

        // Assert
        assertEquals(id, databaseSequence.getId());
        assertEquals(seq, databaseSequence.getSeq());
    }
}
