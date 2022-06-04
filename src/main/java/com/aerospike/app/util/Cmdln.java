
package com.aerospike.app.util;

import com.aerospike.app.util.NumUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


public class Cmdln {

  static Logger log = Logger.getLogger("Cmdln");

  // Flags (31 possible; then go to long for 63)
  public static final int SEEN = 1;        // 0000 0000 0000 0001
  public static final int INT = 2;         // 0000 0000 0000 0010
  public static final int LONG = 4;        // 0000 0000 0000 0100
  public static final int STR = 8;         // 0000 0000 0000 1000
  public static final int BOOL = 16;       // 0000 0000 0001 0000
  public static final int FLAG = 32;       // 0000 0000 0010 0000
  public static final int REQUIRED = 64;   // 0000 0000 0100 0000
  public static final int GROUPED = 128;   // 0000 0000 1000 0000
  public static final int PWD = 256;       // 0000 0001 0000 0000
  public static final int NOSWITCH = 512;  // 0000 0010 0000 0000
  public static final int MULTIARG = 1024; // 0000 0100 0000 0000

  String[] args;                     // Command line args
  Map<String, CLArg> clArgMap;       // Dictionary of switch:CLArg pairs


  public Cmdln () {
    clArgMap = new LinkedHashMap<String, CLArg>();
    add("-u", "--usage", "usage", FLAG);
  }


  public void add (String shortSwitch, String longSwitch, String desc, int flags, Object defaultValue, Object allowedValues) {

    CLArg arg = new CLArg (shortSwitch, longSwitch, desc, flags, defaultValue, allowedValues);
    clArgMap.put (longSwitch, arg);

  } // add


  public void add (String shortSwitch, String longSwitch, String desc, int flags, Object defaultValue) {

    CLArg arg = new CLArg (shortSwitch, longSwitch, desc, flags, defaultValue, null);
    clArgMap.put (longSwitch, arg);

  } // add


  public void add (String shortSwitch, String longSwitch, String desc) {

    CLArg arg = new CLArg (shortSwitch, longSwitch, desc, Cmdln.STR, null, null);
    clArgMap.put (longSwitch, arg);

  } // add


  public void add (String shortSwitch, String longSwitch, String desc, int flags) {

    CLArg arg = new CLArg (shortSwitch, longSwitch, desc, flags, null, null);
    clArgMap.put (longSwitch, arg);

  } // add


  public void add (String shortSwitch, String longSwitch, String desc, Object defaultValue) {

    CLArg arg = new CLArg (shortSwitch, longSwitch, desc, Cmdln.STR, defaultValue, null);
    clArgMap.put (longSwitch, arg);

  } // add


  public CLArg getCLArg (String switchStr) {

    CLArg clArg;
    for (Map.Entry<String,CLArg> entry : clArgMap.entrySet()) {
      clArg = entry.getValue();
      if (clArg.getShortSwitch().equals(switchStr) ||
          clArg.getLongSwitch().equals(switchStr)) {
        return clArg;
      }
    }

    return null;
    // throw new IllegalArgumentException(String.format("Switch '%s' not found\n", switchStr));

  } // getCLArg


  public String getStr (String switchStr) {
    return (String) getValue(switchStr);
  }
  public List<String> getStrMulti (String switchStr) {
    return (List<String>) getValue(switchStr);
  }
  public int getInt (String switchStr) {
    return (int) getValue(switchStr);
  }
  public List<Integer> getIntMulti (String switchStr) {
    return (List<Integer>) getValue(switchStr);
  }
  public long getLong (String switchStr) {
    return (long) getValue(switchStr);
  }
  public List<Long> getLongMulti (String switchStr) {
    return (List<Long>) getValue(switchStr);
  }
  public boolean getBool (String switchStr) {
    return (boolean) getValue(switchStr);
  }
  public boolean getFlag (String switchStr) {
    return (boolean) getValue(switchStr);
  }


  public Object getValue (String switchStr) {

    CLArg clArg;
    for (Map.Entry<String,CLArg> entry : clArgMap.entrySet()) {
      clArg = entry.getValue();
      if (clArg.getShortSwitch().equals(switchStr) ||
          clArg.getLongSwitch().equals(switchStr)) {
        return clArg.getValue();
      }
    }

    throw new IllegalArgumentException(String.format("Switch '%s' not found\n", switchStr));

  }


