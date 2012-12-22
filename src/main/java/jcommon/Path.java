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

import com.sun.jna.Library;
import com.sun.jna.Native;
import java.io.File;
import jcommon.extract.ResourceUtils;
import jcommon.platform.IPath;
import jcommon.platform.PlatformProviders;
import jcommon.platform.win32.Win32Library;

/**
 *
 * @author David Hoyt <dhoyt@hoytsoft.org>
 */
public class Path {
  //<editor-fold defaultstate="collapsed" desc="Constants">
  public static final String
      directorySeparator = System.getProperty("file.separator")
    , pathSeparator = File.pathSeparator
  ;

  public static final String
      tempDirectory = clean(System.getProperty("java.io.tmpdir"))
    , homeDirectory = clean(System.getProperty("user.home"))
    , workingDirectory = clean(new File(".").getAbsolutePath())
  ;

  public static final String
    nativeResourcesDirectoryName = "native_java_resources"
  ;

  public static final String
    nativeResourcesDirectory
  ;
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Variables">
  private static IPath impl = PlatformProviders.find(IPath.class, IPath.DEFAULT);
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Initialization">
  static {
    nativeResourcesDirectory = Path.combine(tempDirectory, nativeResourcesDirectoryName).getAbsolutePath();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Public Static Methods">
  public static String nativeDirectorySeparator(final String path) {
    if (path == null)
      return StringUtil.empty;
    return path.replace("/", directorySeparator).replace("\\", directorySeparator);
  }

  public static String clean(final String path) {
    if (StringUtil.isNullOrEmpty(path))
      return StringUtil.empty;

    if (path.endsWith(directorySeparator))
      return path;

    if (path.endsWith("/") || path.endsWith("\\"))
      return path.substring(0, path.length() - 1) + directorySeparator;

    return path + directorySeparator;
  }

  public static File combine(final File parent, final String child) {
    return new File(parent, child);
  }

  public static File combine(final String parent, final String child) {
    return new File(parent, child);
  }

  public static boolean exists(final String path) {
    return exists(new File(path));
  }

  public static boolean exists(final File path) {
    if (path == null)
      return false;
    return path.exists();
  }

  public static boolean delete(final String path) {
    return delete(new File(path));
  }

  public static boolean delete(final File path) {
    if (path == null)
      return false;

    try {
      //True b/c the intent of this function is satisfied -- the directory/file no longer exists!
      if (!path.exists())
        return true;

      if (path.isFile())
        return path.delete();
      else
        return ResourceUtils.deleteDirectory(path);
    } catch(SecurityException se) {
      return false;
    }
  }

  public static boolean createSymbolicLink(final File to, final File from) {
    if (from == null || to == null)
      return false;
    return createSymbolicLink(to.getAbsolutePath(), from.getAbsolutePath());
  }

  public static boolean createSymbolicLink(final String to, final String from) {
    if (StringUtil.isNullOrEmpty(from) || StringUtil.isNullOrEmpty(to))
      throw new IllegalArgumentException("from and to cannot be empty");

    return impl.createSymbolicLink(to, from);
  }
  //</editor-fold>
}