
public enum PermissionLevels {

	ADMIN(2), USER(1);

	public int getPermissionLevel() {
		return permissionLevel;
	}

	public void setPermissionLevel(int permissionLevel) {
		this.permissionLevel = permissionLevel;
	}

	int permissionLevel;

	private PermissionLevels(int permissionLevel) {
		this.permissionLevel = permissionLevel;
	}
}
