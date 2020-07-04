package main.java.lucia.client.content.employee.type;

import main.java.lucia.client.content.employee.Permission;

import java.util.HashSet;
import java.util.Set;

/**
 * Any object which can hold permissions
 * @author Matthew Kwiatkowski
 */
public class PermissionHolder {

    /**
     * A set of the permissions that the object has
     */
    private Set<Permission> permissions;

    public PermissionHolder(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public PermissionHolder() {
        this.permissions = new HashSet<>();
    }

    /**
     * Gives the employee a specific permission
     *
     * @param e the {@link Permission} to give the employee
     */
    public void addPermission(Permission e) {
        permissions.add(e);
    }

    /**
     * Revokes the given permission for the employee
     *
     * @param e the {@link Permission} to revoke
     */
    public void revokePermission(Permission e) {
        permissions.remove(e);
    }

    /**
     * Checks if the employee has the given permission
     *
     * @param e the {@link Permission} to check
     * @return {@code true} if the employee has the given permission, false otherwise
     */
    public boolean hasPermission(Permission e) {
        return permissions.contains(e);
    }

    public Set<Permission> getPermissions(){
        return permissions;
    }
}
