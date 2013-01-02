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

package jcommon.core.platform.mac;

import jcommon.core.platform.IPlatformImplementation;
import jcommon.core.platform.IProcess;

public final class Process extends MacPlatformProvider implements IProcess {
  public static final IProcess INSTANCE = new Process();

  @Override
  public IPlatformImplementation provideImplementation() {
    return INSTANCE;
  }

  @Override
  public int queryLastError() {
    return IProcess.DEFAULT.queryLastError();
  }

  @Override
  public int queryPID() {
    return IProcess.DEFAULT.queryPID();
  }

  @Override
  public boolean changeWorkingDirectory(String path) {
    return IProcess.DEFAULT.changeWorkingDirectory(path);
  }
}
