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
 * Provides native access to platform-specific environment information.
 */
public interface IEnv extends IPlatformImplementation {
  public static class Default implements IEnv, Serializable {
    public static final IEnv INSTANCE = new Default();

    private Default() {
      //Prevent outside instantiation
    }

    private Object readResolve() {
      return INSTANCE;
    }

    @Override
    public String querySystemPath() {
      throw new UnsupportedOperationException("querySystemPath");
    }

    @Override
    public String queryEnvironmentVariable(String name) {
      throw new UnsupportedOperationException("queryEnvironmentVariable");
    }

    @Override
    public boolean saveEnvironmentVariable(String name, String value) {
      throw new UnsupportedOperationException("saveEnvironmentVariable");
    }

    @Override
    public boolean unsetEnvironmentVariable(String name) {
      throw new UnsupportedOperationException("unsetEnvironmentVariable");
    }
  }

  public static final IEnv DEFAULT = Default.INSTANCE;

  String querySystemPath();

  String queryEnvironmentVariable(String name);
  boolean saveEnvironmentVariable(String name, String value);
  boolean unsetEnvironmentVariable(String name);
}
