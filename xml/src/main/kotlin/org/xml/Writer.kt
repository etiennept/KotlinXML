package org.xml

import org.xml.stax.Children
import java.util.jar.Attributes


interface  Node{
    fun element( name : String , vararg attributes: Pair<String , String> = emptyArray() ,  children :ElementWriter.()-> Unit)
    fun comment( comment : String )
}

interface DocumentWriter : Node  {

}
interface ElementWriter : Node {
    operator fun String.unaryPlus()
}