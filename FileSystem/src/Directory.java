import java.util.HashMap;
import java.util.Map;

//using composite design pattern
//https://www.tutorialspoint.com/design_pattern/composite_pattern.htm
public class Directory extends BaseStorage {

	public Directory getParent() {
		return parent;
	}

	public void setParent(Directory parent) {
		this.parent = parent;
	}

	Map<String, BaseStorage> directories;
	Directory parent;

	public Map<String, BaseStorage> getDirectories() {
		return directories;
	}

	public void setDirectories(Map<String, BaseStorage> directories) {
		this.directories = directories;
	}

	public PermissionLevels getPermissionLevelAllowed() {
		return permissionLevelAllowed;
	}

	public void setPermissionLevelAllowed(PermissionLevels permissionLevelAllowed) {
		this.permissionLevelAllowed = permissionLevelAllowed;
	}

	PermissionLevels permissionLevelAllowed;

	public Directory() {
		directories = new HashMap();
	}

}
