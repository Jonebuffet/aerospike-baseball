
package com.aerospike.app.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class Object1 {

  public enum ObjType {
    T1, T2, T3;
  }

  String field1;
  String field2;
  ObjType objType;
  int int1;
  List<String> strList;


  public Object1 () {
    this.strList = new ArrayList<>();
  }


  public Object1 (String field1, String field2, ObjType objType, int int1, List<String> strList) {
    this.field1 = field1;
    this.field2 = field2;
    this.objType = objType;
    this.int1 = int1;
    this.strList = new ArrayList<>();
    for (String str : strList) {
      this.strList.add(str);
    }
  }


  public Object1(Object1 object1) {
    this.field1 = object1.field1;
    this.field2 = object1.field2;
    this.objType = object1.objType;
    this.int1 = object1.int1;
    this.strList = new ArrayList<>();
    for (String str : object1.getStrList()) {
      this.strList.add(str);
    }
  }


  @Override
  public String toString () {

    String string = "{";

    string += "\"field1\": \"" + this.field1 + "\", ";
    string += "\"field2\": \"" + this.field2 + "\", ";
    string += "\"objType\": \"" + (this.objType == ObjType.T1 ? "T1" : this.objType == ObjType.T2 ? "T2" : "T3") + "\", ";
    string += "\"int1\": \"" + String.valueOf(this.int1) + "\", ";
    if (this.strList == null) {
      string += "\"strList\": \"null\"";
    } else {
      string += "\"strList\": [" ;
      StringBuilder strBuilder = new StringBuilder();
      Iterator<String> strIter = strList.iterator();
      while (strIter.hasNext()) {
        strBuilder.append("\""+strIter.next()+"\"");
        if (strIter.hasNext()) {
          strBuilder.append(", ");
        }
      }
      string += strBuilder.toString()+"]";
    }
    string += "}";

    return string;

  } // toString


  public void setField1 (String field1) {
    this.field1 = field1;
  }
  public String getField1 () {
    return this.field1;
  }


  public void setField2 (String field2) {
    this.field2 = field2;
  }
  public String getField2 () {
    return this.field2;
  }


  public void setObjType (ObjType objType) {
    this.objType = objType;
  }
  public void setObjType (String objTypeStr) {
    this.objType = (objTypeStr.equals("T1") ? Object1.ObjType.T1 :
                    objTypeStr.equals("T2") ? Object1.ObjType.T2 :
                    Object1.ObjType.T3);
  }
  public ObjType getObjType () {
    return this.objType;
  }
  public static ObjType getObjType (String objTypeStr) {
    return objTypeStr.equals("T1") ? Object1.ObjType.T1 :
      objTypeStr.equals("T2") ? Object1.ObjType.T2 :
      Object1.ObjType.T3;
  }
  public String getObjTypeStr () {
    return getObjTypeStr(this.objType);
  }
  public static String getObjTypeStr (ObjType objType) {
    return objType == Object1.ObjType.T1 ? "T1" :
      objType == Object1.ObjType.T2 ? "T2" :
      "T3";
  }


  public void setInt1 (int int1) {
    this.int1 = int1;
  }
  public Integer getInt1 () {
    return this.int1;
  }


  public void setStrList (List<String> strList) {
    this.strList = strList;
  }
  public List<String> getStrList () {
    return this.strList;
  }

} // Object1
