package org.xml.dom

import javax.xml.transform.TransformerFactory

object XMLTransformers {
    private val transformer by lazy { TransformerFactory.newInstance()  }
    fun Transformer() = transformer.newTransformer()!!
}