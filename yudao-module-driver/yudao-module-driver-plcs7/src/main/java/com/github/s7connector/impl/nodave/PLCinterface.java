package com.github.s7connector.impl.nodave;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Thomas Rudin
 */
public final class PLCinterface {
    InputStream in;
    int localMPI; // the adapter's MPI address
    String name;

    OutputStream out;
    int protocol; // The kind of transport used on this interface.
    int wp, rp;

    public PLCinterface(final OutputStream out, final InputStream in, final String name, final int localMPI,
                        final int protocol) {
        this.init(out, in, name, localMPI, protocol);
    }

    public void init(final OutputStream oStream, final InputStream iStream, final String name, final int localMPI,
                     final int protocol) {
        this.out = oStream;
        this.in = iStream;
        this.name = name;
        this.localMPI = localMPI;
        this.protocol = protocol;
    }

    public int read(final byte[] b, int start, int len) {
        int res;
        try {
            int retry = 0;
            while ((this.in.available() <= 0) && (retry < 500)) {
                try {
                    if (retry > 0) {
                        Thread.sleep(1);
                    }
                    retry++;
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            }
            res = 0;
            while ((this.in.available() > 0) && (len > 0)) {
                res = this.in.read(b, start, len);
                start += res;
                len -= res;
            }
            return res;
        } catch (final IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void write(final byte[] b, final int start, final int len) {
        try {
            this.out.write(b, start, len);
        } catch (final IOException e) {
            System.err.println("Interface.write: " + e);
        }
    }

}
