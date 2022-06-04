
package com.aerospike.app.main;

import com.aerospike.app.util.Cmdln;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;


public class CmdlnParser {

  static Logger log = Logger.getLogger("CmdlnParser");

  Cmdln cl;
  List cmds = Arrays.asList("load", "put", "get", "getbatch", "scan", "trunc");


  public CmdlnParser () {
    cl = new Cmdln();
  }


  public CmdlnParser (String[] args) {
    cl = new Cmdln();
    parseArgs(args);
  }


  public void parseArgs (String[] args) {

    cl.add("-c", "--command", "command: "+String.join(",",cmds),
           cl.STR|cl.REQUIRED, null, cmds);

    cl.add("-rc", "--recCount",  "record count", cl.LONG, 10L);
    cl.add("-rs", "--recStartId",  "record start id", cl.LONG, "1M");
    cl.add("-of1", "--object1Field1", "object field1", cl.STR, "f1");
    cl.add("-of2", "--object1Field2", "object field2", cl.STR, "f2");
    cl.add("-otyp", "--object1Type", "object type", cl.STR, "T1");
    cl.add("-oi1", "--object1Int1", "object int1", cl.INT, 1);
    cl.add("-osl", "--object1StringList", "object string list", cl.STR,
           "str1,str2,str3");

    cl.add("-h", "--host", "host", cl.STR, "localhost");
    cl.add("-p", "--port", "port", cl.INT, 3000);
    cl.add("-ns", "--namespace", "namespace", cl.STR, "test");
    cl.add("-set", "--set", "set name", cl.STR, "object1");
    cl.add("-uk", "--userKey", "user key", cl.STR);

    cl.add("-l", "--limit", "scan limit", cl.LONG, 0L);

    cl.add("-v", "--verbose", "verbose mode", cl.BOOL);
    cl.add("-ur", "--updateRate", "update rate (ms)", cl.INT, 2000);

    cl.add("-tc", "--threadCount", "thread count", cl.INT, 1);
    cl.add("-st", "--stackTrace", "show stack traces", cl.FLAG);

    cl.parse(args);

    checkArgs();

  } // parseArgs


  public void checkArgs () {

    String cmd = cl.getStr("--command");

    // default '--set' to objType name
    if (cl.getStr("--set") == null) {
      Cmdln.CLArg clArg = cl.getCLArg("--set");
      clArg.setValue(cl.getStr("--objectType"));
    }

    // set
    if (cl.getStr("--set") == null) {
      cl.usage(String.format("--set required for '%s' command", cmd));
    }

    // ukey
    if (cl.getStr("--userKey")==null &&
        (cmd.equals("get") ||
         cmd.equals("getbatch") ||
         cmd.equals("put") ) ) {
      cl.usage(String.format("userKey required for '%s' command", cmd));
    }

    // threadCnt
    int threadCnt = cl.getInt("--threadCount");
    long recCnt = cl.getLong("--recCount");
    if (threadCnt > recCnt ) {
      log.warn(String.format("threadCount (%d) > recCount (%d); forcing equal\n",
                             threadCnt, recCnt));
      Cmdln.CLArg threadCntArg = cl.getCLArg("--threadCount");
      threadCntArg.setValue((Object)((int)recCnt));
    }

  } // checkArgs


} // CmdlnParser
