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

package jcommon.core;

import java.io.File;

import jcommon.core.platform.IProcess;
import jcommon.core.platform.PlatformProviders;

/**
 * Sets/gets the current (present) working directory, process identifier (pid),
 * and other process-related information.
 *
 * @author David Hoyt <dhoyt@hoytsoft.org>
 */
public class Process {
  //<editor-fold defaultstate="collapsed" desc="Variables">
  private static final Object cwdLock = new Object();
  private static final IProcess impl = PlatformProviders.find(IProcess.class, IProcess.DEFAULT);
  private static String cwd;
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Initialization">
  static {
    cwd = Path.workingDirectory;
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Public Static Methods">
  public static int queryLastError() {
    return impl.queryLastError();
  }

  public static int queryPID() {
    return impl.queryPID();
  }

  public static String queryWorkingDirectory() {
    synchronized(cwdLock) {
      return cwd;
    }
  }

  public static boolean changeWorkingDirectory(final File path) {
    if (path == null || !path.exists())
      return false;
    return changeWorkingDirectory(path.getAbsolutePath());
  }

  public static boolean changeWorkingDirectory(final String path) {
    if (StringUtil.isNullOrEmpty(path))
      return false;

    synchronized(cwdLock) {
      final String oldcwd = cwd;
      boolean ret = false;
      try {
        cwd = path;

        ret = impl.changeWorkingDirectory(path);

        return ret;
      } finally {
        if (!ret)
          cwd = oldcwd;
      }
    }
  }
  //</editor-fold>
}
