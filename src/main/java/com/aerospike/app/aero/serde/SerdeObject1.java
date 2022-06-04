
package com.aerospike.app.aero.serde;

import com.aerospike.app.model.Object1;

import com.aerospike.client.Bin;
import com.aerospike.client.Record;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


public class SerdeObject1 {

  static Logger log = Logger.getLogger("SerdeObject1");

  static public String FIELD1 = "f1";
  static public String FIELD2 = "f2";
  static public String OBJTYPE = "typ";
  static public String INT1 = "i1";
  static public String STRLIST = "lst";


  public SerdeObject1 () {
  }


  static public Bin[] serialize (Object1 object1) {

    List<Bin> binList = new ArrayList<Bin>();

    binList.add(new Bin(FIELD1, object1.getField1())); 
    binList.add(new Bin(FIELD2, object1.getField2())); 
    binList.add(new Bin(OBJTYPE, object1.getObjTypeStr()));
    binList.add(new Bin(INT1, object1.getInt1()));
    binList.add(new Bin(STRLIST, object1.getStrList()));

    return binList.toArray(new Bin[binList.size()]);

  } // serialize


  static public Object1 deserialize (Record record) {

    String field1 = record.getString(FIELD1);
    String field2 = record.getString(FIELD2);
    String objTypeStr = record.getString(OBJTYPE);
    Object1.ObjType objType = Object1.getObjType(record.getString(OBJTYPE));
    int int1 = record.getInt(INT1);
    List<String> strList = (List<String>)record.getList(STRLIST);

    return new Object1(field1, field2, objType, int1, strList);

  } // deserialize


} // SerdeObject1
