package de.jroene.vrapper.test;

import de.jroene.vrapper.vim.LineInformation;
import de.jroene.vrapper.vim.Mark;
import de.jroene.vrapper.vim.Platform;
import de.jroene.vrapper.vim.Search;
import de.jroene.vrapper.vim.SearchResult;
import de.jroene.vrapper.vim.Selection;
import de.jroene.vrapper.vim.Space;
import de.jroene.vrapper.vim.ViewPortInformation;
import de.jroene.vrapper.vim.VimUtils;

/**
 * A simple {@link Platform} implementation for unit tests.
 *
 * @author Matthias Radig
 */
public class TestPlatform implements Platform {

    StringBuilder buffer = new StringBuilder();
    int caretPosition = 0;
    private Selection selection;


    public LineInformation getLineInformation() {
        return getLineInformationOfOffset(getPosition());
    }

    public LineInformation getLineInformation(int line) {
        int index = 0;
        int currLine = 0;
        while(currLine < line && index < buffer.length()) {
            while (index < buffer.length()) {
                String c = buffer.substring(index, index+1);
                index += 1;
                if(VimUtils.isNewLine(c)) {
                    if (index < buffer.length()) {
                        c = buffer.substring(index-1, index+1);
                        if(VimUtils.isNewLine(c)) {
                            index += 1;
                        }
                    }
                    break;
                }
            }
            currLine += 1;
        }
        int startIndex = index;
        while(index < buffer.length()) {
            String c = buffer.substring(index, index+1);
            if(VimUtils.isNewLine(c)) {
                break;
            }
            index += 1;
        }
        int endIndex = index;
        return new LineInformation(line, startIndex, endIndex-startIndex);
    }

    public LineInformation getLineInformationOfOffset(int offset) {
        int index = 0;
        int currLine = 0;
        while(index < offset) {
            String c = buffer.substring(index, index+1);
            if(VimUtils.isNewLine(c)) {
                if (index < offset-1) {
                    c = buffer.substring(index, index+2);
                    if(VimUtils.isNewLine(c)) {
                        index += 1;
                    }
                }
                currLine += 1;
            }
            index += 1;
        }
        return getLineInformation(currLine);
    }

    public int getNumberOfLines() {
        int lines = 1;
        for (int i = 0; i < buffer.length(); i++) {
            String c = buffer.substring(i, i+1);
            if(VimUtils.isNewLine(c)) {
                if (i > 1 && i < buffer.length()) {
                    c = buffer.substring(i-1, i+1);
                    if(VimUtils.isNewLine(c)) {
                        i += 1;
                    }
                }
                lines += 1;
            }
        }
        return lines;
    }

    public int getPosition() {
        return caretPosition;
    }

    public String getText(int index, int length) {
        return buffer.substring(index, index+length);
    }

    public void redo() {
        // TODO Auto-generated method stub

    }

    public void replace(int index, int length, String s) {
        buffer.replace(index, index+length, s);
        if (caretPosition > buffer.length()) {
            caretPosition = buffer.length();
        }
    }

    public void save() {
        // TODO Auto-generated method stub

    }

    public void setActionLine(String actionLine) {
        // TODO Auto-generated method stub

    }

    public void setCommandLine(String commandLine) {
        // TODO Auto-generated method stub

    }

    public void setDefaultSpace() {
        // TODO Auto-generated method stub

    }

    public void setPosition(int index) {
        if (index < 0 || index > buffer.length()) {
            throw new IllegalArgumentException("invalid position: "+index+", buffer content: "+readableBuffer());
        }
        caretPosition = index;
    }

    private String readableBuffer() {
        return buffer.toString().replace("\r", "\\r").replace("\n", "\\n");
    }

    public void setSpace(Space space) {
        // TODO Auto-generated method stub

    }

    public void setUndoMark() {
        // TODO Auto-generated method stub

    }

    public void shift(int line, int lineCount, int shift) {
        // TODO Auto-generated method stub

    }

    public void toCommandLineMode() {
        // TODO Auto-generated method stub

    }

    public void toInsertMode() {
        // TODO Auto-generated method stub

    }

    public void toNormalMode() {
        // TODO Auto-generated method stub

    }

    public void undo() {
        // TODO Auto-generated method stub

    }

    public void setBuffer(String s) {
        buffer.setLength(0);
        replace(0, 0, s);
    }

    public String getBuffer() {
        return buffer.toString();
    }

    public Selection getSelection() {
        return selection;
    }

    public void setSelection(Selection s) {
        selection = s;
    }

    public void beginChange() {
        // TODO Auto-generated method stub

    }

    public void endChange() {
        // TODO Auto-generated method stub

    }

    public SearchResult find(Search search, int offset) {
        // TODO Auto-generated method stub
        return null;
    }

    public void setRepaint(boolean repaint) {
        // TODO Auto-generated method stub

    }

    public void toVisualMode() {
        // TODO Auto-generated method stub

    }

    public void setLineWiseMouseSelection(boolean lineWise) {
        // TODO Auto-generated method stub

    }

    public boolean close(boolean force) {
        // TODO Auto-generated method stub
        return false;
    }

    public void insert(String s) {
        replace(getPosition(), 0, s);
    }

    public ViewPortInformation getViewPortInformation() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setTopLine(int number) {
        // TODO Auto-generated method stub

    }

    public void format(Selection s) {
        //do nothing
    }

    public void toOperatorPendingMode() {
        // TODO Auto-generated method stub

    }

    public Mark getMark(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    public void setMark(String name) {
        // TODO Auto-generated method stub

    }

}
