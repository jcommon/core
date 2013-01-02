/*
  Copyright (C) 2012-2013 the original author or authors.

  See the LICENSE.txt file distributed with this work for additional
  information regarding copyright ownership.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/

package jcommon.core.platform.win32;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.List;

import static jcommon.core.platform.JNAUtils.fromSeq;

/**
 * Provides native access to platform-specific information.
 */
@SuppressWarnings("all")
public class Win32Platform {
  public static class MSVCRT implements Win32Library {
    static {
      com.sun.jna.Native.register("msvcrt");
    }

    public static native String getenv(final String name);
    public static native int _putenv(final String name);
  }

  public static class Kernel32 implements Win32Library {
    static CharacterEncodingFunctions char_encoding;

    static {
      com.sun.jna.Native.register("kernel32");
      char_encoding = USE_UNICODE ? new Unicode() : new ASCII();
    }

    public static final int INVALID_HANDLE_VALUE = -1;

    public static final int STD_INPUT_HANDLE = -10;
    public static final int STD_OUTPUT_HANDLE = -11;
    public static final int STD_ERROR_HANDLE = -12;

    public static final int FILE_SHARE_READ = 1;
    public static final int FILE_SHARE_WRITE = 2;

    public static final int GENERIC_READ  = 0x80000000;
    public static final int GENERIC_WRITE = 0x40000000;

    public static final int CONSOLE_TEXTMODE_BUFFER = 1;

    // http://msdn.microsoft.com/en-us/library/ms682093(VS.85).aspx
    public static class CONSOLE_SCREEN_BUFFER_INFO extends Structure {
      public short wSizeX;
      public short wSizeY;
      public short wCursorPositionX;
      public short wCursorPositionY;
      public short wAttributes;
      public short wWindowLeft;
      public short wWindowTop;
      public short wWindowRight;
      public short wWindowBottom;
      public short wMaximumWindowSizeX;
      public short wMaximumWindowSizeY;

      public static class ByReference extends CONSOLE_SCREEN_BUFFER_INFO implements Structure.ByReference {
      }

      public CONSOLE_SCREEN_BUFFER_INFO() {
      }

      public CONSOLE_SCREEN_BUFFER_INFO(Pointer memory) {
        super(memory);
        read();
      }

      @Override
      protected List getFieldOrder() {
        return fromSeq(
            "wSizeX"
          , "wSizeY"
          , "wCursorPositionX"
          , "wCursorPositionY"
          , "wAttributes"
          , "wWindowLeft"
          , "wWindowTop"
          , "wWindowRight"
          , "wWindowBottom"
          , "wMaximumWindowSizeX"
          , "wMaximumWindowSizeY"
        );
      }
    }

    public static native int CloseHandle(int hObject);

    public static native int GetStdHandle(int nStdHandle);

    // http://msdn.microsoft.com/en-us/library/ms683171(VS.85).aspx
    public static native int GetConsoleScreenBufferInfo(int hConsoleOutput, CONSOLE_SCREEN_BUFFER_INFO lpConsoleScreenBufferInfo);

    public static native int CreateConsoleScreenBuffer(int dwDesiredAccess, int dwShareMode, Pointer lpSecurityAttributes, int dwFlags, Pointer lpScreenBufferData);

    public static native int GetLastError();
    public static native int GetCurrentProcessId();

    public static int SetCurrentDirectory(final String path) { return char_encoding.SetCurrentDirectory(path); }

    public static interface CharacterEncodingFunctions {
      int SetCurrentDirectory(final String path);
    }

    public static class ASCII implements CharacterEncodingFunctions {
      static {
        com.sun.jna.Native.register("kernel32");
      }

      public static native int SetCurrentDirectoryA(final String path);
      @Override public int SetCurrentDirectory(final String path) { return SetCurrentDirectoryA(path); }
    }

    public static class Unicode implements CharacterEncodingFunctions {
      static {
        com.sun.jna.Native.register("kernel32");
      }

      public static native int SetCurrentDirectoryW(final String path);
      @Override public int SetCurrentDirectory(final String path) { return SetCurrentDirectoryW(path); }
    }
  }
}
