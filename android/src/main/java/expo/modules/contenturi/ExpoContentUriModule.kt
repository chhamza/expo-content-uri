package expo.modules.contenturi

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import java.io.File
import java.io.FileOutputStream

class ExpoContentUriModule : Module() {

  override fun definition() = ModuleDefinition {
    Name("ExpoContentUri")

    AsyncFunction("copyToAppStorage") { uriStr: String, options: Map<String, Any>? ->
      val ctx = appContext.reactContext ?: throw Exception("No React context")
      val cr: ContentResolver = ctx.contentResolver
      val uri = Uri.parse(uriStr)

      val subdir = (options?.get("subdir") as? String)?.trim()?.ifEmpty { "imports" } ?: "imports"
      val requestedFileName = (options?.get("fileName") as? String)?.trim()?.ifEmpty { null }

      val dir = File(ctx.filesDir, subdir)
      if (!dir.exists()) dir.mkdirs()

      val displayName = requestedFileName
        ?: queryDisplayName(cr, uri)
        ?: fallbackNameFromUri(uri)
        ?: "Imported_${System.currentTimeMillis()}"

      val outFile = uniqueFile(dir, sanitizeFileName(displayName))

      var bytesWritten = 0L
      cr.openInputStream(uri).use { input ->
        if (input == null) throw Exception("Cannot open input stream for URI: $uriStr")

        FileOutputStream(outFile).use { output ->
          val buffer = ByteArray(1024 * 1024) // 1MB
          while (true) {
            val read = input.read(buffer)
            if (read <= 0) break
            output.write(buffer, 0, read)
            bytesWritten += read.toLong()
          }
          output.flush()
        }
      }

      mapOf(
        "filePath" to outFile.absolutePath,
        "fileName" to outFile.name,
        "size" to bytesWritten
      )
    }
  }

  private fun queryDisplayName(cr: ContentResolver, uri: Uri): String? {
    var cursor: Cursor? = null
    return try {
      cursor = cr.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
      if (cursor != null && cursor.moveToFirst()) {
        val idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (idx >= 0) cursor.getString(idx) else null
      } else null
    } catch (_: Exception) {
      null
    } finally {
      try { cursor?.close() } catch (_: Exception) {}
    }
  }

  private fun fallbackNameFromUri(uri: Uri): String? {
    val seg = uri.lastPathSegment ?: return null
    val last = seg.substringAfterLast('/')
    return if (last.isNotBlank()) last else null
  }

  private fun sanitizeFileName(name: String): String {
    return name
      .replace(Regex("[\\\\/:*?\"<>|]"), "_")
      .replace(Regex("\\s+"), " ")
      .trim()
  }

  private fun uniqueFile(dir: File, fileName: String): File {
    val base = fileName.substringBeforeLast('.', fileName)
    val ext = fileName.substringAfterLast('.', "")
    var candidate = File(dir, fileName)
    var i = 1
    while (candidate.exists()) {
      val newName = if (ext.isNotEmpty()) "$base ($i).$ext" else "$base ($i)"
      candidate = File(dir, newName)
      i++
    }
    return candidate
  }
}