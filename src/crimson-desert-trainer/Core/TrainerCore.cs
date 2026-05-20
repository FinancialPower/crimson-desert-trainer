using System;
using CrimsonDesertTrainer.Memory;

namespace CrimsonDesertTrainer.Core
{
    /// <summary>
    /// Core trainer logic that manages game memory modifications.
    /// </summary>
    public class TrainerCore
    {
        private MemoryManager _memoryManager;

        // Example memory offsets for Crimson Desert (hypothetical)
        private const int HealthOffset = 0x1A2B3C;
        private const int StaminaOffset = 0x4D5E6F;
        private const int GoldOffset = 0x7A8B9C;

        /// <summary>
        /// Initializes the trainer with a memory manager instance.
        /// </summary>
        /// <param name="memoryManager">The memory manager to use for process interaction.</param>
        public void Initialize(MemoryManager memoryManager)
        {
            _memoryManager = memoryManager ?? throw new ArgumentNullException(nameof(memoryManager));
            Console.WriteLine("Trainer initialized.");
        }

        /// <summary>
        /// Sets the player's health to the specified value.
        /// </summary>
        /// <param name="value">Desired health value.</param>
        public void SetHealth(int value)
        {
            if (_memoryManager == null) return;
            _memoryManager.WriteMemory(HealthOffset, value);
        }

        /// <summary>
        /// Sets the player's stamina to the specified value.
        /// </summary>
        /// <param name="value">Desired stamina value.</param>
        public void SetStamina(int value)
        {
            if (_memoryManager == null) return;
            _memoryManager.WriteMemory(StaminaOffset, value);
        }

        /// <summary>
        /// Sets the player's gold to the specified value.
        /// </summary>
        /// <param name="value">Desired gold amount.</param>
        public void SetGold(int value)
        {
            if (_memoryManager == null) return;
            _memoryManager.WriteMemory(GoldOffset, value);
        }
    }
}
