import java.io.File
import kotlin.experimental.xor

/**
 * Created by pasuco on 2017/05/24.
 */

private val curDirPath = System.getProperty("user.dir") + "/sample"
private val cryPath = System.getProperty("user.dir") + "/sample2"
private val ISCRY = ".ISCRY"

fun main(args: Array<String>) {
    println("crypt start!!")
    val target = File(curDirPath) //コピーもと
    val temp = createTempFile(cryPath) //temp

    target.copyRecursively(temp, true)
    fileRename(temp.listFiles())
    fileCrypt(temp.listFiles())
    target.deleteRecursively() // sample dir削除
    temp.copyRecursively(target, true) //
    temp.deleteOnExit() // tempが消える
    println("crypt finish!!")
}

/**
 * @param files リネーム対象フォルダ
 */
fun fileRename(files: Array<File>) {
    for (file in files) {
        if (file.isDirectory) {
            fileRename(file.listFiles())
        } else {
            val new : File
            if (!file.name.endsWith(ISCRY)) {
                 new = File(file.absolutePath + ISCRY)
            } else {
                 new = File(file.absolutePath.replace(ISCRY, ""))
            }
            file.renameTo(new)
        }
    }
}

/**
 * @param files 暗号化対象フォルダ
 */
fun fileCrypt(files: Array<File>) {
    for (file in files) {
        if (file.isDirectory) {
            fileCrypt(file.listFiles())
        } else {
            val a = file.readBytes()
            for (i in a.indices) {
                val num = 255
                val b: Byte = num.toByte()
                a[i] = a[i].xor(b)
            }
            file.outputStream().write(a)
        }
    }
}
