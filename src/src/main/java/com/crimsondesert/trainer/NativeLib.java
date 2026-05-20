package com.crimsondesert.trainer;

import jnr.ffi.LibraryLoader;
import jnr.ffi.annotations.In;
import jnr.ffi.annotations.Out;
import java.util.logging.Logger;

/**
 * Native library binding for platform-specific process memory operations.
 * Uses JNR-FFI to load the native library (e.g., kernel32 on Windows).
 */
public class NativeLib {

    private static final Logger LOGGER = Logger.getLogger(NativeLib.class.getName());
    private static NativeLib instance;
    private final NativeMemory nativeMemory;

    /**
     * Interface defining native memory functions.
     */
    public interface NativeMemory {
        /**
         * Reads a 4-byte integer from the target process memory.
         *
         * @param pid     the process ID
         * @param address the memory address
         * @return the integer read
         */
        int readProcessMemory(@In int pid, @In long address);

        /**
         * Writes a 4-byte integer to the target process memory.
         *
         * @param pid     the process ID
         * @param address the memory address
         * @param value   the value to write
         */
        void writeProcessMemory(@In int pid, @In long address, @In int value);
    }

    /**
     * Private constructor for singleton pattern.
     */
    private NativeLib() {
        // Attempt to load a platform-specific native library (simulated here)
        try {
            this.nativeMemory = LibraryLoader.create(NativeMemory.class)
                    .load("crimson_memory"); // Placeholder: would be "kernel32" on Windows
            LOGGER.info("Native library loaded successfully");
        } catch (UnsatisfiedLinkError e) {
            LOGGER.severe("Failed to load native library: " + e.getMessage());
            throw new RuntimeException("Native library not available", e);
        }
    }

    /**
     * Returns the singleton instance of NativeLib.
     *
     * @return the singleton instance
     */
    public static synchronized NativeLib getInstance() {
        if (instance == null) {
            instance = new NativeLib();
        }
        return instance;
    }

    /**
     * Delegates to native readProcessMemory.
     *
     * @param pid     the process ID
     * @param address the memory address
     * @return the integer value
     */
    public int readProcessMemory(int pid, long address) {
        return nativeMemory.readProcessMemory(pid, address);
    }

    /**
     * Delegates to native writeProcessMemory.
     *
     * @param pid     the process ID
     * @param address the memory address
     * @param value   the value to write
     */
    public void writeProcessMemory(int pid, long address, int value) {
        nativeMemory.writeProcessMemory(pid, address, value);
    }
}
