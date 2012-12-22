
package jcommon.init;

import jcommon.Sys;
import static org.junit.Assert.*;
import jcommon.extract.Resources;

/**
 *
 * @author David Hoyt <dhoyt@hoytsoft.org>
 */
public class TestRegistryReference extends RegistryReference {

	@Override
	public void register() throws Throwable {
		assertTrue(true);
	}

	@Override
	public void unregister() throws Throwable {
		assertTrue(true);
	}

	@Override
	public Resources createResourceExtractor() {
		return Resources.newInstance(
			Sys.createPlatformPackageResourceName("resources.extraction", "test.xml")
		);
	}

}
