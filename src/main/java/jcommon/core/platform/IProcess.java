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

package jcommon.core.platform;

import java.io.Serializable;

/**
 * Provides native access to platform-specific process operations.
 */
public interface IProcess extends IPlatformImplementation {
  public static class Default implements IProcess, Serializable {
    public static final IProcess INSTANCE = new Default();

    private Default() {
      //Prevent outside instantiation
    }

    private Object readResolve() {
      return INSTANCE;
    }
    @Override
    public int queryLastError() {
      throw new UnsupportedOperationException("queryLastError");
    }

    @Override
    public int queryPID() {
      throw new UnsupportedOperationException("queryPID");
    }

    @Override
    public boolean changeWorkingDirectory(String path) {
      throw new UnsupportedOperationException("changeWorkingDirectory");
    }
  }

  public static final IProcess DEFAULT = Default.INSTANCE;

  int queryLastError();
  int queryPID();
  boolean changeWorkingDirectory(final String path);
}