  public boolean switchExists (String switchStr) {

    CLArg clArg;
    for (Map.Entry<String,CLArg> entry : clArgMap.entrySet()) {
      clArg = entry.getValue();
      if (clArg.getShortSwitch().equals(switchStr) ||
          clArg.getLongSwitch().equals(switchStr)) {
        return true;
      }
    }

    return false;

  }


  public void usage (String msg) {
    log.info(String.format(msg+"\n"));
    usage ();
  }


  public void usage () {

    CLArg clArg;
    String usageStr = "Usage: \n";

    for (Map.Entry<String,CLArg> entry : clArgMap.entrySet()) {

      clArg = entry.getValue();

      usageStr += String.format("%-30s","  "+clArg.getShortSwitch()+", "+clArg.getLongSwitch());
      usageStr += String.format("%-6s", clArg.getFlagsStr());
      usageStr += clArg.getDesc();
      if (clArg.getDefaultValue() != null) {
        usageStr += " (default: "+clArg.getDefaultValue()+")";
      }
      if ((clArg.flags & Cmdln.LONG) == Cmdln.LONG) {
        usageStr += String.format("\n%36s%s","", "supports K,M,G,B,T modifiers");
      }
      if ((clArg.flags & Cmdln.BOOL) == Cmdln.BOOL) {
        usageStr += String.format("\n%36s%s", "", "no arg; toggles default");
      }
      if ((clArg.flags & Cmdln.FLAG) == Cmdln.FLAG) {
        usageStr += String.format("\n%36s%s", "", "no arg; sets true");
      }
      usageStr += "\n";
    }

    log.info(String.format(usageStr));

    System.exit(-1);

  } // usage


  public void parse (String[] args) {

    this.args = args;

    CLArg clArg;
    String errs = "";

    for (int i=0; i<args.length; i++) {

      clArg = getCLArg(args[i]);
      if (clArg == null) {
        errs += String.format("Unrecognized switch: '%s'\n", args[i]);
        continue;
      }

      // report missing values
      if ( ( (clArg.flags & Cmdln.INT) == Cmdln.INT   ||
             (clArg.flags & Cmdln.LONG) == Cmdln.LONG ||
             (clArg.flags & Cmdln.STR) == Cmdln.STR     ) &&
           i+1 == args.length) {
        errs += String.format("'%s' switch requires an argument\n", args[i]);
        continue;
      }

      // handle bool or flag
      if (((clArg.flags & Cmdln.BOOL) == Cmdln.BOOL) ||
          ((clArg.flags & Cmdln.FLAG) == Cmdln.FLAG)) {
        handleNoArg(clArg);
      }

      // handle single arg
      else if ((clArg.flags & Cmdln.MULTIARG) != Cmdln.MULTIARG) {
        handleArg(clArg, args[++i]);
      }

      // handle multi-arg
      else {

        // MULTIARG on flag, or bool is an error; they have no args!
        if ((clArg.flags & Cmdln.FLAG) == Cmdln.FLAG ||
            (clArg.flags & Cmdln.BOOL) == Cmdln.BOOL   ) {
          errs += String.format("Boolean and Flag args may not be MULTIARG: '%s'\n", args[i]);
          continue;
        }

        // handle comma delimited single arg
        else if (args[i+1].contains(",")) {
          String[] vals = args[++i].split(",");
          for (int j=0; j<vals.length; j++) {
            handleArg(clArg, vals[j]);
          }
        }

        // handle following args
        else {

          // check next arg is a value; not a switch
          if (getCLArg(args[i+1]) != null) {
            usage(String.format("'%s' switch requires an argument", args[i]));
          }

          // while next arg is not a switch
          CLArg clArgNext;
          for (clArgNext=getCLArg(args[i+1]); clArgNext==null; i++, clArgNext=getCLArg(args[i+1])) {
            handleArg(clArg, args[i+1]);
          }
        }

      }

      clArg.flags |= Cmdln.SEEN;

    } // for args

    // check for errors
    if (!errs.equals("")) {
      usage(errs);
    }

    // check for missing required
    errs = "";
    for (Map.Entry<String,CLArg> entry : clArgMap.entrySet()) {
      clArg = entry.getValue();
      // shld be && clArg.isSeen(), but this allows for req'd w/ defaultValue
      if (clArg.isRequired() && clArg.getValue()==null) {
        errs += String.format("'%s' switch required\n", clArg.getLongSwitch());
      }
    }
    if (!errs.equals("")) {
      usage(errs);
    }

    // check allowed values
    errs = "";
    for (Map.Entry<String,CLArg> entry : clArgMap.entrySet()) {
      clArg = entry.getValue();
      Object allowedValues = clArg.getAllowedValues();
      if (allowedValues != null) {
        if (allowedValues instanceof List) {
          if (!((List)allowedValues).contains((String)clArg.getValue())) {
            errs += String.format("Illegal value '%s'. "+
                                  "'%s' arg must be one of: %s",
                                  (String)clArg.getValue(),
                                  clArg.getLongSwitch(),
                                  String.join(",", (List)allowedValues));
          }
        }
      }
    }
    if (!errs.equals("")) {
      usage(errs);
    }

    // check for usage
    if (args.length==0 || (boolean)(getCLArg("--usage").getValue())) {
      usage();
    }

  } // parse


