

package xcom.apps ;


import java.io.File ;
import java.io.IOException ;


public class RecursiveDelete {

	public static void deleteDirectory(File directory) throws IOException {
		if ( !directory.exists() ) {
			return ;
		}

		File[] files = directory.listFiles() ;
		if ( files != null ) {
			for ( File file : files ) {
				if ( file.isDirectory() ) {
					deleteDirectory(file) ; // Recursive call for subdirectories
				}
//				else {
//					if ( !file.delete() ) {
//						throw new IOException("Failed to delete file: " + file.getAbsolutePath()) ;
//					}
//				}
			}
		}
//		if ( !directory.delete() ) {
//			throw new IOException("Failed to delete directory: " + directory.getAbsolutePath()) ;
//		}
	}

	public static void main(String[] args) {
		File directoryToDelete = new File("N:\\__delete") ; // Replace with the actual path
		try {
			deleteDirectory(directoryToDelete) ;
			System.out.println("Directory deleted successfully.") ;
		}
		catch ( IOException e ) {
			System.err.println("Error deleting directory: " + e.getMessage()) ;
		}
	}
}
