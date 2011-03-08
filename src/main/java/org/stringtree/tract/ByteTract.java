package org.stringtree.tract;

import org.stringtree.Tract;

public interface ByteTract extends Tract {
    byte[] getContentBytes();
    void setContentBytes(byte[] bytes);
}
