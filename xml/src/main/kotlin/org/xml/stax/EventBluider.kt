package org.xml.stax

import javax.xml.namespace.QName
import javax.xml.stream.XMLEventFactory

object EventBluider {
    private val event by lazy { XMLEventFactory.newInstance()  }
    init {

    }
    fun createStartDocument() = event.createStartDocument()!!
    fun createStartDocument( string : String    ) = event.createStartDocument(string)!!
    fun createStartDocument( string: String , string1: String) = event.createStartDocument(string , string1)!!
    fun createComment(string: String ) = event.createComment(string)!!
    fun createAttribute( pair: Pair<String , String>) = event.createAttribute(pair.first , pair.second)!!
    fun createStartElement (string: String? , string1: String ? , string2  : String  ) = event.createStartElement(string , "" , "")!!
   fun createStartElement( name :  QName) = event.createStartElement(name  ,  null , null )!!
   fun createEndElement( name :QName) = event.createEndElement(name , null  , )!!
    fun createEndDocument() = event.createEndDocument()!!
    fun createEndElement( string : String? ,string1: String?, string2: String ) = event.createEndElement( string,string1 ,string2 )!!
    fun createCharacters(string: String) = event.createCharacters(string)!!
}