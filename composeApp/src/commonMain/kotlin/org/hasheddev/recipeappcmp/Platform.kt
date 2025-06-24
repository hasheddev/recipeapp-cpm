package org.hasheddev.recipeappcmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform