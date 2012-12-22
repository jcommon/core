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

package jcommon.platform.mac;

import jcommon.platform.IPlatformImplementation;
import jcommon.platform.ITerminal;

import java.io.Serializable;

public final class Terminal extends MacPlatformProvider implements ITerminal, Serializable {
  public static final ITerminal INSTANCE = new Terminal();

  @Override
  public IPlatformImplementation provideImplementation() {
    return Terminal.INSTANCE;
  }

  @Override
  public jcommon.Terminal.Dimension queryTerminalDimensions() {
    return ITerminal.DEFAULT.queryTerminalDimensions();
  }
}