  public void setValue (CLArg clArg, Object object) {

    if ((clArg.flags & Cmdln.MULTIARG) == Cmdln.MULTIARG) {
      clArg.addValue(object);
    } else {
      clArg.setValue(object);
    }

  } // setValue


  public void handleNoArg (CLArg clArg) {

    // BOOls get toggled
    if ((clArg.flags & Cmdln.BOOL) == Cmdln.BOOL) {
      setValue(clArg, (Object)(!((boolean)clArg.getValue())));
    }

    // FLAGs get set
    else {
      setValue(clArg, (Object)(true));
    }

  } // handleNoArg


  public void handleArg (CLArg clArg, Object value) {

      if ((clArg.flags & Cmdln.INT) == Cmdln.INT) {
        setValue(clArg, (Object)(Integer.parseInt((String)value)));
      }

      else if ((clArg.flags & Cmdln.LONG) == Cmdln.LONG) {
        setValue(clArg, (Object)(NumUtil.parseLong((String)value)));
      }

      else if ((clArg.flags & Cmdln.STR) == Cmdln.STR) {
        setValue(clArg, (Object)(value));
      }

  } // handleArg


  public class CLArg {

    String shortSwitch;
    String longSwitch;
    String desc;
    int flags;
    Object defaultValue;
    Object allowedValues;
    Object value;

    public CLArg (String shortSwitch, String longSwitch, String desc, int flags, Object defaultValue, Object allowedValues) {

      this.shortSwitch = shortSwitch;
      this.longSwitch = longSwitch;
      this.desc = desc;
      this.flags = flags;
      if (isInt() && defaultValue instanceof String) {
        defaultValue = (Object) NumUtil.parseInt((String)defaultValue);
      }
      if (isLong() && defaultValue instanceof String) {
        defaultValue = (Object) NumUtil.parseLong((String)defaultValue);
      }
      this.defaultValue = defaultValue;
      this.allowedValues = allowedValues;
      if (defaultValue != null) {
        this.value = defaultValue;
      }
      else {
        if (isInt()) {
          if (isMultiArg()) {
            List<Integer> intLst = new ArrayList<>();
            this.value = (Object)intLst;
          } else {
            this.value = 0;
          }
        }
        else if (isLong()) {
          if (isMultiArg()) {
            List<Long> longLst = new ArrayList<>();
            this.value = (Object)longLst;
          } else {
            this.value = 0L;
          }
        }
        else if (isStr()) {
          if (isMultiArg()) {
            List<String> stringLst = new ArrayList<>();
            this.value = (Object)stringLst;
          } else {
            this.value = null;
          }
        }
        else if (isBool()) {
          this.value = false;
        }
        else if (isFlag()) {
          this.value = false;
        }
      }
    }


