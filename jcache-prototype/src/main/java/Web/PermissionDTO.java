package Web;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PermissionDTO implements Serializable {

    private static final long serialVersionUID = -2601463615136880049L;

    // groupId -> permessi
    private Map<Long, Permission> permissions = new HashMap<>();

    public void addPermission(Long groupId, Boolean read, Boolean write, Boolean writePermissions) {
        permissions.put(groupId, new Permission(read, write, writePermissions));
    }

    public void addPermission(Long groupId, Permission permission) {
        permissions.put(groupId, permission);
    }

    public Permission getPermission(Long groupId) {
        return permissions.get(groupId);
    }

    public Map<Long, Permission> getPermissions() {
        return permissions;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(permissions).build();
    }

    public static class Permission implements Serializable {

        private static final long serialVersionUID = -8403555050894999480L;

        private Boolean read;
        private Boolean write;
        private Boolean writePermissions;

        public Permission() {
        }

        public Permission(Boolean read, Boolean write, Boolean writePermissions) {
            this.read = read;
            this.write = write;
            this.writePermissions = writePermissions;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("R", read).append("W", write).append("P", writePermissions).build();
        }

        /**
         * @return the read
         */
        public Boolean isRead() {
            return read;
        }

        /**
         * @param read the read to set
         */
        public void setRead(Boolean read) {
            this.read = read;
        }

        /**
         * @return the write
         */
        public Boolean isWrite() {
            return write;
        }

        /**
         * @param write the write to set
         */
        public void setWrite(Boolean write) {
            this.write = write;
        }

        /**
         * @return the writePermissions
         */
        public Boolean isWritePermissions() {
            return writePermissions;
        }

        /**
         * @param writePermissions the writePermissions to set
         */
        public void setWritePermissions(Boolean writePermissions) {
            this.writePermissions = writePermissions;
        }
    }
}