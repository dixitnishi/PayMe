package com.walletapplication.payme.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.walletapplication.payme.model.entity.DatabaseSequence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SequenceGenerator.class})
@ExtendWith(SpringExtension.class)
class SequenceGeneratorTest {
    @MockBean
    private MongoOperations mongoOperations;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Test
    void testGenerateSequence() {
        // Arrange
        DatabaseSequence databaseSequence = new DatabaseSequence();
        databaseSequence.setId("42");
        databaseSequence.setSeq(1L);
        when(mongoOperations.findAndModify(Mockito.<Query>any(), Mockito.<UpdateDefinition>any(),
                Mockito.<FindAndModifyOptions>any(), Mockito.<Class<DatabaseSequence>>any())).thenReturn(databaseSequence);

        // Act
        long actualGenerateSequenceResult = sequenceGenerator.generateSequence("Seq Name");

        // Assert
        verify(mongoOperations).findAndModify(Mockito.<Query>any(), Mockito.<UpdateDefinition>any(),
                Mockito.<FindAndModifyOptions>any(), Mockito.<Class<DatabaseSequence>>any());
        assertEquals(1L, actualGenerateSequenceResult);
    }
}
