package org.xml.dom

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import javax.xml.transform.Result
import javax.xml.transform.Source





fun Element.createAttr( attribute:  Pair<String, String>)  = this.ownerDocument!!.createAttribute(attribute.first)!!.apply { value = value }

fun Element.appendAttr(attributes:  Array< out Pair<String, String>> ){
    attributes.forEach {
        setAttributeNode( createAttr( it))
    }
}
private fun Node.addElement( document: Document  ,  name : String , attributes:  Array < out Pair<String, String >> )
        = document.createElement( name )!!.apply {
    appendAttr(attributes)
        this@addElement.appendChild(this)}

fun Element.addElement(name: String ,attributes:   Array < out  Pair<  String, String> > )= addElement(ownerDocument , name , attributes  )
fun Document.addElement( name: String , attributes:   Array <out Pair<String, String>>  )= addElement(this , name , attributes  )

private fun Node.addComment( document: Document  ,  string: String ){
    appendChild( document.createComment(string)  )
}
fun Element.addComment( string: String  )  =  addComment(ownerDocument , string)
fun Document.addComment(string: String)  = addComment( this , string)
