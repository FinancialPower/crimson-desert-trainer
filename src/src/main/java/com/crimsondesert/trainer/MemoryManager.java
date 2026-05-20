package com.crimsondesert.trainer;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Manages memory read/write operations for the Crimson Desert process.
 * Uses platform-specific native calls via JNR-FFI to access process memory.
 */
public class MemoryManager {

    private static final Logger LOGGER = Logger.getLogger(MemoryManager.class.getName());
    private final ProcessHandle gameProcess;
    private final NativeLib nativeLib;

    /**
     * Constructs a MemoryManager bound to the specified game process.
     *
     * @param processId the process ID of the running Crimson Desert game
     * @throws IllegalArgumentException if processId is invalid or process not found
     */
    public MemoryManager(long processId) {
        this.gameProcess = ProcessHandle.of(processId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid process ID: " + processId));
        this.nativeLib = NativeLib.getInstance();
        LOGGER.info("MemoryManager initialized for process PID: " + processId);
    }

    /**
     * Reads a 4-byte integer from the specified memory address.
     *
     * @param address the memory address to read from
     * @return the integer value at that address
     * @throws IOException if reading fails
     */
    public int readInt(long address) throws IOException {
        if (address <= 0) {
            throw new IllegalArgumentException("Invalid memory address: " + address);
        }
        try {
            int value = nativeLib.readProcessMemory((int) gameProcess.pid(), address);
            LOGGER.fine("Read int at 0x" + Long.toHexString(address) + ": " + value);
            return value;
        } catch (Exception e) {
            throw new IOException("Failed to read memory at 0x" + Long.toHexString(address), e);
        }
    }

    /**
     * Writes a 4-byte integer to the specified memory address.
     *
     * @param address the memory address to write to
     * @param value   the integer value to write
     * @throws IOException if writing fails
     */
    public void writeInt(long address, int value) throws IOException {
        if (address <= 0) {
            throw new IllegalArgumentException("Invalid memory address: " + address);
        }
        try {
            nativeLib.writeProcessMemory((int) gameProcess.pid(), address, value);
            LOGGER.fine("Wrote int " + value + " to 0x" + Long.toHexString(address));
        } catch (Exception e) {
            throw new IOException("Failed to write memory at 0x" + Long.toHexString(address), e);
        }
    }

    /**
     * Returns the game process handle.
     *
     * @return the ProcessHandle of the game
     */
    public ProcessHandle getGameProcess() {
        return gameProcess;
    }
}
