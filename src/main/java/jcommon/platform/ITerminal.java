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

package jcommon.platform;

import jcommon.Terminal;

import java.io.Serializable;

/**
 * Provides native access to platform-specific, terminal-related information.
 */
public interface ITerminal extends IPlatformImplementation {
  public static final Terminal.Dimension DEFAULT_DIMENSIONS = new Terminal.Dimension(80, 24);

  public static class Default implements ITerminal, Serializable {
    public static final ITerminal INSTANCE = new Default();

    private Default() {
      //Prevent outside instantiation
    }

    private Object readResolve() {
      return INSTANCE;
    }

    @Override
    public Terminal.Dimension queryTerminalDimensions() {
      return DEFAULT_DIMENSIONS;
    }
  }

  public static final ITerminal DEFAULT = Default.INSTANCE;

  Terminal.Dimension queryTerminalDimensions();
}
