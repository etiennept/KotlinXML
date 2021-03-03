package org.xml

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.dom.XMLBuilder
import org.xml.dom.XMLTransformers
import org.xml.dom.addComment
import org.xml.dom.addElement
import javax.xml.transform.Result
import javax.xml.transform.Source
import javax.xml.transform.dom.DOMSource


class DomDocumentWrite internal constructor ( private val  document: Document):DocumentWriter {
    private val bool= true
    override fun comment(string : String   ) {
        document.addComment(string)
    }
   override fun element ( name : String , vararg  attributes  :Pair<String , String>   ,  children: ElementWriter.() -> Unit ){
        if ( bool){
            document.appendChild( DomElementWriter(document.addElement(name  , attributes ) ).apply {  children() }.element  )
        }else{
            throw Exception()
        }
    }

    internal val domSource get() = DOMSource(document)

}

class DomElementWriter internal constructor(internal val element: Element) : ElementWriter  {
    override fun element(name : String, vararg attributes  : Pair< String, String> , children : ElementWriter.()->Unit   ){
        element.appendChild( DomElementWriter(element.addElement(name  , attributes ) ).apply {  children() }.element  )
    }
    override operator fun  String.unaryPlus(){
        element.appendChild( element.ownerDocument.createTextNode(this))
    }
    override fun comment(string: String ){
        element.addComment(string)
    }
}


fun write (source : Source, result : Result, vararg property : Pair<String , String >   ) = XMLTransformers.Transformer().run {
    property.map {

        this.setOutputProperty( it.first , it.second )
    }
    transform( source , result )
}
fun domXMlwrite(result: Result, vararg property : Pair<String , String >, init : DomDocumentWrite.() ->  Unit ){
    write(DomDocumentWrite(XMLBuilder.DocumentBuilder().newDocument()).apply(init).domSource , result , *property )
}
