package org.openscada.opc.dcom.da.impl;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.openscada.opc.dcom.common.impl.BaseCOMObject;
import org.openscada.opc.dcom.da.Constants;

import java.net.UnknownHostException;

public class OPCBrowse extends BaseCOMObject
{
    public OPCBrowse ( final IJIComObject opcServer ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        super ( opcServer.queryInterface ( Constants.IOPCBrowse_IID ) );
    }
}
