package net.sourceforge.vrapper.eclipse.platform;

import net.sourceforge.vrapper.platform.HistoryService;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.IUndoManagerExtension;
import org.eclipse.swt.custom.StyledText;

public class EclipseHistoryService implements IUndoManager, IUndoManagerExtension, HistoryService {

    private final IUndoManager delegate;
    private boolean locked;
    private final StyledText textWidget;

    public EclipseHistoryService(StyledText textWidget, IUndoManager delegate) {
        this.textWidget = textWidget;
        this.delegate = delegate;
    }

    public void lock() {
        locked = true;
    }

    public void unlock() {
        locked = false;
    }

    public void beginCompoundChange() {
        if (!locked) {
            delegate.beginCompoundChange();
        }
    }

    public void endCompoundChange() {
        if (!locked) {
            delegate.endCompoundChange();
        }
    }

    public void connect(ITextViewer arg0) {
        delegate.connect(arg0);
    }

    public void disconnect() {
        delegate.disconnect();
    }

    public void undo() {
        delegate.undo();
        deselectAll();
    }

    public void redo() {
        delegate.redo();
        deselectAll();
    }

    public boolean undoable() {
        return delegate.undoable();
    }

    public boolean redoable() {
        return delegate.redoable();
    }

    public void reset() {
        delegate.reset();
    }

    public void setMaximalUndoLevel(int arg0) {
        delegate.setMaximalUndoLevel(arg0);
    }

    private void deselectAll() {
        // XXX: we acheive some degree of Vim compatibility by jumping
        // to beginning of selection; this is hackish
        int caretOffset = textWidget.getSelection().x;
        textWidget.setSelection(caretOffset);
    }

	public IUndoContext getUndoContext() {
		if (delegate instanceof IUndoManagerExtension) {
			return ((IUndoManagerExtension)delegate).getUndoContext();
		}
		return null;
	}

}
