

package org.xml

import javax.xml.namespace.QName
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLStreamReader
import javax.xml.stream.events.StartElement

class StaxElementReader <T> internal constructor(private  val qname :QName , override val attribute: Attribute ) : ElementReader<T>  {
    private val list = mutableListOf<T>()
    override val name: String get() = qname.localPart
    override val children: List<T> = list
    internal fun addChildren( child : T ) = list.add(child)
    internal fun end(qname: QName, init: ElementReader<T>.() -> T ) =if(qname == this.qname  ) {
                init()
        }else  throw Exception()

}
class StaxAttributes( private val  attribute : Map<String , String>   ) : Attribute{
    override fun get(key: String): String?  = attribute[key]

}

fun <T> XMLStreamReader.read(run : ElementReader<T>.() -> T  ): T? {
    val stuck = mutableListOf<StaxElementReader<T>>()
    var value  : T? = null
    while (hasNext()) {
        when {
            isStartElement -> {
                val attribute = mutableMapOf<String, String>()
                for (i in 0 until this.attributeCount) {
                    attribute[getAttributeName(i ).localPart] = getAttributeValue(i)
                }
                stuck.add( StaxElementReader( this.name  , StaxAttributes(attribute) )  )
            }
            isCharacters -> {
                @Suppress("UNCHECKED_CAST")
                stuck.last().addChildren( this.text  as T )
            }
            isEndElement -> stuck.run {
                val a = removeLast().end(name , run   )
                if ( isNotEmpty()    ){
                    last().addChildren( a  )
                }else {
                    value = a }

            }
        }
    }
    return value!!
}

fun <T> XMLEventReader.read(run : ElementReader<T>.() -> T ) : T {
    val stuck = mutableListOf<StaxElementReader<T>>()
    var value  : T? = null
    while (hasNext()) {
        this.nextEvent()!!.run {
            when {
                isStartDocument -> {
                    asStartElement()!!.let {
                        stuck.add( StaxElementReader( it.name  , StaxAttributes(it.attribute()) )  )
                    }
                }
                isCharacters -> {
                    asCharacters()!!.let {
                        @Suppress("UNCHECKED_CAST")
                        stuck.last().addChildren(it.data as T )
                    }
                }
                isEndElement -> {
                    asEndElement()!!.let{ stuck.run {
                        val a = removeLast().end( it.name , run   )
                        if ( isNotEmpty()    ){
                            last().addChildren( a  )
                        }else {
                            value = a }

                    }
                    }
                }
                else -> {
                    throw Exception()
                }
            }
        }
    }
    return value!!
}

fun StartElement.attribute() = attributes.run {
    val attribute = mutableMapOf<String, String>()
    while (hasNext()) {
        this.next().run {
           attribute[name.localPart] = value
        }
    }
    return@run attribute
}


