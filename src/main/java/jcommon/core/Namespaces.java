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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.xml.namespace.NamespaceContext;

/**
 *
 * @author David Hoyt <dhoyt@hoytsoft.org>
 */
public class Namespaces {
  //<editor-fold defaultstate="collapsed" desc="Constants">
  public static final String
      DEFAULT_NAMESPACE_PREFIX	= "jcommon"
    , DEFAULT_NAMESPACE_URI		  = "https://github.com/davidhoyt/jcommon/"
  ;
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Public Static Methods">
  public static NamespaceContext createNamespaceContext() {
    return new NamespaceContext() {
      private HashMap<String, String> namespaces = new HashMap<String, String>(1);

      {
        namespaces.put(DEFAULT_NAMESPACE_PREFIX, DEFAULT_NAMESPACE_URI);
      }

      public String getNamespaceURI(String prefix) {
        if (namespaces.isEmpty() || StringUtil.isNullOrEmpty(prefix) || !namespaces.containsKey(prefix))
          return StringUtil.empty;
        return namespaces.get(prefix);
      }

      public Iterator getPrefixes(String namespaceURI) {
        return namespaces.values().iterator();
      }

      public String getPrefix(String namespaceURI) {
        if (StringUtil.isNullOrEmpty(namespaceURI))
          return StringUtil.empty;

        for(Entry<String, String> entry : namespaces.entrySet())
          if (namespaceURI.equals(entry.getValue()))
            return entry.getKey();

        return StringUtil.empty;
      }
    };
  }
  //</editor-fold>
}
