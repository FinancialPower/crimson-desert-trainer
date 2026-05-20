package com.crimsondesert.trainer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Main trainer class providing cheats for Crimson Desert.
 * Manages memory addresses and exposes high-level toggle methods.
 */
public class Trainer {

    private static final Logger LOGGER = Logger.getLogger(Trainer.class.getName());
    private final MemoryManager memoryManager;
    private final Map<String, Long> addressMap;
    private boolean infiniteHealthEnabled = false;
    private boolean infiniteStaminaEnabled = false;

    // Base addresses (example offsets, not real game values)
    private static final long HEALTH_BASE = 0x1A2B3C4D;
    private static final long STAMINA_BASE = 0x2B3C4D5E;

    /**
     * Creates a Trainer attached to the given MemoryManager.
     *
     * @param memoryManager the memory manager for process interaction
     */
    public Trainer(MemoryManager memoryManager) {
        this.memoryManager = memoryManager;
        this.addressMap = new HashMap<>();
        initializeAddresses();
        LOGGER.info("Trainer initialized");
    }

    /**
     * Populates the address map with known offsets for game variables.
     */
    private void initializeAddresses() {
        addressMap.put("health", HEALTH_BASE);
        addressMap.put("stamina", STAMINA_BASE);
        LOGGER.fine("Address map initialized with " + addressMap.size() + " entries");
    }

    /**
     * Toggles infinite health on or off.
     *
     * @param enable true to enable, false to disable
     * @throws IOException if memory write fails
     */
    public void setInfiniteHealth(boolean enable) throws IOException {
        long healthAddr = addressMap.get("health");
        if (enable) {
            memoryManager.writeInt(healthAddr, 9999);
            infiniteHealthEnabled = true;
            LOGGER.info("Infinite health enabled");
        } else {
            memoryManager.writeInt(healthAddr, 100);
            infiniteHealthEnabled = false;
            LOGGER.info("Infinite health disabled");
        }
    }

    /**
     * Toggles infinite stamina on or off.
     *
     * @param enable true to enable, false to disable
     * @throws IOException if memory write fails
     */
    public void setInfiniteStamina(boolean enable) throws IOException {
        long staminaAddr = addressMap.get("stamina");
        if (enable) {
            memoryManager.writeInt(staminaAddr, 9999);
            infiniteStaminaEnabled = true;
            LOGGER.info("Infinite stamina enabled");
        } else {
            memoryManager.writeInt(staminaAddr, 100);
            infiniteStaminaEnabled = false;
            LOGGER.info("Infinite stamina disabled");
        }
    }

    /**
     * Returns whether infinite health is currently active.
     *
     * @return true if enabled
     */
    public boolean isInfiniteHealthEnabled() {
        return infiniteHealthEnabled;
    }

    /**
     * Returns whether infinite stamina is currently active.
     *
     * @return true if enabled
     */
    public boolean isInfiniteStaminaEnabled() {
        return infiniteStaminaEnabled;
    }

    /**
     * Gets the underlying MemoryManager.
     *
     * @return the MemoryManager instance
     */
    public MemoryManager getMemoryManager() {
        return memoryManager;
    }
}
