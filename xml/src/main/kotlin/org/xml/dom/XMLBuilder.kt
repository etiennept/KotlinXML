package org.xml.dom

import javax.xml.parsers.DocumentBuilderFactory

object XMLBuilder {
    private val documentBuilderFactory by lazy { DocumentBuilderFactory.newInstance() }
    fun DocumentBuilder() = documentBuilderFactory.newDocumentBuilder()!!
}