    public boolean isSeen () {
      return (flags & Cmdln.SEEN) == Cmdln.SEEN;
    }
    public boolean isInt () {
      return (flags & Cmdln.INT) == Cmdln.INT;
    }
    public boolean isLong () {
      return (flags & Cmdln.LONG) == Cmdln.LONG;
    }
    public boolean isStr () {
      return (flags & Cmdln.STR) == Cmdln.STR;
    }
    public boolean isBool () {
      return (flags & Cmdln.BOOL) == Cmdln.BOOL;
    }
    public boolean isFlag () {
      return (flags & Cmdln.FLAG) == Cmdln.FLAG;
    }
    public boolean isRequired () {
      return (flags & Cmdln.REQUIRED) == Cmdln.REQUIRED;
    }
    public boolean isGrouped () {
      return (flags & Cmdln.GROUPED) == Cmdln.GROUPED;
    }
    public boolean isPwd () {
      return (flags & Cmdln.PWD) == Cmdln.PWD;
    }
    public boolean isNoSwitch () {
      return (flags & Cmdln.NOSWITCH) == Cmdln.NOSWITCH;
    }
    public boolean isMultiArg () {
      return (flags & Cmdln.MULTIARG) == Cmdln.MULTIARG;
    }


    public String getFlagsStr () {

      String flags = "";

      if (isSeen()) {
        if (!flags.equals("")) {
          flags += "|";
        }
        flags += "Seen";
      }

      if (isInt()) {
        if (!flags.equals("")) {
          flags += "|";
        }
        flags += "Int";
      }

      if (isLong()) {
        if (!flags.equals("")) {
          flags += "|";
        }
        flags += "Long";
      }

      if (isStr()) {
        if (!flags.equals("")) {
          flags += "|";
        }
        flags += "Str";
      }

      if (isBool()) {
        if (!flags.equals("")) {
          flags += "|";
        }
        flags += "Bool";
      }

      if (isFlag()) {
        if (!flags.equals("")) {
          flags += "|";
        }
        flags += "Flag";
      }

      if (isRequired()) {
        if (!flags.equals("")) {
          flags += "|";
        }
        flags += "Required";
      }

      if (isGrouped()) {
        if (!flags.equals("")) {
          flags += "|";
        }
        flags += "Grouped";
      }

      if (isPwd()) {
        if (!flags.equals("")) {
          flags += "|";
        }
        flags += "Pwd";
      }

      if (isNoSwitch()) {
        if (!flags.equals("")) {
          flags += "|";
        }
        flags += "NoSwitch";
      }

      if (isMultiArg()) {
        if (!flags.equals("")) {
          flags += "|";
        }
        flags += "MultiArg";
      }

      flags += " ";

      return flags;

    } // getFlagsStr


    public void setShortSwitch (String shortSwitch) {
      this.shortSwitch = shortSwitch;
    }
    public String getShortSwitch () {
      return this.shortSwitch;
    }


    public void setLongSwitch (String longSwitch) {
      this.longSwitch = longSwitch;
    }
    public String getLongSwitch () {
      return this.longSwitch;
    }


    public void setDesc (String desc) {
      this.desc = desc;
    }
    public String getDesc () {
      return this.desc;
    }


    public void setFlags (int flags) {
      this.flags = flags;
    }
    public int getFlags () {
      return this.flags;
    }


    public void setDefaultValue (Object defaultValue) {
      this.defaultValue = defaultValue;
    }
    public Object getDefaultValue () {
      return this.defaultValue;
    }


    public void setAllowedValues (Object allowedValues) {
      this.allowedValues = allowedValues;
    }
    public Object getAllowedValues () {
      return this.allowedValues;
    }


    public void addValue (Object value) {
      if (this.value == null) {
        ArrayList<Object> lst = new ArrayList<>();
        this.value = (Object)lst;
      }
      ((List<Object>)(this.value)).add(value);
    }
    public void setValue (Object value) {
      this.value = value;
    }
    public Object getValue () {
      return this.value;
    }


    @Override
    public String toString () {

      String str = "{";
      str += "\""+this.shortSwitch+"\", ";
      str += "\""+this.longSwitch+"\", ";
      str += "\""+this.desc+"\", ";
      str += "\""+getFlagsStr()+"\", ";
      str += "\""+this.defaultValue+"\", ";
      str += "\""+this.value+"\", ";
      str += "}";

      return str;

    } // toString


  } // CLArg


} // Cmdln
