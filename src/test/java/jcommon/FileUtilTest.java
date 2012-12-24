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

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("unchecked")
public class FileUtilTest {
  private static void createTempTestFiles(int count, File base) throws IOException {
    for(int i = 0; i < count; ++i) {
      assertTrue(File.createTempFile("test-", ".unittest", base).exists());
    }
  }

  @Test
  public void testDeleteDirectory() throws IOException {
    final File tmp = new File(Path.tempDirectory);
    final File package_base = new File(tmp, FileUtil.class.getPackage().getName());
    final File base = new File(package_base, FileUtil.class.getSimpleName());
    assertTrue(base.exists() || base.mkdirs());

    final File d_1 = new File(base, "d_1");
    assertTrue(d_1.exists() || d_1.mkdirs());

    //Simple directory, nothing else inside it.
    assertTrue(d_1.exists());
    assertTrue(FileUtil.deleteDirectory(d_1));
    assertFalse(d_1.exists());

    //No nested directories, just a directory w/ a bunch of files inside.
    assertTrue(d_1.mkdirs());
    createTempTestFiles(30, d_1);
    assertTrue(FileUtil.deleteDirectory(d_1));
    assertFalse(d_1.exists());

    //Single nested directory, files inside that, none outside.
    final File d_2 = new File(d_1, "d_2");
    assertTrue(d_1.mkdir());
    assertTrue(d_2.mkdir());
    createTempTestFiles(15, d_2);
    assertTrue(d_2.exists());
    assertTrue(FileUtil.deleteDirectory(d_1));
    assertFalse(d_1.exists());

    //Doubly nested directory, files inside that, none outside.
    final File d_3 = new File(d_2, "d_3");
    assertTrue(d_1.mkdir());
    assertTrue(d_2.mkdir());
    assertTrue(d_3.mkdir());
    createTempTestFiles(15, d_3);
    assertTrue(d_3.exists());
    assertTrue(FileUtil.deleteDirectory(d_1));
    assertFalse(d_1.exists());

    //Doubly nested directory, multiple files/directories inside each.
    final File d_4 = new File(d_2, "d_4");
    final File d_5 = new File(d_4, "d_5");
    assertTrue(d_3.mkdirs());
    assertTrue(d_5.mkdirs());
    createTempTestFiles(15, d_3);
    createTempTestFiles(15, d_4);
    createTempTestFiles(15, d_5);
    assertTrue(d_3.exists());
    assertTrue(d_4.exists());
    assertTrue(d_5.exists());
    assertTrue(FileUtil.deleteDirectory(d_1));
    assertFalse(d_1.exists());


    assertTrue(d_1.mkdirs());
    assertTrue(d_2.mkdirs());
    assertTrue(d_3.mkdirs());
    assertTrue(d_4.mkdirs());
    assertTrue(d_5.mkdirs());
    final File d_6 = new File(d_2, "d_6");    assertTrue(d_6.mkdirs());
    final File d_7 = new File(d_2, "d_7");    assertTrue(d_7.mkdirs());
    final File d_8 = new File(d_7, "d_8");    assertTrue(d_8.mkdirs());
    final File d_9 = new File(d_8, "d_9");    assertTrue(d_9.mkdirs());
    final File d_10 = new File(d_8, "d_10");  assertTrue(d_10.mkdirs());
    final File d_11 = new File(d_7, "d_11");  assertTrue(d_11.mkdirs());
    final File d_12 = new File(d_7, "d_12");  assertTrue(d_12.mkdirs());
    final File d_13 = new File(d_11, "d_13"); assertTrue(d_13.mkdirs());
    final File d_14 = new File(d_10, "d_14"); assertTrue(d_14.mkdirs());
    createTempTestFiles(10, d_1);
    createTempTestFiles(10, d_2);
    createTempTestFiles(10, d_3);
    createTempTestFiles(10, d_7);
    createTempTestFiles(10, d_11);
    createTempTestFiles(10, d_13);
    assertTrue(FileUtil.deleteDirectory(d_1));
    assertFalse(d_1.exists());
  }
}
