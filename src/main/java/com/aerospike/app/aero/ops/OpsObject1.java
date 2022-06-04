
package com.aerospike.app.aero.ops;

import com.aerospike.app.model.Object1;
import com.aerospike.app.aero.serde.SerdeObject1;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException.ScanTerminated;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Operation;
import com.aerospike.client.Record;
import com.aerospike.client.ScanCallback;
import com.aerospike.client.exp.Exp;
import com.aerospike.client.exp.ExpOperation;
import com.aerospike.client.exp.ExpWriteFlags;
import com.aerospike.client.exp.Expression;
import com.aerospike.client.policy.BatchPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.ScanPolicy;
import com.aerospike.client.policy.WritePolicy;

import org.apache.log4j.Logger;


public class OpsObject1 {

  static Logger log = Logger.getLogger("OpsObject1");

  public AerospikeClient client;
  SerdeObject1 sdObject1;


  public OpsObject1 (AerospikeClient client) {
    this.client = client;
    this.sdObject1 = new SerdeObject1();
  }


  public void put (String ns, String set, String ukey, Object1 object1) {

    WritePolicy writePolicy = client.getWritePolicyDefault();
    Key key = new Key (ns, set, ukey);
    Bin[] bins = sdObject1.serialize(object1);

    client.put(writePolicy, key, bins);

  } // put


  public Object1 get (String ns, String set, String ukey) {

    Policy readPolicy = client.getReadPolicyDefault();
    Key key = new Key (ns, set, ukey);

    Expression expSiz =
      Exp.build(
                Exp.deviceSize()
                );
    Operation[] ops = new Operation[6];
    int onx = 0;
    ops[onx++] = Operation.get(SerdeObject1.FIELD1);
    ops[onx++] = Operation.get(SerdeObject1.FIELD2);
    ops[onx++] = Operation.get(SerdeObject1.OBJTYPE);
    ops[onx++] = Operation.get(SerdeObject1.INT1);
    ops[onx++] = Operation.get(SerdeObject1.STRLIST);
    ops[onx++] = ExpOperation.read("siz", expSiz, 0);
    WritePolicy writePolicy = client.getWritePolicyDefault();
    Record record = client.operate(writePolicy, key, ops);
    // System.out.printf("dmf: record:%s\n", record);

    // Record record = client.get(readPolicy, key);

    Object1 object1;

    if (record == null) {
      object1 = null;
    } else {
      object1 = sdObject1.deserialize(record);
    }

    return object1;

  } // get


  public Object1[] getBatch (String ns, String set, String ukeys) {

    String[] ukeyArr = ukeys.split(",");
    Key[] keys = new Key[ukeyArr.length];
    BatchPolicy batchPolicy = client.getBatchPolicyDefault();
    Record[] records;
    Object1[] object1s;

    for (int i=0; i<ukeyArr.length; i++) {
      keys[i] = new Key (ns, set, ukeyArr[i]);
    }

    records = client.get(batchPolicy, keys);

    object1s = new Object1[records.length];
    for (int i=0; i<records.length; i++) {
      if (records[i] == null) {
        object1s[i] = null;
      } else {
        object1s[i] = sdObject1.deserialize(records[i]);
      }
    }

    return object1s;

  } // getBatch


  public void scan (String ns, String set, ScanCallback cb) {

    ScanPolicy scanPolicy = client.getScanPolicyDefault();

    try {
      client.scanAll(scanPolicy, ns, set, cb);
    }

    catch (ScanTerminated e) {
      log.debug(String.format("Scan terminated"));
    }

    return;

  } // scan


} // OpsObject1
