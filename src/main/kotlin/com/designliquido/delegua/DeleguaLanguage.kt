package com.designliquido.delegua

import com.intellij.lang.Language

object DeleguaLanguage : Language("Delegua") {
    private fun readResolve(): Any = DeleguaLanguage

    override fun getDisplayName() = "Delégua"
}
