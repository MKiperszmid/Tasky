package com.mk.tasky.agenda.domain.usecase

class FormatNameUseCase {
    operator fun invoke(name: String): String {
        val formattedName = if (name.contains(' ')) {
            val split = name.split(' ')
            "${split[0][0]}${split[1][0]}"
        } else {
            "${name[0]}${name[1]}"
        }
        return formattedName.uppercase()
    }
}
