import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//https://leetcode.com/articles/design-in-memory-file-system/
public class FileSystem {

	Directory home;

	static Map<String, String> fileReadbleFormats = new HashMap();

	public FileSystem(Directory home) {
		this.home = home;
	}

	// returns directory at a given path, and add if any of the directory is not
	// present in the path
	public Directory getDirectoryAtPath(String path) {
		String[] smallPaths = path.split("/");
		Directory current = home;
		int length = smallPaths.length;
		for (int index = 0; index < length; index++) {
			String currentDirecotryName = smallPaths[index];

			if (current.getDirectories().containsKey(currentDirecotryName)
					&& current.getDirectories().get(currentDirecotryName) instanceof Directory) {
				current = (Directory) current.getDirectories().get(currentDirecotryName);
			} else {
				if (current.getPermissionLevelAllowed().getPermissionLevel() >= PermissionLevels.ADMIN
						.getPermissionLevel()) {
					Directory childDirecotry = new Directory();
					childDirecotry.setParent(current);
					current.getDirectories().put(currentDirecotryName, childDirecotry);
					current = childDirecotry;
				}
			}
		}
		return current;
	}

	public List<BaseStorage> getContentsInSortOrder(String Path) {
		Directory current = getDirectoryAtPath(Path);
		List<BaseStorage> directories = current.getDirectories().entrySet().stream().map(en -> en.getValue())
				.sorted((e1, e2) -> e1.getContentSize().compareTo(e2.getContentSize())).collect(Collectors.toList());
		return directories;
	}

	// read file content present in the path
	public String readContentFromFile(String path) throws Exception {
		String[] smallPaths = path.split("/");
		Directory current = getDirectoryForGivenFilePath(path);
		String currentDirecotryName = smallPaths[smallPaths.length - 1];
		if (current.getDirectories().containsKey(currentDirecotryName)
				&& current.getDirectories().get(currentDirecotryName) instanceof File)
			return current.getDirectories().get(currentDirecotryName).toString();
		return "";
	}

	// gives the directory for the given file path
	public Directory getDirectoryForGivenFilePath(String filePath) throws Exception {
		String[] smallPaths = filePath.split("/");
		Directory current = home;
		int length = smallPaths.length;
		String currentDirecotryName = "";
		for (int index = 0; index < length - 1; index++) {
			currentDirecotryName = smallPaths[index];

			if (current.getDirectories().containsKey(currentDirecotryName)
					&& current.getDirectories().get(currentDirecotryName) instanceof Directory) {
				current = (Directory) current.getDirectories().get(currentDirecotryName);
			} else {
				throw new Exception();
			}
		}
		return current;
	}

	// adds the file at the given path
	public void addFileAtGivenPath(String fileContent, String path) throws Exception {
		String[] smallPaths = path.split("/");
		Directory current = getDirectoryForGivenFilePath(path);
		String currentDirecotryName = smallPaths[smallPaths.length - 1];
		if (current.getDirectories().containsKey(currentDirecotryName)
				&& current.getDirectories().get(currentDirecotryName) instanceof File) {
			File file = ((File) current.getDirectories().get(currentDirecotryName));
			String newContent = file.getFileContent().concat(fileContent);
			file.setFileContent(newContent);
		} else {
			File file = new File();
			file.setFileContent(fileContent);
			current.getDirectories().put(currentDirecotryName, file);
		}

	}

	public static void addReableFormats(String formatType, String readerApplicationName) {
		fileReadbleFormats.put(formatType, readerApplicationName);
	}

}
