package com.designliquido.delegua.parser

import com.designliquido.delegua.DeleguaFileType
import com.designliquido.delegua.DeleguaLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class DeleguaPsiFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, DeleguaLanguage) {
    override fun getFileType(): FileType = DeleguaFileType
    override fun toString(): String = "Arquivo Delégua"
}
