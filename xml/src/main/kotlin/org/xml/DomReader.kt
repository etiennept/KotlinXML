package org.xml

import org.w3c.dom.Attr
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node



class DomElementReader <T >(override val name: String , override val attribute: Attribute , override val children: List<T>): ElementReader<T > {
}
class DomAttribute(private  val  getAttributeId : (String) -> Attr) : Attribute{
    override fun get(key: String) = getAttributeId(key).value!!
}
private val Element.attribute get() =  DomAttribute( ::getAttributeNode  )
private fun <T >Element.readBuilder(  list : List<T>  ) =  DomElementReader(tagName , attribute , list )
private fun  <T > Element.read(init  : DomElementReader<T>.( ) ->  T   ) : T {
    val list = mutableListOf<T>()
    if (hasChildNodes()){
        childNodes.run{
            for ( i in 0 until length ){
                list.add( item(i)!!.run {
                    return@run when( nodeType){
                        Node.ELEMENT_NODE -> {
                            (this as Element ).read(init)
                        }
                        Node.TEXT_NODE -> {
                            textContent!!  as T
                        }
                        else -> throw Exception()
                    }
                })
            }
        }
    }
    return readBuilder(list).init()
}
fun < T  > Document.read(init: ElementReader<T>.() -> T  )= documentElement.read(init)