package com.designliquido.delegua

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object DeleguaFileType : LanguageFileType(DeleguaLanguage) {
    override fun getName() = "Delegua"
    override fun getDescription() = "Arquivo de linguagem Delégua"
    override fun getDefaultExtension() = "delegua"
    override fun getIcon(): Icon = DeleguaIcons.FILE
}
