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

package jcommon;

import jcommon.platform.ITerminal;
import jcommon.platform.PlatformProviders;

public final class Terminal {
  //<editor-fold defaultstate="collapsed" desc="Variables">
  private static ITerminal impl = PlatformProviders.find(ITerminal.class, ITerminal.DEFAULT);
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Initialization">
  private Terminal() {
  }
  //</editor-fold>

  public static Dimension queryTerminalDimensions() {
    return impl.queryTerminalDimensions();
  }

  public static class Dimension {
    public final long Width;
    public final long Height;

    public Dimension(long width, long height) {
      this.Width = width;
      this.Height = height;
    }

    @Override
    public String toString() {
      return "(" + Width + ", " + Height + ")";
    }
  }
}
