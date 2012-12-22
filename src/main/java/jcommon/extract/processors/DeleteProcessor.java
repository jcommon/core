
package jcommon.extract.processors;

import java.io.File;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import jcommon.StringUtil;
import jcommon.extract.DefaultResourceProcessor;
import jcommon.extract.IResourceFilter;
import jcommon.extract.IResourcePackage;
import jcommon.extract.IResourceProgressListener;
import jcommon.extract.IVariableProcessor;
import jcommon.extract.ResourceProcessor;
import jcommon.extract.ResourceProcessorFactory;
import jcommon.extract.ResourceUtils;

/**
 * Deletes every file/subdirectory from the provided directory.
 * 
 * @author David Hoyt <dhoyt@hoytsoft.org>
 */
@ResourceProcessor(
	tagName = "Delete",
	supportsSize = false
)
public class DeleteProcessor extends DefaultResourceProcessor {
	//<editor-fold defaultstate="collapsed" desc="Constants">
	public static final String
		  ATTRIBUTE_DIRECTORY	= "directory"
	;
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="Variables">
	private String directory = StringUtil.empty;
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="Initialization">
	public DeleteProcessor() {
	}

	public DeleteProcessor(File directory) {
		this(directory.getAbsolutePath(), StringUtil.empty, StringUtil.empty);
	}

	public DeleteProcessor(String directory) {
		this(directory, StringUtil.empty, StringUtil.empty);
	}

	public DeleteProcessor(String directory, String Title, String Description) {
		super(false, StringUtil.empty, StringUtil.empty, StringUtil.empty, Title, Description);

		this.directory = directory;
	}
	//</editor-fold>
	
	@Override
	protected boolean loadSettings(final String fullResourceName, final IResourcePackage pkg, final XPath xpath, final Node node, final Document document, final IVariableProcessor varproc, final ResourceProcessorFactory factory) throws XPathException {
		this.directory = stringAttributeValue(varproc, StringUtil.empty, node, ATTRIBUTE_DIRECTORY);
		
		return true;
	}

	@Override
	protected boolean processResource(final String fullResourceName, final IResourcePackage pkg, final IResourceFilter filter, final IResourceProgressListener progress) {
		final String dir = StringUtil.isNullOrEmpty(directory) || ".".equalsIgnoreCase(directory) ? pkg.getDirectory() : directory;
		if (StringUtil.isNullOrEmpty(dir))
			return true;

		final File d = new File(dir);
		if (!d.exists())
			return true;

		try {
			return ResourceUtils.deleteDirectory(d);
		} catch(Throwable t) {
			return false;
		}
	}
}
