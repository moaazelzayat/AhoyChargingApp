import java.io.File
import java.io.FileInputStream
import java.util.Properties

/**
 * Loads properties file from project
 *
 * @param propsFile [File] object to be loaded
 * @return nullable [Properties] containing the loaded file, if found.
 */
fun loadPropertiesFile(propsFile: File): Properties? = propsFile
    .takeIf { it.exists() }
    ?.let {
        val props = Properties()
        props.load(FileInputStream(it))
        return@let props
    }
