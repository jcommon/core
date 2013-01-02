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

import jcommon.core.platform.IPlatformImplementation;
import jcommon.core.platform.IProcess;
import jcommon.core.platform.win32.Win32Platform.Kernel32;

public final class Process extends Win32PlatformProvider implements IProcess {
  public static final IProcess INSTANCE = new Process();

  @Override
  public IPlatformImplementation provideImplementation() {
    return INSTANCE;
  }

  @Override
  public int queryLastError() {
    return Kernel32.GetLastError();
  }

  @Override
  public int queryPID() {
    return Kernel32.GetCurrentProcessId();
  }

  @Override
  public boolean changeWorkingDirectory(String path) {
    return (Kernel32.SetCurrentDirectory(path) != 0);
  }
}
