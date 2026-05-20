using System;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace CrimsonDesertTrainer.Memory
{
    /// <summary>
    /// Manlow-level memory operations for the target game process.
    /// </summary>
    public class MemoryManager
    {
        private IntPtr _processHandle;
        private int _processId;

        // Win32 API imports for memory manipulation
        [DllImport("kernel32.dll")]
        private static extern IntPtr OpenProcess(uint dwDesiredAccess, bool bInheritHandle, int dwProcessId);

        [DllImport("kernel32.dll")]
        private static extern bool ReadProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, byte[] lpBuffer, int dwSize, out int lpNumberOfBytesRead);

        [DllImport("kernel32.dll")]
        private static extern bool WriteProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, byte[] lpBuffer, int dwSize, out int lpNumberOfBytesWritten);

        [DllImport("kernel32.dll")]
        private static extern bool CloseHandle(IntPtr hObject);

        private const uint PROCESS_VM_READ = 0x0010;
        private const uint PROCESS_VM_WRITE = 0x0020;
        private const uint PROCESS_VM_OPERATION = 0x0008;

        /// <summary>
        /// Finds the target game process by name.
        /// </summary>
        /// <param name="processName">Name of the process (without .exe).</param>
        /// <returns>True if process is found and handle is opened.</returns>
        public bool FindProcess(string processName)
        {
            var processes = Process.GetProcessesByName(processName);
            if (processes.Length == 0)
                return false;

            _processId = processes[0].Id;
            _processHandle = OpenProcess(PROCESS_VM_READ | PROCESS_VM_WRITE | PROCESS_VM_OPERATION, false, _processId);

            return _processHandle != IntPtr.Zero;
        }

        /// <summary>
        /// Writes an integer value to a memory address relative to the base address.
        /// </summary>
        /// <param name="offset">Offset from the process base address.</param>
        /// <param name="value">Integer value to write.</param>
        public void WriteMemory(int offset, int value)
        {
            if (_processHandle == IntPtr.Zero)
                throw new InvalidOperationException("Process not attached.");

            // In a real trainer, we'd resolve the base address dynamically.
            // Here we use a fixed base for demonstration.
            IntPtr baseAddress = (IntPtr)0x400000; // Example base
            IntPtr address = baseAddress + offset;

            byte[] buffer = BitConverter.GetBytes(value);
            if (!WriteProcessMemory(_processHandle, address, buffer, buffer.Length, out int bytesWritten))
            {
                Console.WriteLine($"Failed to write memory at 0x{address.ToInt64():X}");
            }
        }

        /// <summary>
        /// Reads an integer value from a memory address.
        /// </summary>
        /// <param name="offset">Offset from the process base address.</param>
        /// <returns>The integer value read from memory.</returns>
        public int ReadMemory(int offset)
        {
            if (_processHandle == IntPtr.Zero)
                throw new InvalidOperationException("Process not attached.");

            IntPtr baseAddress = (IntPtr)0x400000;
            IntPtr address = baseAddress + offset;

            byte[] buffer = new byte[4];
            if (ReadProcessMemory(_processHandle, address, buffer, buffer.Length, out int bytesRead))
            {
                return BitConverter.ToInt32(buffer, 0);
            }

            Console.WriteLine($"Failed to read memory at 0x{address.ToInt64():X}");
            return 0;
        }

        /// <summary>
        /// Releases the process handle when done.
        /// </summary>
        public void Dispose()
        {
            if (_processHandle != IntPtr.Zero)
            {
                CloseHandle(_processHandle);
                _processHandle = IntPtr.Zero;
            }
        }
    }
}
