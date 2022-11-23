package org.openscada.opc.dcom.da;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.*;

public class OPCITEMDEF {
    private String accessPath = "";

    private String itemID = "";

    private boolean active = true;

    private int clientHandle;

    private short requestedDataType = JIVariant.VT_EMPTY;

    private short reserved;

    public String getAccessPath() {
        return this.accessPath;
    }

    public void setAccessPath(final String accessPath) {
        this.accessPath = accessPath;
    }

    public int getClientHandle() {
        return this.clientHandle;
    }

    public void setClientHandle(final int clientHandle) {
        this.clientHandle = clientHandle;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public String getItemID() {
        return this.itemID;
    }

    public void setItemID(final String itemID) {
        this.itemID = itemID;
    }

    public short getRequestedDataType() {
        return this.requestedDataType;
    }

    public void setRequestedDataType(final short requestedDataType) {
        this.requestedDataType = requestedDataType;
    }

    public short getReserved() {
        return this.reserved;
    }

    public void setReserved(final short reserved) {
        this.reserved = reserved;
    }

    /**
     * Convert to structure to a J-Interop structure
     *
     * @return the j-interop structe
     * @throws JIException
     */
    public JIStruct toStruct() throws JIException {
        final JIStruct struct = new JIStruct();
        struct.addMember(new JIString(getAccessPath(), JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR));
        struct.addMember(new JIString(getItemID(), JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR));
        struct.addMember(isActive() ? 1 : 0);
        struct.addMember(getClientHandle());

        struct.addMember(0); // blob size
        struct.addMember(new JIPointer(null)); // blob

        struct.addMember(getRequestedDataType());
        struct.addMember(getReserved());
        return struct;
    }
}
