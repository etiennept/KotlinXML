package org.xml.dom

import org.w3c.dom.Attr
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node.*

class Attributes internal constructor ( private  val  getAttributeId : (String) -> Attr   ) {
    fun get( key : String):String? = getAttributeId(key).value
}
class ReadBuilder <T> internal constructor (val tagName : String, val attributes : Attributes, children: List<T>)  {}
val Element.attribute get() =  Attributes( ::getAttributeNode  )
private fun <T >Element.readBuilder(  list : List<T>  ) =  ReadBuilder(tagName , attribute , list )




