using System;
using System.Threading;
using CrimsonDesertTrainer.Core;
using CrimsonDesertTrainer.Memory;

namespace CrimsonDesertTrainer
{
    /// <summary>
    /// Main entry point for the Crimson Desert Trainer.
    /// </summary>
    internal class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Crimson Desert Trainer v1.0");
            Console.WriteLine("Waiting for game process...");

            var trainer = new TrainerCore();
            var memoryManager = new MemoryManager();

            // Wait for the game process to start
            while (!memoryManager.FindProcess("CrimsonDesert"))
            {
                Thread.Sleep(1000);
            }

            Console.WriteLine("Game found. Attaching...");
            trainer.Initialize(memoryManager);

            // Simple console input loop for demo
            Console.WriteLine("Commands: [h] health, [s] stamina, [g] gold, [q] quit");
            while (true)
            {
                var key = Console.ReadKey(true).KeyChar;
                switch (key)
                {
                    case 'h':
                        trainer.SetHealth(9999);
                        Console.WriteLine("Health set to 9999");
                        break;
                    case 's':
                        trainer.SetStamina(9999);
                        Console.WriteLine("Stamina set to 9999");
                        break;
                    case 'g':
                        trainer.SetGold(999999);
                        Console.WriteLine("Gold set to 999999");
                        break;
                    case 'q':
                        Console.WriteLine("Exiting...");
                        return;
                }
            }
        }
    }
}
