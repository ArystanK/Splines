package kz.arctan.splines

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform