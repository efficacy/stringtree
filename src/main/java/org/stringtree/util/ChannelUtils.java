package org.stringtree.util;

import java.io.IOException;
import java.nio.channels.FileChannel;

public class ChannelUtils {
    public static void close(FileChannel channel) {
        try {
            if (null != channel) channel.close();
        } catch (IOException e) {
            // can't close? not a lot we can do!
            e.printStackTrace();
        }
    }
}
