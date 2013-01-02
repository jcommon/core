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
import jcommon.core.platform.IPlatformImplementation;
import jcommon.core.platform.ITerminal;
import jcommon.core.platform.win32.Win32Platform.*;

import java.io.Serializable;

public final class Terminal extends Win32PlatformProvider implements ITerminal, Serializable {
  public static final ITerminal INSTANCE = new Terminal();
  private static final Object lock = new Object();

  @Override
  public IPlatformImplementation provideImplementation() {
    return Terminal.INSTANCE;
  }

  @Override
  public jcommon.core.Terminal.Dimension queryTerminalDimensions() {
    long width = DEFAULT_DIMENSIONS.Width;
    long height = DEFAULT_DIMENSIONS.Height;

    synchronized (lock) {
      final int handle = Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
      if (Kernel32.INVALID_HANDLE_VALUE != handle) {
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
        if (Kernel32.GetConsoleScreenBufferInfo(handle, info) != 0) {
          width = Math.max(0, info.wWindowRight - info.wWindowLeft + 1);
          height = Math.max(0, info.wWindowBottom - info.wWindowTop + 1);
        } else {
          //It's likely that STDOUT has been redirected, so create a new buffer, get the info.
          //we need, and then get out.
          final int new_buffer_handle = Kernel32.CreateConsoleScreenBuffer(Kernel32.GENERIC_READ | Kernel32.GENERIC_WRITE, Kernel32.FILE_SHARE_READ | Kernel32.FILE_SHARE_WRITE, Pointer.NULL, Kernel32.CONSOLE_TEXTMODE_BUFFER, Pointer.NULL);
          if (Kernel32.INVALID_HANDLE_VALUE != new_buffer_handle) {
            try {
              if (Kernel32.GetConsoleScreenBufferInfo(new_buffer_handle, info) != 0) {
                width = Math.max(0, info.wWindowRight - info.wWindowLeft + 1);
                height = Math.max(0, info.wWindowBottom - info.wWindowTop + 1);
              }
            } finally {
              Kernel32.CloseHandle(new_buffer_handle);
            }
          }
        }
        info = null;
      }
    }

    return new jcommon.core.Terminal.Dimension(width, height);
  }
}
