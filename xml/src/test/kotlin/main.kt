import org.xml.*
import org.xml.dom.readXMLDOM
import java.io.File



fun main(){





    print(writeXMLDom{
        element("name"   , "id" to "grand"  ){
            element("value"){
                +"hello"
            }
            element("value"){
                +"bye bye "
            }
        }




    })





}