package hu.robnn.rss_analyzer.enums;

public enum UserRole {
    ADMIN(2),
    USER(1),
    ;

    Integer permissionLevel;

    UserRole(Integer permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public Integer getPermissionLevel() {
        return permissionLevel;
    }
}
