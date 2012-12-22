package jcommon.platform;

import jcommon.Arch;
import jcommon.ArchWordSize;
import jcommon.OS;
import jcommon.OSFamily;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Searches platform-specific providers for native implementations of routines.
 */
public class PlatformProviders {
  private static final Object
      lock = new Object()
  ;

  private static final ServiceLoader<IPlatformProvider>
      platform_provider_loader = ServiceLoader.load(IPlatformProvider.class)
  ;

  private static final Map<Class<? extends IPlatformImplementation>, IPlatformImplementation>
      providers = new HashMap<Class<? extends IPlatformImplementation>, IPlatformImplementation>(5, 1.0f)
  ;

  static {
    refresh();
  }

  public static void refresh() {
    OSFamily family = OSFamily.getSystemOSFamily();
    OS os = OS.getSystemOS();
    Arch arch = Arch.getSystemArch();

    synchronized (lock) {
      platform_provider_loader.reload();
      providers.clear();

      for(IPlatformProvider provider : platform_provider_loader) {
        if (!provider.providerMatchesPlatform(family, os, arch))
          continue;

        //Get the instance
        IPlatformImplementation impl = provider.provideImplementation();
        providers.put(impl.getClass(), impl);
      }
    }
  }

  public static <T extends IPlatformImplementation> T find(Class<T> interfaceClass) {
    return find(interfaceClass, null);
  }

  @SuppressWarnings("unchecked")
  public static <T extends IPlatformImplementation> T find(Class<T> interfaceClass, T defaultImplementation) {
    if (!interfaceClass.isInterface())
      throw new IllegalArgumentException(interfaceClass.getName() + " must represent an interface");

    IPlatformImplementation impl = null;
    for (Map.Entry<Class<? extends IPlatformImplementation>, IPlatformImplementation> e : providers.entrySet()) {
      if (!interfaceClass.isAssignableFrom(e.getKey()))
        continue;
      impl = e.getValue();
    }

    return (impl != null ? (T)impl : defaultImplementation);
  }
}
