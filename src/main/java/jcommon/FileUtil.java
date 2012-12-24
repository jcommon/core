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

import java.io.File;
import java.util.Stack;

/**
 * Contains general-purpose file and directory manipulation utilities.
 */
public class FileUtil {
  /**
   * Uses an iterative algorithm to recursively delete an entire directory and all its subdirectories.
   * Could be revised later for Java v1.7 compatibility by using Files.walkFileTree().
   *
   * @param path The directory to be deleted.
   * @return <code>true</code> if the entire directory and all subdirectories were removed; <code>false</code> otherwise.
   * @see <a href="http://fahdshariff.blogspot.com/2011/08/java-7-deleting-directory-by-walking.html">http://fahdshariff.blogspot.com/2011/08/java-7-deleting-directory-by-walking.html</a>
   */
  public static final boolean deleteDirectory(final File path) {
    if (!path.isDirectory())
      throw new IllegalArgumentException("path must be a directory");
    if (path == null)
      return false;
    if (!path.exists())
      return true;

    try {
      File curr;
      Stack<File> stack = new Stack<File>();
      Stack<File> dir_stack = new Stack<File>();
      stack.push(path);

      while(!stack.empty() && (curr = stack.pop()) != null) {
        for(File potential : curr.listFiles()) {
          if (potential.isDirectory()) {
            stack.push(potential);
            dir_stack.push(potential);
          } else {
            potential.delete();
          }
        }
      }

      while(!dir_stack.empty() && (curr = dir_stack.pop()) != null) {
        curr.delete();
      }

      path.delete();

      return true;
    } catch(Throwable t) {
      return false;
    }
  }
}
