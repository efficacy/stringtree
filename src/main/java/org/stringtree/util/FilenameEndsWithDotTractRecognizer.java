package org.stringtree.util;

import java.io.File;

import org.stringtree.tract.TractRecognizer;

public class FilenameEndsWithDotTractRecognizer implements TractRecognizer {
    
    public boolean isTract(File file) {
        return file != null && file.getName().endsWith(".tract");
    }
}
