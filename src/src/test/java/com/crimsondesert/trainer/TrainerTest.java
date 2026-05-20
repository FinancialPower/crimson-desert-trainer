package com.crimsondesert.trainer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the Trainer class.
 * Uses Mockito to mock the MemoryManager dependency.
 */
@ExtendWith(MockitoExtension.class)
public class TrainerTest {

    @Mock
    private MemoryManager mockMemoryManager;

    private Trainer trainer;

    @BeforeEach
    public void setUp() {
        trainer = new Trainer(mockMemoryManager);
    }

    @Test
    public void testInfiniteHealthEnable() throws IOException {
        // Act
        trainer.setInfiniteHealth(true);

        // Assert
        assertTrue(trainer.isInfiniteHealthEnabled());
        verify(mockMemoryManager, times(1)).writeInt(anyLong(), eq(9999));
    }

    @Test
    public void testInfiniteHealthDisable() throws IOException {
        // Arrange
        trainer.setInfiniteHealth(true);

        // Act
        trainer.setInfiniteHealth(false);

        // Assert
        assertFalse(trainer.isInfiniteHealthEnabled());
        verify(mockMemoryManager, times(1)).writeInt(anyLong(), eq(100));
    }

    @Test
    public void testInfiniteStaminaEnable() throws IOException {
        // Act
        trainer.setInfiniteStamina(true);

        // Assert
        assertTrue(trainer.isInfiniteStaminaEnabled());
        verify(mockMemoryManager, times(1)).writeInt(anyLong(), eq(9999));
    }

    @Test
    public void testInfiniteStaminaDisable() throws IOException {
        // Arrange
        trainer.setInfiniteStamina(true);

        // Act
        trainer.setInfiniteStamina(false);

        // Assert
        assertFalse(trainer.isInfiniteStaminaEnabled());
        verify(mockMemoryManager, times(1)).writeInt(anyLong(), eq(100));
    }

    @Test
    public void testMemoryManagerPropagation() {
        // Assert that the trainer uses the provided memory manager
        assertSame(mockMemoryManager, trainer.getMemoryManager());
    }
}
