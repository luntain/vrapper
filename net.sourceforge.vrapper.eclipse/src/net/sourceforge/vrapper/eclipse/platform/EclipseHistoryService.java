package net.sourceforge.vrapper.eclipse.platform;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

import net.sourceforge.vrapper.platform.HistoryService;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.IUndoManagerExtension;
import org.eclipse.swt.custom.StyledText;

public class EclipseHistoryService implements IUndoManager, IUndoManagerExtension, HistoryService {
	private static final Logger LOGGER = Logger.getLogger(EclipseHistoryService.class.getName());

    private final IUndoManager delegate;
    private boolean locked;
    private final StyledText textWidget;
    
    static {
    	FileHandler handler;
		try {
			handler = new FileHandler("/tmp/vrapper.log");
			LOGGER.addHandler(handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public EclipseHistoryService(StyledText textWidget, IUndoManager delegate) {
        this.textWidget = textWidget;
        this.delegate = delegate;
        LOGGER.info("Initialised EclipseHistoryService");
    }

    public void lock() {
    	LOGGER.info("lock()");
        locked = true;
    }

    public void unlock() {
    	LOGGER.info("unlock()");
        locked = false;
    }

    public void beginCompoundChange() {
    	LOGGER.info("beginCompoundChange()");
        if (!locked) {
        	LOGGER.info("delegate.beginCompoundChange()");
            delegate.beginCompoundChange();
        }
    }

    public void endCompoundChange() {
    	LOGGER.info("endCompoundChange()");
        if (!locked) {
        	LOGGER.info("delegate.endCompoundChange()");
            delegate.endCompoundChange();
        }
    }

    public void connect(ITextViewer arg0) {
    	LOGGER.info("connect()");
        delegate.connect(arg0);
    }

    public void disconnect() {
    	LOGGER.info("disconnect()");
        //delegate.disconnect();
    }

    public void undo() {
    	LOGGER.info("undo()");
        delegate.undo();
        deselectAll();
    }

    public void redo() {
    	LOGGER.info("redo()");
        delegate.redo();
        deselectAll();
    }

    public boolean undoable() {
    	boolean undoable = delegate.undoable();
    	LOGGER.info("undoable() returns " + undoable);
		return undoable;
    }

    public boolean redoable() {
    	boolean redoable = delegate.redoable();
    	LOGGER.info("redoable() returns " + redoable);
		return redoable;
    }

    public void reset() {
    	LOGGER.info("reset()");
        delegate.reset();
    }

    public void setMaximalUndoLevel(int level) {
    	LOGGER.info("setMaximalUndoLevel(" + level + ")");
        delegate.setMaximalUndoLevel(level);
    }

    private void deselectAll() {
        // XXX: we acheive some degree of Vim compatibility by jumping
        // to beginning of selection; this is hackish
        int caretOffset = textWidget.getSelection().x;
        textWidget.setSelection(caretOffset);
    }

	public IUndoContext getUndoContext() {
    	LOGGER.info("getUndoContext()");
		if (delegate instanceof IUndoManagerExtension) {
			return ((IUndoManagerExtension)delegate).getUndoContext();
		}
		return null;
	}

}
