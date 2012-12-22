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

package jcommon.platform.win32;

/**
 * Standard Win32Platform error values for use with GetLastError().
 *
 * Courtesy JNA project:
 *     https://jna.dev.java.net/source/browse/jna/trunk/jnalib/src/com/sun/jna/examples/win32/W32Errors.java?rev=7&view=markup
 *
 * @author David Hoyt <dhoyt@hoytsoft.org>
 */
public interface Win32Errors {
  public static final int
      NO_ERROR                  = 0
    , ERROR_INVALID_FUNCTION    = 1
    , ERROR_FILE_NOT_FOUND      = 2
    , ERROR_PATH_NOT_FOUND      = 3
  ;
}