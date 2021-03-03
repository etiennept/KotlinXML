package org.xml.dom

import org.w3c.dom.Node
import org.w3c.dom.*

fun <T> NodeList.map (  init  :  (Node)  ->  T       ) {
    val list = mutableListOf<T>()
    for ( i  in 0 until length ){
         init(item(i))
    }
}



