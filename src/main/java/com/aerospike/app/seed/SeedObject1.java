
package com.aerospike.app.seed;


import com.aerospike.app.model.Object1;

import java.util.Random;


public class SeedObject1 extends Seeder<Object1> {

  public SeedObject1() {
    super();
  }

  public SeedObject1(Random random) {
    super(random);
  }

  protected SeedObject1(long seed) {
    super(seed);
  }
    

  @Override
  public Object1 get() {

    Object1 object1 = new Object1();

    object1.setField1(seedUtils.getString(random, 10));
    object1.setField2(seedUtils.getString(random, 10));
    int i = random.nextInt(100);
    if (i < 33) {
      object1.setObjType(Object1.ObjType.T1);
    } else if (i < 66) {
      object1.setObjType(Object1.ObjType.T2);
    } else {
      object1.setObjType(Object1.ObjType.T3);
    }
    object1.setInt1(random.nextInt(100));
    object1.setStrList(seedUtils.getStringList(random, 5/*lst*/, 2/*str*/));

    return object1;

  } // get


  public Object1 update(Object1 object1, String field1, String field2, Object1.ObjType objType, int int1) {

    object1.setField1(field1);
    object1.setField2(field2);
    object1.setObjType(objType);
    object1.setInt1(int1);

    return object1;

  } // update


} // SeedObject1
