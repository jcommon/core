package jcommon.platform.unix;

import com.sun.jna.NativeLibrary;

/**
 * Created with IntelliJ IDEA.
 * User: dhoyt
 * Date: 12/22/12
 * Time: 1:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class UnixPlatform {
  public static class C {
    static {
      com.sun.jna.Native.register("c");
    }

    public static native String getenv(final String name);
    public static native int setenv(final String name, final String value, final int overwrite);
    public static native int unsetenv(final String name);

    public static native int symlink(final String to, final String from);

    public static native int getpid();
    public static native int chdir(final String path);

    public static int errno() {
      return NativeLibrary.getInstance("c").getGlobalVariableAddress("errno").getInt(0L);
    }
  }
}
