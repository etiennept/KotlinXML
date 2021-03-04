package org.xml

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.dom.XMLBuilder
import org.xml.dom.XMLTransformers
import org.xml.dom.addComment
import org.xml.dom.addElement
import javax.xml.transform.OutputKeys.*
import javax.xml.transform.Result
import javax.xml.transform.Source
import javax.xml.transform.dom.DOMSource


class DomDocumentWrite internal constructor ( private val  document: Document):DocumentWriter {
    private val bool= true
    override fun comment(string : String   ) {
        document.addComment(string)
    }
   override fun element ( name : String , vararg  attributes  :Pair<String , String>   ,  children: ElementWriter.() -> Unit ){
       DomElementWriter(document.addElement( name , attributes )).children()
    }

    internal val domSource get() = DOMSource(document)

}

class DomElementWriter internal constructor(internal val element: Element) : ElementWriter  {
    override fun element(name : String, vararg attributes  : Pair< String, String> , children : ElementWriter.()->Unit   ){
        DomElementWriter(element.addElement( name , attributes )).children()
    }
    override operator fun  String.unaryPlus(){
        element.appendChild( element.ownerDocument.createTextNode(this))
    }
    override fun comment(string: String ){
        element.addComment(string)
    }
}

fun transformWrite (source : Source, result : Result, vararg property : Pair<String , String >   ) = XMLTransformers.Transformer().run {
    property.map {

        this.setOutputProperty( it.first , it.second )
    }
    transform( source , result )
}
internal  fun  Result.write(version  : String , Encoding : String  ,  init : DomDocumentWrite.() ->  Unit ){
    transformWrite(DomDocumentWrite(XMLBuilder.DocumentBuilder().newDocument()).apply(init).domSource , this  , METHOD to  "xml"  ,VERSION to version , ENCODING to Encoding)
}
