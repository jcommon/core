
package jcommon.init;

import jcommon.extract.Resources;

/**
 *
 * @author David Hoyt <dhoyt@hoytsoft.org>
 */
public abstract class RegistryReference implements IRegistryReference {

	@Override
	public void register() throws Throwable {
	}

	@Override
	public void unregister() throws Throwable {
	}

	@Override
	public Resources createResourceExtractor() {
		return Resources.Empty;
	}

}
