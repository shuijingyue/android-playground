package android.playio.repository.entities
enum class Type {
    a, b, c, d
}
data class Info(val type: Type, val secs: Long)
