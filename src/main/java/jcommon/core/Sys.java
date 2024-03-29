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

import java.lang.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Utilities for accessing various system attributes and configuration.
 *
 * @author David Hoyt <dhoyt@hoytsoft.org>
 */
public class Sys {
  //<editor-fold defaultstate="collapsed" desc="Getters">
  public static OS getOS() {
    return OS.getSystemOS();
  }

  public static OSFamily getOSFamily() {
    return OS.getSystemOSFamily();
  }

  public static Arch getArch() {
    return Arch.getSystemArch();
  }

  public static int getPID() {
    return Process.queryPID();
  }

  public static int getLastError() {
    return Process.queryLastError();
  }

  public static String querySystemPath() {
    return Env.querySystemPath();
  }

  public static String queryEnvironmentVariable(final String name) {
    return Env.queryEnvironmentVariable(name);
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Helper Methods">
  protected static boolean loadResourcesAndWaitHelper(final Future f) {
    if (f == null)
      return false;
    try {
      Object o = f.get();
      return true;
    } catch(CancellationException t) {
      return false;
    } catch(ExecutionException t) {
      return false;
    } catch(InterruptedException t) {
      return false;
    }
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Public Static Methods">
  public static boolean isOS(final OS OS) {
    return (OS.getSystemOS() == OS);
  }

  public static boolean isOSFamily(final OSFamily OSFamily) {
    return (OS.getSystemOSFamily() == OSFamily);
  }

  public static boolean isArch(final Arch Arch) {
    return (Arch.getSystemArch() == Arch);
  }

  public static boolean isResourceAvailable(final String ResourceName) {
    final String res = (ResourceName.startsWith("/") && ResourceName.length() > 1 ? ResourceName.substring(1) : ResourceName).trim();
    if (StringUtil.isNullOrEmpty(res))
      return false;
    return (Thread.currentThread().getContextClassLoader().getResource(res) != null);
  }

  public static boolean saveEnvironmentVariable(final String name, final String value) {
    return Env.saveEnvironmentVariable(name, value);
  }

  public static boolean unsetEnvironmentVariable(final String name) {
    return Env.unsetEnvironmentVariable(name);
  }

  public static String createPlatformName() {
    return createPlatformName(getOSFamily());
  }

  public static String createPlatformName(OS OS) {
    return OS.getPlatformPartName() + "." + getArch().getPlatformPartName();
  }

  public static String createPlatformName(OSFamily OSFamily) {
    return OSFamily.getPlatformPartName() + "." + getArch().getPlatformPartName();
  }

  public static String createPackageResourcePrefix(final String PackagePrefix, final String PackageSuffix) {
    final String prefix = PackagePrefix.trim();
    final String suffix = PackageSuffix.trim();
    return
        '/' +
            (!StringUtil.isNullOrEmpty(prefix) ? prefix.replace('.', '/') + '/' : StringUtil.empty) +
            (!StringUtil.isNullOrEmpty(suffix) ? suffix.replace('.', '/') + '/' : StringUtil.empty)
        ;
  }

  public static String createPackageResourcePrefix(final String PackagePrefix) {
    return createPackageResourcePrefix(PackagePrefix, StringUtil.empty);
  }

  public static String createPlatformPackageName(final String PackagePrefix, final String PlatformName, final String PackageSuffix) {
    final String prefix = PackagePrefix.trim();
    final String suffix = PackageSuffix.trim();

    String ret = PlatformName;

    if (!StringUtil.isNullOrEmpty(prefix)) {
      if (prefix.endsWith("."))
        ret = prefix + ret;
      else
        ret = prefix + "." + ret;
    }

    if (!StringUtil.isNullOrEmpty(suffix)) {
      if (suffix.startsWith("."))
        ret = ret + suffix;
      else
        ret = ret + "." + suffix;
    }

    return ret;
  }

  public static String createPlatformPackageName(final String PackagePrefix, final String PackageSuffix) {
    return createPlatformPackageName(PackagePrefix, createPlatformName(), PackageSuffix);
  }

  public static String createPlatformPackageName(final String PackagePrefix) {
    return createPlatformPackageName(PackagePrefix, createPlatformName(), StringUtil.empty);
  }

  public static String createPlatformPackageName(final String PackagePrefix, final OS OS, final String PackageSuffix) {
    return createPlatformPackageName(PackagePrefix, createPlatformName(OS), PackageSuffix);
  }

  public static String createPlatformPackageName(final String PackagePrefix, final OS OS) {
    return createPlatformPackageName(PackagePrefix, createPlatformName(OS), StringUtil.empty);
  }

  public static String createPlatformPackageName(final String PackagePrefix, final OSFamily OSFamily, final String PackageSuffix) {
    return createPlatformPackageName(PackagePrefix, createPlatformName(OSFamily), PackageSuffix);
  }

  public static String createPlatformPackageName(final String PackagePrefix, final OSFamily OSFamily) {
    return createPlatformPackageName(PackagePrefix, createPlatformName(OSFamily), StringUtil.empty);
  }

  public static String createPlatformPackageResourcePrefix(final String PackagePrefix, final String PackageSuffix) {
    return createPackageResourcePrefix(createPlatformPackageName(PackagePrefix, PackageSuffix));
  }

  public static String createPlatformPackageResourcePrefix(final String PackagePrefix) {
    return createPackageResourcePrefix(createPlatformPackageName(PackagePrefix));
  }

  public static String createPlatformPackageResourcePrefix(final String PackagePrefix, final OS OS, final String PackageSuffix) {
    return createPackageResourcePrefix(createPlatformPackageName(PackagePrefix, OS, PackageSuffix));
  }

  public static String createPlatformPackageResourcePrefix(final String PackagePrefix, final OS OS) {
    return createPackageResourcePrefix(createPlatformPackageName(PackagePrefix, OS));
  }

  public static String createPlatformPackageResourcePrefix(final String PackagePrefix, final OSFamily OSFamily, final String PackageSuffix) {
    return createPackageResourcePrefix(createPlatformPackageName(PackagePrefix, OSFamily, PackageSuffix));
  }

  public static String createPlatformPackageResourcePrefix(final String PackagePrefix, final OSFamily OSFamily) {
    return createPackageResourcePrefix(createPlatformPackageName(PackagePrefix, OSFamily));
  }

  public static String createPlatformPackageResourceName(final String PackagePrefix, final String PackageSuffix, final String ResourceName) {
    return createPlatformPackageResourcePrefix(PackagePrefix, PackageSuffix) + ResourceName;
  }

  public static String createPlatformPackageResourceName(final String PackagePrefix, final String ResourceName) {
    return createPlatformPackageResourcePrefix(PackagePrefix) + ResourceName;
  }

  public static String createPlatformPackageResourceName(final String PackagePrefix, final OS OS, final String PackageSuffix, final String ResourceName) {
    return createPlatformPackageResourcePrefix(PackagePrefix, OS, PackageSuffix) + ResourceName;
  }

  public static String createPlatformPackageResourceName(final String PackagePrefix, final OS OS, final String ResourceName) {
    return createPlatformPackageResourcePrefix(PackagePrefix, OS) + ResourceName;
  }

  public static String createPlatformPackageResourceName(final String PackagePrefix, final OSFamily OSFamily, final String PackageSuffix, final String ResourceName) {
    return createPlatformPackageResourcePrefix(PackagePrefix, OSFamily, PackageSuffix) + ResourceName;
  }

  public static String createPlatformPackageResourceName(final String PackagePrefix, final OSFamily OSFamily, final String ResourceName) {
    return createPlatformPackageResourcePrefix(PackagePrefix, OSFamily) + ResourceName;
  }
  //</editor-fold>
}
